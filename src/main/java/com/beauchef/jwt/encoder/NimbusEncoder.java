package com.beauchef.jwt.encoder;

import java.io.UnsupportedEncodingException;

import com.beauchef.jwt.Encoder;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Signature;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class NimbusEncoder extends Encoder {

	JWTClaimsSet claimsSet = new JWTClaimsSet();
	SignedJWT signedJWT;
	private byte[] keyBytes;
	private String payload;
	
	public NimbusEncoder(String key, Signature alg) {
		super(key, alg);
		try {
			keyBytes = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ueex) {
			throw new RuntimeException("Unsupported encoding: UTF-8");
		}
	}

	@Override
	protected void iss() {
		claimsSet.setIssuer(claims.iss());
	}

	@Override
	protected void sub() {
		claimsSet.setSubject(claims.sub());
	}

	@Override
	protected void aud() {
		claimsSet.setAudience(claims.aud());
	}

	@Override
	protected void exp() {
		claimsSet.setExpirationTime(claims.exp());
	}

	@Override
	protected void nbf() {
		claimsSet.setNotBeforeTime(claims.nbf());
	}

	@Override
	protected void iat() {
		claimsSet.setIssueTime(claims.iat());
	}

	@Override
	protected void jti() {
		claimsSet.setJWTID(claims.jti());
	}

	@Override
	protected void additionalClaim(String claimName, Object claim) {
		claimsSet.setClaim(claimName, claim);
	}

	@Override
	protected void sign() {
		payload = claimsSet.toJSONObject().toJSONString();
		JWSAlgorithm alg = JWSAlgorithm.parse(algorithm.getName());
		JWSSigner signer;
		if (algorithm.isHmac()) {
			signer = new MACSigner(keyBytes);
		} else {
			// TODO
			//signer = new RSASSASigner(??);
			throw new RuntimeException("RSA not yet implemented...");
		}
		signedJWT = new SignedJWT(new JWSHeader(alg), claimsSet);
    	try {
    		signedJWT.sign(signer);
    	} catch (Exception ex) {
    		throw new RuntimeException(ex.getMessage());
    	}
	}

	@Override
	protected Jwt generate() {
		if (signedJWT == null) {
			throw new RuntimeException("Signed JWT is null!");
		}
		if (payload == null) {
			throw new RuntimeException("Payload is null!");
		}
		String token = signedJWT.serialize();
		Jwt jwt = new Jwt(payload, token);
        return jwt;
	}

}
