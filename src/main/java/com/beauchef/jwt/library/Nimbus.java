package com.beauchef.jwt.library;

import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Jwt;
import com.beauchef.jwt.Library;
import com.beauchef.jwt.Signature;
import com.beauchef.jwt.decoder.NimbusDecoder;
import com.beauchef.jwt.encoder.NimbusEncoder;

public class Nimbus extends Library {

	public Nimbus(String key, Signature alg) {
		super(key, alg);
	}

	@Override
	public Jwt encode(Claims claims) {
		return (new NimbusEncoder(secret, algorithm)).encode(claims);
	}

	@Override
	public Claims decode(String token) {
		return (new NimbusDecoder(secret, algorithm)).decode(token);
	}
	
}