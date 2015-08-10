package com.beauchef.jwt;

public enum Signature {

    HS256("HS256"),
    HS384("HS384"),
    HS512("HS512"),
    RS256("RS256"),
    RS384("RS384"),
    RS512("RS512");

    private final String  name;

	Signature(String name) {
        this.name = name;
    }
	
    public String getName() {
        return name;
    }
    
    public boolean isHmac() {
    	return (this == Signature.HS256 || this == Signature.HS384 || this == Signature.HS512);
    }

    public boolean isRsa() {
    	return (this == Signature.RS256 || this == Signature.RS384 || this == Signature.RS512);
    }

}
