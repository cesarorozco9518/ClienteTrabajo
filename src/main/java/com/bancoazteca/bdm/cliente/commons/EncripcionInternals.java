package com.bancoazteca.bdm.cliente.commons;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Cesar M Orozco R
 *
 */
public class EncripcionInternals {
	
	private static final String key = "89/ser-6int69*/0";

	public static String cifraCadenaInternal(Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		String cadenaCifrada = null;
		try {
			String chainSTR = mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
					.writeValueAsString(map);
			System.out.println("El json final en cifraCadena es: " + chainSTR);
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

	public static Map<String, Object> decifraCadenaInternal(String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, skeySpec);
			byte[] original = cipher.doFinal(Base64.decodeBase64(result));
			String s = new String(original);
			map = mapper.readValue(s, new TypeReference<Map<String, Object>>(){});			
			String resultado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
