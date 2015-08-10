package com.beauchef.jwt.library;

import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Library;
import com.beauchef.jwt.Signature;
import com.beauchef.jwt.decoder.JavaJwtDecoder;
import com.beauchef.jwt.encoder.JavaJwtEncoder;

public class JavaJwt extends Library {

	public JavaJwt(String key, Signature alg) {
		super(key, alg);
	}

	@Override
	public Jwt encode(Claims claims) {
		return (new JavaJwtEncoder(secret, algorithm)).encode(claims);
	}

	@Override
	public Claims decode(String token) {
		return (new JavaJwtDecoder(secret, algorithm)).decode(token);
	}

}
