package com.beauchef.testjwt;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

/**
 * Jose4J
 *
 */
public class Jose4J 
{
	
    /**
     * Test of Jose4J
     */
    public String run() {
    	Key secretKey;
    	try {
    		secretKey = new HmacKey(App.SECRET_KEY_TEXT_256_BITS.getBytes("UTF-8"));
    	} catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Required encoding unsupported: UTF-8", e);
        }
    	
    	@SuppressWarnings("unused") // unused for now!
		JwtConsumerBuilder jwtConsumerBuilder = new JwtConsumerBuilder()
    			.setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(App.ALLOWED_SKEW_SECONDS)
                .setRequireSubject()
                .setExpectedIssuer(App.ISSUER)
                .setExpectedAudience(App.AUDIENCE).setVerificationKey(secretKey);
    	
        // Create payload (claims) object
        final JwtClaims claims = new JwtClaims();
        claims.setIssuer(App.ISSUER);
        claims.setSubject(App.SUBJECT);
        claims.setAudience(App.AUDIENCE);
        //claims.setExpirationTimeMinutesInTheFuture(App.EXPIRATION_IN_MINUTES);
        claims.setExpirationTime(NumericDate.fromMilliseconds(App.EXPIRATION_TIME.getMillis()));
        //claims.setIssuedAtToNow();
        claims.setIssuedAt(NumericDate.fromMilliseconds(App.ISSUED_TIME.getMillis()));
        //claims.setGeneratedJwtId();
        claims.setJwtId(App.TEST_ID);
        claims.setStringListClaim(App.AUTHORITIES_CLAIM, App.AUTHORITIES);
        claims.setStringListClaim(App.TENANTS_CLAIM, App.TENANTS);
        claims.setStringListClaim(App.MODULES_CLAIM, App.MODULES);

        // Sign payload and header
        final JsonWebSignature jws = new JsonWebSignature();
        String payload = claims.toJson();
        jws.setPayload(payload);
        jws.setKey(secretKey);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        String jwt = null;
        try {
            jwt = jws.getCompactSerialization();
        } catch (final JoseException jex) {
            throw new IllegalArgumentException(jex.getMessage(), jex);
        }
        App.displayResults("jose.4.j", payload, jwt);
        return jwt;
    }
}
