package com.beauchef.jwt.library;

import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Library;
import com.beauchef.jwt.Signature;
import com.beauchef.jwt.decoder.JjwtDecoder;
import com.beauchef.jwt.encoder.JjwtEncoder;

public class Jjwt extends Library {

	public Jjwt(String key, Signature alg) {
		super(key, alg);
	}

	@Override
	public Jwt encode(Claims claims) {
		return (new JjwtEncoder(secret, algorithm)).encode(claims);
	}

	@Override
	public Claims decode(String token) {
		return (new JjwtDecoder(secret, algorithm)).decode(token);
	}

}
