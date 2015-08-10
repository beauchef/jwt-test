package com.beauchef.jwt.encoder;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import com.beauchef.jwt.Encoder;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Signature;

public class Jose4jEncoder extends Encoder {

	private JwtClaims jwtClaims = new JwtClaims();
	private JsonWebSignature jws;
	private Key secretKey;
	private String payload;
	
	public Jose4jEncoder(String key, Signature alg) {
		super(key, alg);
    	try {
    		secretKey = new HmacKey(key.getBytes("UTF-8"));
    	} catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Required encoding unsupported: UTF-8", e);
        }
	}

	@Override
	protected void iss() {
		jwtClaims.setIssuer(claims.iss());
	}

	@Override
	protected void sub() {
		jwtClaims.setSubject(claims.sub());
	}

	@Override
	protected void aud() {
		jwtClaims.setAudience(claims.aud());
	}

	@Override
	protected void exp() {
		jwtClaims.setExpirationTime(NumericDate.fromMilliseconds(claims.exp().getTime()));
	}

	@Override
	protected void nbf() {
		jwtClaims.setNotBefore(NumericDate.fromMilliseconds(claims.nbf().getTime()));
	}

	@Override
	protected void iat() {
		jwtClaims.setIssuedAt(NumericDate.fromMilliseconds(claims.iat().getTime()));
	}

	@Override
	protected void jti() {
		jwtClaims.setJwtId(claims.jti());
	}

	@Override
	protected void additionalClaim(String claimName, Object claim) {
		jwtClaims.setClaim(claimName, claim);
	}

	@Override
	protected void sign() {
        jws = new JsonWebSignature();
        payload = jwtClaims.toJson();
        jws.setPayload(payload);
        jws.setKey(secretKey);
        jws.setAlgorithmHeaderValue(algorithm.getName());
	}

	@Override
	protected Jwt generate() {
		if (jws == null) {
			throw new RuntimeException("JWS is null!");
		}
		if (payload == null) {
			throw new RuntimeException("Payload is null!");
		}
        Jwt jwt = null;
        try {
        	String token = jws.getCompactSerialization();
        	jwt = new Jwt(payload, token);
        } catch (final JoseException jex) {
            throw new RuntimeException(jex.getMessage());
        }
        return jwt;
	}
}
