package com.beauchef.jwt;

public class Jwt {

	public final static int HEADER_PART = 0;
	public final static int CLAIMS_PART = 1;
	public final static int ENCRYPTED_PART = 2;
	
	private String payload;
	private String token;
	
	public Jwt(String payload, String token) {
		this.payload = payload;
		this.token = token;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public String getToken() {
		return token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jwt other = (Jwt) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return token;
	}
}
