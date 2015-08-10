package com.beauchef.jwt.encoder;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.beauchef.jwt.Encoder;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Signature;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JjwtEncoder extends Encoder {

	private JwtBuilder jwtBuilder = Jwts.builder();
	private byte[] keyBytes;
	
	public JjwtEncoder(String key, Signature alg) {
		super(key, alg);
		try {
			keyBytes = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ueex) {
			throw new RuntimeException("Unsupported encoding: UTF-8");
		}
	}

	@Override
	protected void iss() {
		jwtBuilder.setIssuer(claims.iss());
	}

	@Override
	protected void sub() {
		jwtBuilder.setSubject(claims.sub());
	}

	@Override
	protected void aud() {
		jwtBuilder.setAudience(claims.aud());
	}

	@Override
	protected void exp() {
		jwtBuilder.setExpiration(claims.exp());
	}

	@Override
	protected void nbf() {
		jwtBuilder.setNotBefore(claims.nbf());
	}

	@Override
	protected void iat() {
		jwtBuilder.setIssuedAt(claims.iat());
	}

	@Override
	protected void jti() {
		jwtBuilder.setId(claims.jti());
	}

	@Override
	protected void additionalClaim(String claimName, Object claim) {
		jwtBuilder.claim(claimName, claim);
	}

	@Override
	protected void sign() {
		SignatureAlgorithm sig = SignatureAlgorithm.forName(this.algorithm.getName());
		jwtBuilder.signWith(sig, this.keyBytes);
	}

	@Override
	protected Jwt generate() {
    	String token = jwtBuilder.compact();
    	String[] jwtParts = token.split("\\.");
        String payload = new String(Base64.decodeBase64(jwtParts[Jwt.CLAIMS_PART]));
		return new Jwt(payload, token);
	}

}
