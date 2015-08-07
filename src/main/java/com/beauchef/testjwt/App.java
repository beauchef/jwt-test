package com.beauchef.testjwt;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * JWT Tests
 * 
 * https://tools.ietf.org/html/rfc7519
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
	public static final String EXPECTED_CLAIMS = "{\"iss\":\"http://www.beauchef.com/issuer\",\"sub\":\"johndoe@example.com\",\"aud\":\"http://www.beauchef.com/audience\",\"exp\":1439653500,\"iat\":1439652600,\"jti\":\"EmBq37zYDootE1f_NXg1Sw\",\"aut\":[\"authority1\",\"authority2\",\"authority3\",\"authority4\",\"authority5\"],\"ten\":[\"tenant1\",\"tenant2\",\"tenant3\"],\"mod\":[\"module1\",\"module2\",\"module3\"]}";
	
	public static final int HEAD = 0;
	public static final int CLAIMS = 1;
	public static final int SIG = 2;
	
	public static final int EXPIRATION_IN_MINUTES = 15;
	public static DateTime ISSUED_TIME = new DateTime(2015, 8, 15, 11, 30);
	public static DateTime EXPIRATION_TIME = ISSUED_TIME.plusMinutes(EXPIRATION_IN_MINUTES);
	public static final String TEST_ID = "EmBq37zYDootE1f_NXg1Sw";
	
    public static final int ALLOWED_SKEW_SECONDS = 30;
	public static final long ONE_MINUTE_IN_MILLIS = 60000;
    public static final String AUTHORITIES_CLAIM = "aut";
    public static final String TENANTS_CLAIM = "ten";
    public static final String MODULES_CLAIM = "mod";
    public static final String AUDIENCE_CLAIM = "aud";
    public static final String ISSUER_CLAIM = "iss";
    public static final String ISSUED_AT = "iat";
    public static final String EXPIRY = "exp";
    public static final String ID = "jti";
    public static final String SUBJECT_CLAIM = "sub";
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
        
        Jose4J jose4j = new Jose4J();
        String jose4jToken = jose4j.run();
        
        JavaJwt javaJwt = new JavaJwt();
        String javaJwtToken = javaJwt.run();
        
        Jjwt jjwt = new Jjwt();
        String jjwtToken = jjwt.run();
        
        Nimbus nimbus = new Nimbus();
        String nimbusToken = nimbus.run();
        
        System.out.println("The jose4j and javaJwt tokens are " + ((jose4jToken.equals(javaJwtToken)) ? "" : "not ") + "equal.");
        System.out.println("The jose4j and jjwt tokens are " + ((jose4jToken.equals(jjwtToken)) ? "" : "not ") + "equal.");
        System.out.println("The jose4j and nimbus tokens are " + ((jose4jToken.equals(nimbusToken)) ? "" : "not ") + "equal.");
    }
}
