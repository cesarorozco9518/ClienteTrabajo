package com.bancoazteca.bdm.commons;

import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EncripcionInternals {
	
	private static final String key = "89/ser-6int69*/0";
	
	public static String cifraCadenaInternal(Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		String cadenaCifrada = null;
		try {
			String chainSTR = mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false).writeValueAsString(map);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, secretKeySpec);
			byte[] encrypted = cipher.doFinal(chainSTR.getBytes());
			cadenaCifrada = new String(Base64.encodeBase64(encrypted), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cadenaCifrada;
	}
	
	public static String decifraCadenaInternal(String result) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			cipher.init(2, skeySpec);
			byte[] original = cipher.doFinal(Base64.decodeBase64(result));
			resultado = mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false).readValue(original, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return resultado;
	}

}
