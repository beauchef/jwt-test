package com.beauchef.testjwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * Nimbus
 *
 */
public class Nimbus {

    /**
     * Test of Nimbus
     */
    public String run() {
    	JWSSigner signer = new MACSigner(App.SECRET_KEY_TEXT_256_BITS.getBytes());
    	JWTClaimsSet claimsSet = new JWTClaimsSet();
    	claimsSet.setIssuer(App.ISSUER);
    	claimsSet.setSubject(App.SUBJECT);
    	claimsSet.setAudience(App.AUDIENCE);
    	claimsSet.setExpirationTime(App.EXPIRATION_TIME.toDate());
    	claimsSet.setIssueTime(App.ISSUED_TIME.toDate());
    	claimsSet.setJWTID(App.TEST_ID);
    	claimsSet.setCustomClaim(App.AUTHORITIES_CLAIM, App.AUTHORITIES);
    	claimsSet.setCustomClaim(App.TENANTS_CLAIM, App.TENANTS);
    	claimsSet.setCustomClaim(App.MODULES_CLAIM, App.MODULES);
    	SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    	try {
    	signedJWT.sign(signer);
    	} catch (Exception ex) {
    		System.err.println(ex.getMessage());
    	}
    	
    	String payload = claimsSet.toJSONObject().toJSONString();
    	String jwt = signedJWT.serialize();
    	App.displayResults("Nimbus", payload, jwt);
    	return jwt;
    }

}
