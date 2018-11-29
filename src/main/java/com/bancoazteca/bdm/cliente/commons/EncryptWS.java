package com.bancoazteca.bdm.cliente.commons;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Cesar M Orozco R
 *
 */
public class EncryptWS {
	
	private static final byte[] ivBytes = { 2, -90, -127, -15, 25, -117, -121, 96, -26, 86, -127, -63, 101, -120, 93, 52 };

	public static String encryptSafeUrl(String text, String tokenCode) {
		String crypt = encrypt(text, tokenCode);
		crypt = crypt.replace("=", "");
		crypt = crypt.replace("\\n", "");
		return crypt.trim();
	}

	public static String decryptSafeUrl(String text, String code) {
		if (text == null) {
			return "";
		}
		String decrypt = decrypt(text, code);
		return decrypt.trim();
	}

	public static String encrypt(String text, String code) {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(code.getBytes(), "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(1, newKey, ivSpec);
			byte[] raw = cipher.doFinal(text.getBytes("UTF-8"));
			return Base64.encodeBase64String(raw);
		} catch (Exception e) {
			System.out.println("EncryptWS encrypt " + e.getMessage() + " " + e.getCause());
		}
		return text;
	}

	public static String decrypt(String text, String code) {
		try {
			byte[] raw = Base64.decodeBase64(text);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(code.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(2, newKey, ivSpec);
			return new String(cipher.doFinal(raw), "UTF-8");
		} catch (Exception e) {
			System.out.println("EncryptWS decrypt " + e.getMessage() + " " + e.getCause());
		}
		return text;
	}
}
