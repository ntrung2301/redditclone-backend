package com.trungnguyen.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungnguyen.dto.AuthenticateResponse;
import com.trungnguyen.dto.LoginRequest;
import com.trungnguyen.dto.RefreshTokenRequest;
import com.trungnguyen.dto.RegisterRequest;
import com.trungnguyen.exception.SpringRedditException;
import com.trungnguyen.model.NotificationEmail;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.User;
import com.trungnguyen.model.VerificationToken;
import com.trungnguyen.repository.UserRepository;
import com.trungnguyen.repository.VerificationTokenRepository;
import com.trungnguyen.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	/*
	 *  Field Injection is not recommended
	 *  Constructor Injection is used - constructor is defined by @AllArgsConstructor
	 * 
	 * */
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final MailContentBuilder mailBuilder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	
	@Transactional
	public void signUp(RegisterRequest rr) {
		User user = new User();
		user.setEmail(rr.getEmail());
		user.setUsername(rr.getUsername());
		user.setPassword(passwordEncoder.encode(rr.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token = generateVerificationToken(user);
		String message = mailBuilder.build("Please click on the below url :" +
				"http://localhost:8080/api/auth/accountVerification/" + token);
		mailService.sendMail(new NotificationEmail("Please activate your account",
											user.getEmail(),
											message));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken vt = new VerificationToken();
		vt.setToken(token);
		vt.setUser(user);
		verificationTokenRepository.save(vt);
		return token;
	}
	
	/*
	 *  Verifying user's token with the one in DB
	 *  If the token is incorrect, throw exception
	 */
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		fetchUserAndEnable(verificationToken.get());
	}
	
	/*
	 *  Enable user with valid token
	 *  Then update the record in the DB
	 */
	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional.orElseThrow(() -> new SpringRedditException("User is not found!" + username));
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	/*
	 *	Generate the token for user to make further requests at the server
	 *  @param take LoginRequest parameter from client
	 *  @return AuthenticateResponse class
	 */
	public AuthenticateResponse login(LoginRequest lr) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(lr.getUsername(), lr.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		return AuthenticateResponse.builder().authenticationToken(token)
							.refreshToken(refreshTokenService.generateRefreshToken().getToken())
							.username(lr.getUsername())
							.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
							.build();
	}
	
	@Transactional
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new SpringRedditException("No current user found"));
	}

	public AuthenticateResponse refreshToken(@Valid RefreshTokenRequest rtr) {
		refreshTokenService.validateRefreshToken(rtr.getRefreshToken());
		String newToken = jwtProvider.generateTokenWithUsername(rtr.getUsername());
		return AuthenticateResponse.builder()
				.authenticationToken(newToken)
				.username(rtr.getUsername())
				.refreshToken(rtr.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.build();
	}
}
