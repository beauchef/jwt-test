package com.beauchef.testjwt;

import java.util.LinkedHashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;

/**
 * JavaJwt
 *
 */
public class JavaJwt {

    /**
     * Test of Java JWT
     */
    public String run() {
    	JWTSigner signer = new JWTSigner(App.SECRET_KEY_TEXT_256_BITS);
    	Map<String, Object> claims = new LinkedHashMap<String, Object>();
    	//Date issuedAt = new Date();
    	//Date expiration = new Date(issuedAt.getTime() + App.EXPIRATION_IN_MINUTES * App.ONE_MINUTE_IN_MILLIS);
    	claims.put(App.ISSUER_CLAIM, App.ISSUER);
    	claims.put(App.SUBJECT_CLAIM, App.SUBJECT);
    	claims.put(App.AUDIENCE_CLAIM, App.AUDIENCE);
    	claims.put(App.EXPIRY, App.EXPIRATION_TIME.getMillis());
    	claims.put(App.ISSUED_AT, App.ISSUED_TIME.getMillis());
    	claims.put(App.ID, App.TEST_ID); // UUID.randomUUID().toString()
    	claims.put(App.AUTHORITIES_CLAIM, App.AUTHORITIES);
    	claims.put(App.TENANTS_CLAIM, App.TENANTS);
    	claims.put(App.MODULES_CLAIM, App.MODULES);
    	//.signWith(SignatureAlgorithm.HS256, key);
        String jwt = signer.sign(claims);
        String[] jwtParts = jwt.split("\\.");
        String payload = new String(Base64.decodeBase64(jwtParts[App.CLAIMS]));
        App.displayResults("Java JWT", payload, jwt);
        return jwt;
    }
	
}
