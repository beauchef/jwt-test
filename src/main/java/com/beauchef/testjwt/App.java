package com.beauchef.testjwt;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Library;
import com.beauchef.jwt.Signature;
import com.beauchef.jwt.library.JavaJwt;
import com.beauchef.jwt.library.Jjwt;
import com.beauchef.jwt.library.Jose4j;
import com.beauchef.jwt.library.Nimbus;

/**
 * JWT Tests
 * 
 * @see <a href="https://tools.ietf.org/html/rfc7519">IETF Request for Comments: 7519</a>
 * 
 * The sequence used is the one described in the RFC, so it is as following:
 * 
 *   - iss : issuer
 *   - sub : subject
 *   - aud : audience
 *   - exp : expiration
 *   - nbf : not before     NOT USED
 *   - iat : issued at
 *   - jti : id
 *   - aut : authorities    NON-STANDARD
 *   - ten : tenants        NON-STANDARD
 *   - mod : modules        NON-STANDARD
 */
public class App 
{
	public static final int EXPIRATION_IN_MINUTES = 15;
	public static DateTime ISSUED_TIME = new DateTime(2015, 8, 15, 11, 30);
	public static DateTime EXPIRATION_TIME = ISSUED_TIME.plusMinutes(EXPIRATION_IN_MINUTES);
	
	public static final String ID = "EmBq37zYDootE1f_NXg1Sw";
	
    public static final int ALLOWED_SKEW_SECONDS = 30;
    
    public static final String AUTHORITIES_CLAIM = "aut";
    public static final String TENANTS_CLAIM = "ten";
    public static final String MODULES_CLAIM = "mod";
    public static final String SUBJECT = "johndoe@example.com";
    public static final String AUDIENCE = "http://www.beauchef.com/audience";
    public static final String ISSUER = "http://www.beauchef.com/issuer";
    public static final String SECRET_KEY_TEXT_256_BITS = 
    		"mysecretmysecretmysecretmysecret"; // 256 bits
    public static final String SECRET_KEY_TEXT_512_BITS = 
    		"mysecretmysecretmysecretmysecretmysecretmysecretmysecretmysecret"; // 512 bits
    
    public static final List<String> AUTHORITIES = new ArrayList<String>() {
		private static final long serialVersionUID = 3767529895301642959L;
		{
            for (int i = 1; i <= 5; i++) {
                add("authority"+i); // add 5 authorities
            }
    	}
    };
    public static final List<String> TENANTS = new ArrayList<String>() {
		private static final long serialVersionUID = -5704092366546128088L;
		{ 
            for (int i = 1; i <= 3; i++) {
                add("tenant"+i); // add 3 tenants
            }
    	}
    };
    public static final List<String> MODULES = new ArrayList<String>() {
		private static final long serialVersionUID = -3670383167541707537L;
		{
            for (int i = 1; i <= 3; i++) {
                add("module"+i); // add 3 modules
            }
    	}
    };
    
    /**
     * Display the results of a test
     * 
     * @param title
     * @param payload
     * @param token
     */
    public static void displayResults(String title, String payload, String token) {
    	System.out.println("*** " + title);
        System.out.println("Payload: " + payload);
        System.out.println("JWT: " + token);
        System.out.println();
    }
    
    /**
     * main
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Tests of different JWT libraries" );
        System.out.println();
        
        Claims claims = new Claims(ISSUER, SUBJECT, AUDIENCE, EXPIRATION_TIME.toDate(), null, ISSUED_TIME.toDate(), ID);
        claims.add(AUTHORITIES_CLAIM, AUTHORITIES);
        claims.add(TENANTS_CLAIM, TENANTS);
        claims.add(MODULES_CLAIM, MODULES);

        Library jjwt = new Jjwt(SECRET_KEY_TEXT_256_BITS, Signature.HS256);
        Jwt jjwtToken = jjwt.encode(claims);
        displayResults("JJWT", jjwtToken.getPayload(), jjwtToken.getToken());
        
        Library javaJwt = new JavaJwt(SECRET_KEY_TEXT_256_BITS, Signature.HS256);
        Jwt javaJwtToken = javaJwt.encode(claims);
        displayResults("Java JWT", javaJwtToken.getPayload(), javaJwtToken.getToken());
        
        Library jose4j = new Jose4j(SECRET_KEY_TEXT_256_BITS, Signature.HS256);
        Jwt jose4jToken = jose4j.encode(claims);
        displayResults("Jose.4.j", jose4jToken.getPayload(), jose4jToken.getToken());
        
        Library nimbus = new Nimbus(SECRET_KEY_TEXT_256_BITS, Signature.HS256);
        Jwt nimbusToken = nimbus.encode(claims);
        displayResults("Nimbus", nimbusToken.getPayload(), nimbusToken.getToken());
        
        System.out.println("The jose4j and javaJwt tokens are " + ((jose4jToken.equals(javaJwtToken)) ? "" : "not ") + "equal.");
        System.out.println("The jose4j and jjwt tokens are " + ((jose4jToken.equals(jjwtToken)) ? "" : "not ") + "equal.");
        System.out.println("The jose4j and nimbus tokens are " + ((jose4jToken.equals(nimbusToken)) ? "" : "not ") + "equal.");
        
        System.out.println();
        
        Claims jjwtClaims = jose4j.decode(jjwtToken.getToken());
        Claims javaJwtClaims = jose4j.decode(javaJwtToken.getToken());
        Claims jose4jClaims = jose4j.decode(jose4jToken.getToken());
        Claims nimbusClaims = jose4j.decode(nimbusToken.getToken());
        
        System.out.println("*** Decoding by Jose4J");
        System.out.println("JJWT claims:      " + jjwtClaims);
        System.out.println("Java JWT claims:  " + javaJwtClaims);
        System.out.println("Jose4J claims:    " + jose4jClaims);
        System.out.println("Nimbus claims:    " + nimbusClaims);
    }
}
