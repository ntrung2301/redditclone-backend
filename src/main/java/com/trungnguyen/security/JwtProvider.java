package com.trungnguyen.security;


import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.trungnguyen.exception.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


/*
 *  USE ASYMMETRIC ENCRYPTION
 *  PRIVATE KEY - USE TO SIGN A TOKEN
 *  PUBLIC KEY - TO VERIFY THE TOKEN
 *  
 */
@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;
	
	/*
	 *  In the @PostConstruct method bean is fully initialised and dependencies injected 
	 *  Retrieve and load the key in the JKS type to the keyStore
	 *  Load the keyStore at the time of server starts up
	 */
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/redditclone.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new SpringRedditException("Error occured during loading keystore");
		}
	}
	
	/*
	 *  Use private key to sign the constructed JWT
	 */
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}
	
	public String generateTokenWithUsername(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}
	
	
	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}
	
	/*
	 *  Get the private key from the keyStore
	 */
	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("redditclone", "secret".toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new SpringRedditException("Error occured during retrieving the private key");
		}
	}
	
	/*
	 *  Use public key to verify the token
	 */
	public boolean validateToken(String jwt) {
		Jwts.parserBuilder()
		.setSigningKey(getPublickey())
		.build()
		.parseClaimsJws(jwt);
		return true;
	}
	
	/*
	 *  Get the public key from the keyStore
	 */
	private PublicKey getPublickey() {
		try {
			return keyStore.getCertificate("redditclone").getPublicKey();
		} catch (KeyStoreException e) {
			throw new SpringRedditException("Error occured during retrieving the public key");
		}
	}
	
	/*
	 *  To get the username from JWT
	 */
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(getPublickey())
						.build()
						.parseClaimsJws(token)
						.getBody();
		return claims.getSubject();
	}
	
}
