package com.beauchef.jwt;

public abstract class Decoder {

	protected String secret;
	protected Signature algorithm;

	public Decoder(String key, Signature alg) {
		if (key==null) {
			throw new RuntimeException("Secret key cannot be null!");
		}
		if (alg==null) {
			throw new RuntimeException("Signature algorithm cannot be null!");
		}
		this.secret = key;
		this.algorithm = alg;
	}

	public abstract Claims decode(String jwt);
	
}
