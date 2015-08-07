package com.beauchef.testjwt;

import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jjwt
 *
 */
public class Jjwt {

	
    /**
     * Test of JJWT
     */
    public String run() {
    	//byte[] key = Base64.getEncoder().encode(App.SECRET_KEY_TEXT_256_BITS.getBytes());
    	byte[] key = App.SECRET_KEY_TEXT_256_BITS.getBytes(); // key should not be encoded at this point
    	//Date issuedAt = new Date();
    	//Date expiration = new Date(issuedAt.getTime() + App.EXPIRATION_IN_MINUTES * App.ONE_MINUTE_IN_MILLIS);
    	JwtBuilder jwtBuilder = Jwts.builder()
    			.setIssuer(App.ISSUER)
    			.setSubject(App.SUBJECT)
    			.setAudience(App.AUDIENCE)
    			.setExpiration(App.EXPIRATION_TIME.toDate())
    			.setIssuedAt(App.ISSUED_TIME.toDate())
    			.setId(App.TEST_ID)
    			.claim(App.AUTHORITIES_CLAIM, App.AUTHORITIES)
    			.claim(App.TENANTS_CLAIM, App.TENANTS)
    			.claim(App.MODULES_CLAIM, App.MODULES)
    			.signWith(SignatureAlgorithm.HS256, key);
    	String jwt = jwtBuilder.compact();
    	String[] jwtParts = jwt.split("\\.");
        String payload = new String(Base64.decodeBase64(jwtParts[App.CLAIMS]));
        App.displayResults("JJWT", payload, jwt);
        
        // To set the payload directly matches the result from jose4j
//        jwtBuilder = Jwts.builder().setPayload(App.EXPECTED_CLAIMS)
//        		.signWith(SignatureAlgorithm.HS256, key);
//        jwt = jwtBuilder.compact();
//        App.displayResults("JJWT with payload", App.EXPECTED_CLAIMS, jwt);
        return jwt;
    }

}
