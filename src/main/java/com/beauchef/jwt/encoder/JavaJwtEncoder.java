package com.beauchef.jwt.encoder;

import java.util.LinkedHashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.beauchef.jwt.Claim;
import com.beauchef.jwt.Encoder;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Signature;

public class JavaJwtEncoder extends Encoder {

	JWTSigner signer;
	Map<String, Object> claimsSet = new LinkedHashMap<String, Object>();
	String token;
	
	public JavaJwtEncoder(String key, Signature alg) {
		super(key, alg);
		if (!alg.isHmac()) {
			throw new RuntimeException("Auth0 Java JWT library only supports HMAC-SHA algorithms.");
		}
		signer = new JWTSigner(key);
	}

	@Override
	protected void iss() {
		claimsSet.put(Claim.ISS, claims.iss());
	}

	@Override
	protected void sub() {
		claimsSet.put(Claim.SUB, claims.sub());
	}

	@Override
	protected void aud() {
		claimsSet.put(Claim.AUD, claims.aud());
	}

	@Override
	protected void exp() {
		claimsSet.put(Claim.EXP, claims.exp().getTime()/1000L);
	}

	@Override
	protected void nbf() {
		claimsSet.put(Claim.NBF, claims.nbf().getTime()/1000L);
	}

	@Override
	protected void iat() {
		claimsSet.put(Claim.IAT, claims.iat().getTime()/1000L);
	}

	@Override
	protected void jti() {
		claimsSet.put(Claim.JTI, claims.jti());
	}

	@Override
	protected void additionalClaim(String claimName, Object claim) {
		claimsSet.put(claimName, claim);
	}

	@Override
	protected void sign() {
        token = signer.sign(claimsSet);
	}

	@Override
	protected Jwt generate() {
		if (token == null) {
			throw new RuntimeException("Token is null!");
		}
        String[] jwtParts = token.split("\\.");
        String payload = new String(Base64.decodeBase64(jwtParts[Jwt.CLAIMS_PART]));
		return new Jwt(payload, token);
	}

}