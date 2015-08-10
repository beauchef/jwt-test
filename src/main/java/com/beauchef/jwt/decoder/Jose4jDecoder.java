package com.beauchef.jwt.decoder;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

import com.beauchef.App;
import com.beauchef.jwt.Claim;
import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Decoder;
import com.beauchef.jwt.Signature;

public class Jose4jDecoder extends Decoder {

	private JwtConsumer jwtConsumer;
	
	public Jose4jDecoder(String key, Signature alg) {
		super(key, alg);
		Key secretKey;
		if (alg.isRsa()) {
			throw new RuntimeException("RSA not implemented yet.");
		} else {
			try {
				secretKey = new HmacKey(key.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException ueex) {
				throw new RuntimeException("Unsupported encoding exception occured.");
			}
		}
		this.jwtConsumer = new JwtConsumerBuilder()
	            .setVerificationKey(secretKey)
	            .setExpectedAudience(false, "")    // Bizarre Jose4J behavior.. needs empty string here...
	            .setExpectedIssuer(false, null)    // ... but null here, in order to have the field optional.
	            .setExpectedAudience(App.AUDIENCE) // And this does not work
	            .build();
	}
	
	@Override
	public Claims decode(String jwt) {
		Claims claims = null;
		try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
			Date exp = null;
			Date nbf = null;
			Date iat = null;
			if (jwtClaims.getExpirationTime()!=null) {
				exp = new Date(jwtClaims.getExpirationTime().getValueInMillis());
			}
			if (jwtClaims.getNotBefore()!=null) {
				nbf = new Date(jwtClaims.getNotBefore().getValueInMillis());
			}
			if (jwtClaims.getIssuedAt()!=null) {
				iat = new Date(jwtClaims.getIssuedAt().getValueInMillis());
			}
			claims = new Claims(
					jwtClaims.getIssuer(), 
					jwtClaims.getSubject(), 
					jwtClaims.getAudience().get(0),
					exp, 
					nbf,
					iat,
					jwtClaims.getJwtId());
			// Additional claims
			Set<String> omittedClaims = new HashSet<>();
			omittedClaims.addAll(Arrays.asList(Claim.ISS, Claim.SUB, Claim.AUD, Claim.EXP, Claim.NBF, Claim.IAT, Claim.JTI));
			Map<String, Object> additionalClaims = jwtClaims.getClaimsMap(omittedClaims);
			for (Map.Entry<String, Object> entry : additionalClaims.entrySet()) {
			    claims.add(entry.getKey(), entry.getValue());
			}
		} catch (InvalidJwtException e) {
	        throw new RuntimeException("Invalid JWT token.");
	    } catch (MalformedClaimException mce) {
	    	throw new RuntimeException("Malformed claim exception.");
	    }
		return claims;
	}

}
