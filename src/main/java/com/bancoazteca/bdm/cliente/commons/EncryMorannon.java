package com.bancoazteca.bdm.cliente.commons;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Cesar M Orozco R
 *
 */
public class EncryMorannon {
	
	
	private static final String LLAVE_360 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT+Oh2D8EN7rxe1/4YQWYUSESsF71KLmiPjWn/KDdFATi4q3QHJju5bdpf0pxg7sh9nBPzn507bHs+z/qDVxI0N3IerZ+cnKpfk+55MXLhfMAPHMf3JuwLl7lAH76RFPkACWeCg+WP6vJ/PbuaoWZSwLBdCIRF1aQOF8AQOSknIwIDAQAB";

	public static String cifraPassword(String cadena, String app) {
		String cadenaContrasenia = new Date().getTime() + "|" + cadena;
		System.out.println("Antes de cifrar: " + cadenaContrasenia);
		return cifraTodo(cadenaContrasenia, app);
	}

	public static String cifraTodo(String cadena, String app) {
		String cadenaCif = null;
		String llavePublica = LLAVE_360;
		try {
			byte[] keyBytesPublic = decodeBase64(llavePublica);
			Key key = crearCifradorPlano(keyBytesPublic);
			cadenaCif = encodeBase64(cifra(cadena.getBytes("UTF-8"), key));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Truena en cifrado");
			e.printStackTrace();
		}
		return cadenaCif;
	}

	private static byte[] decodeBase64(String message) {
		return Base64.decodeBase64(message);
	}

	private static String encodeBase64(byte[] message) {
		return byteToString(Base64.encodeBase64(message));
	}

	private static String byteToString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static Key crearCifradorPlano(byte[] publicKeyBytes) {
		PublicKey key = null;
		try {
			KeyFactory KeyFactory = java.security.KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			key = KeyFactory.generatePublic(publicKeySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return key;
	}

	private static byte[] cifra(byte[] messageBytes, Key key) {
		byte[] cipherText = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(1, key);
			cipherText = cipher.doFinal(messageBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cipherText;
	}
}
