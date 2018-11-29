package com.bancoazteca.bdm.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

public class SHA256BD {
	
	
	public static String testHashCipher(String oP, String tokenCode) throws InvalidKeySpecException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(oP.getBytes());
		byte[] aMessageDigest = md.digest();
		String outEncoded = Base64.encodeBase64String(aMessageDigest);
		System.out.println("outEncoded:" + outEncoded);
		outEncoded = EncryptWS.encryptSafeUrl(outEncoded, tokenCode);
		return outEncoded.trim();
	}

	public static String testHashDecipher(String oP, String tokenCode) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String outDecoded = EncryptWS.decryptSafeUrl(oP, tokenCode);
		outDecoded = outDecoded.replace("=", "");
		outDecoded = outDecoded.replace("\\n", "");
		return outDecoded.trim();
	}

	public static byte[] randomUtil(String cadenaToIV) {
		byte[] ivBytes = { 2, -90, -127, -15, 25, -117, -121, 96, -26, 86, -127, -63, 101, -120, 93, 52 };

		byte[] ivectorRandom = new byte[ivBytes.length];
		for (int i = 0; i < ivBytes.length; i++) {
			ivectorRandom[i] = ivBytes[i];
		}
		System.out.println("Random:" + cadenaToIV);

		return ivectorRandom;
	}
}
