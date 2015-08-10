package com.beauchef.jwt;

import java.util.Map;

public abstract class Encoder {

	protected String secret;
	protected Signature algorithm;
	protected Claims claims;
	
	public Encoder(String key, Signature alg) {
		if (key==null) {
			throw new RuntimeException("Secret key cannot be null!");
		}
		if (alg==null) {
			throw new RuntimeException("Signature algorithm cannot be null!");
		}
		this.secret = key;
		this.algorithm = alg;
	}
	
	public Jwt encode(Claims claims) {
		this.claims = claims;
		issuer();
		subject();
		audience();
		expiry();
		notBefore();
		issuedAt();
		jwtId();
		additionalClaims();
		sign();
		return generate();
	}

	protected abstract void iss();
	protected abstract void sub();
	protected abstract void aud();
	protected abstract void exp();
	protected abstract void nbf();
	protected abstract void iat();
	protected abstract void jti();
	protected abstract void additionalClaim(String claimName, Object claim);
	protected abstract void sign();
	protected abstract Jwt generate();

	private void issuer() {
		if (claims.iss() != null) {
			iss();
		}
	}

	private void subject() {
		if (claims.sub() != null) {
			sub();
		}
	}

	private void audience() {
		if (claims.aud() != null) {
			aud();
		}
	}

	private void expiry() {
		if (claims.exp() != null) {
			exp();
		}
	}

	private void notBefore() {
		if (claims.nbf() != null) {
			nbf();
		}
	}

	private void issuedAt() {
		if (claims.iat() != null) {
			iat();
		}
	}

	private void jwtId() {
		if (claims.jti() != null) {
			jti();
		}
	}

	private void additionalClaims() {
		if (claims.getAdditionalClaims() != null) {
			for (Map.Entry<String, Object> entry : claims.getAdditionalClaims()) {
			    String claimName = entry.getKey();
			    Object claim = entry.getValue();
			    additionalClaim(claimName, claim);
			}
		}
	}

}
