package com.beauchef.jwt;

public abstract class Library {

	protected String secret;
	protected Signature algorithm;
	
	public Library(String key, Signature alg) {
		if (key==null) {
			throw new RuntimeException("Secret key cannot be null!");
		}
		if (alg==null) {
			throw new RuntimeException("Signature algorithm cannot be null!");
		}
		this.secret = key;
		this.algorithm = alg;
	}
	
	public abstract Jwt encode(Claims claims);
	public abstract Claims decode(String token);
	
}
