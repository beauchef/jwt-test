package com.beauchef.jwt.decoder;

import com.beauchef.jwt.Claims;
import com.beauchef.jwt.Decoder;
import com.beauchef.jwt.Signature;

public class JjwtDecoder extends Decoder {

	public JjwtDecoder(String key, Signature alg) {
		super(key, alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Claims decode(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

}
