package com.waben.stock.interfaces.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCrypt {

	public static String crypt(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}
