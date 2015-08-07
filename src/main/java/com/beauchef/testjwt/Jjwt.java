package com.beauchef.testjwt;

import java.util.Date;

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
    public void run() {
    	//byte[] key = Base64.getEncoder().encode(App.SECRET_KEY_TEXT_256_BITS.getBytes());
    	byte[] key = App.SECRET_KEY_TEXT_256_BITS.getBytes(); // key should not be encoded at this point
    	Date issuedAt = new Date();
    	Date expiration = new Date(issuedAt.getTime() + App.EXPIRATION_IN_MINUTES * App.ONE_MINUTE_IN_MILLIS);
    	JwtBuilder jwtBuilder = Jwts.builder()
    			.setSubject(App.SUBJECT)
    			.setAudience(App.AUDIENCE)
    			.setIssuer(App.ISSUER)
    			.setIssuedAt(issuedAt)
    			.setExpiration(expiration)
    			.claim(App.AUTHORITIES_CLAIM, App.AUTHORITIES)
    			.claim(App.TENANTS_CLAIM, App.TENANTS)
    			.claim(App.MODULES_CLAIM, App.MODULES)
    			.signWith(SignatureAlgorithm.HS256, key);
    	String payload = "unavailable?";
    	String jwt = jwtBuilder.compact();
        App.displayResults("JJWT", payload, jwt);
    }

}
