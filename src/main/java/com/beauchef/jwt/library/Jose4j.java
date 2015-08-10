package com.beauchef.jwt.library;

import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Library;
import com.beauchef.jwt.Signature;
import com.beauchef.jwt.decoder.Jose4jDecoder;
import com.beauchef.jwt.encoder.Jose4jEncoder;

public class Jose4j extends Library {

	public Jose4j(String key, Signature alg) {
		super(key, alg);
	}

	@Override
	public Jwt encode(Claims claims) {
		return (new Jose4jEncoder(secret, algorithm)).encode(claims);
	}

	@Override
	public Claims decode(String token) {
		return (new Jose4jDecoder(secret, algorithm)).decode(token);
	}
	
}
