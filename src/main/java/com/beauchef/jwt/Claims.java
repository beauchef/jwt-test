package com.beauchef.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Claims {

    private String iss;
    private String sub;
    private String aud;
    private Date exp;
    private Date nbf;
    private Date iat;
    private String jti;
    private Map<String, Object> additionalClaims = new LinkedHashMap<>();
    
    public Claims(String iss, String sub, String aud, Date exp, Date nbf, Date iat, String jti) {
    	this.iss = iss;
    	this.sub = sub;
    	this.aud = aud;
    	this.exp = exp;
    	this.nbf = nbf;
    	this.iat = iat;
    	this.jti = jti;
    }

    public String iss() {
    	return iss;
    }
    public String sub() {
    	return sub;
    }
    public String aud() {
    	return aud;
    }
    public Date exp() {
    	return exp;
    }
    public Date nbf() {
    	return nbf;
    }
    public Date iat() {
    	return iat;
    }
    public String jti() {
    	return jti;
    }
    public void add(String claimName, Object claim) {
    	additionalClaims.put(claimName, claim);
    }
    public Set<Entry<String, Object>> getAdditionalClaims() {
    	return additionalClaims.entrySet();
    }

    @SuppressWarnings("rawtypes")
	private String toStringAdditionalClaims() {
    	StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : additionalClaims.entrySet()) {
			sb.append(", " + entry.getKey() + "=");
			if (!(entry.getValue() instanceof Collection)) {
				sb.append(entry.getValue());
			} else {
				sb.append("[");
				boolean first = true;
				for (Object subEntry : (Collection)entry.getValue()) {
					if (!first) {
						sb.append(", ");
					}
					first=false;
					sb.append(subEntry);
				}
				sb.append("]");
			}
		}
		return sb.toString();
    }
    
	@Override
	public String toString() {
		return "Claims [iss=" + iss + ", sub=" + sub + ", aud=" + aud + ", exp=" + exp + ", nbf=" + nbf + ", iat=" + iat
				+ ", jti=" + jti + toStringAdditionalClaims() + "]";
	}

    
}
