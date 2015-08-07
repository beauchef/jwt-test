package com.beauchef.testjwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWTSigner;

/**
 * JavaJwt
 *
 */
public class JavaJwt {

    /**
     * Test of Java JWT
     */
    public void run() {
    	JWTSigner signer = new JWTSigner(App.SECRET_KEY_TEXT_256_BITS);
    	Map<String, Object> claims = new HashMap<String, Object>();
    	Date issuedAt = new Date();
    	Date expiration = new Date(issuedAt.getTime() + App.EXPIRATION_IN_MINUTES * App.ONE_MINUTE_IN_MILLIS);
    	claims.put(App.ID, UUID.randomUUID().toString());
    	claims.put(App.SUBJECT_CLAIM, App.SUBJECT);
    	claims.put(App.AUDIENCE_CLAIM, App.AUDIENCE);
    	claims.put(App.ISSUER_CLAIM, App.ISSUER);
    	claims.put(App.ISSUED_AT, issuedAt.getTime());
    	claims.put(App.EXPIRY, expiration.getTime());
    	claims.put(App.AUTHORITIES_CLAIM, App.AUTHORITIES);
    	claims.put(App.TENANTS_CLAIM, App.TENANTS);
    	claims.put(App.MODULES_CLAIM, App.MODULES);
    	//.signWith(SignatureAlgorithm.HS256, key);
        String payload = "unavailable?";
        String jwt = signer.sign(claims);
        App.displayResults("Java JWT", payload, jwt);
    }
	
}
