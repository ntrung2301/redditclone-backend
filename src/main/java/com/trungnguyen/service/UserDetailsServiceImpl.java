package com.trungnguyen.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trungnguyen.model.User;
import com.trungnguyen.repository.UserRepository;

import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	
	/*
	 *  Fetch user details
	 *  And wrap the user details in another User object implemented by UserDetails interface
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No " + username + " is found"));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(), 
				user.isEnabled(),
				true,
				true,
				true,
				getAuthorities("USER")
				);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		// TODO Auto-generated method stub
		return singletonList(new SimpleGrantedAuthority(role));
	}
	
	
}
