package com.beauchef.jwt;

/**
 * @see <a href="https://tools.ietf.org/html/rfc7519">IETF Request for Comments: 7519</a>
 * 
 * Standard JWT claims, in the order listed in the RFC:
 * 
 *   - iss : issuer
 *   - sub : subject
 *   - aud : audience
 *   - exp : expiration
 *   - nbf : not before
 *   - iat : issued at
 *   - jti : id
 *   
 */
public class Claim {

    public static final String ISS = "iss";
    public static final String SUB = "sub";
    public static final String AUD = "aud";
    public static final String EXP = "exp";
    public static final String NBF = "nbf";
    public static final String IAT = "iat";
    public static final String JTI = "jti";

}
