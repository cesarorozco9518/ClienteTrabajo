package com.bancoazteca.bdm.cliente.commons;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class CifradoPublico {
	
	public String LLAVE_NIP = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt1CdYw74RDS8TQFm1gUNWK71YwyKceqjYH1HK4e0oTRCzMKuBU3qDJLmJfL+9/Z0oNu+JYxpxtqNOWcl1lJIslTW+bofomRoAPZQEPpabQ+InPzGclZwdvWqdBGbKxUFqrt5pm5VibiwAxAhpIBObPIOV1+Ehwc6eHCNkmo5DQq1mmk1YYwKwg3vO6kjsbZseA+gOU3F0MEx+4XR4sca8rXvaenC/kTximdwilBhmHHEi9EB4I8Zoy5aXxPgjnQ4/Gz7eFrXZAeNFw6RIlb6qboJkE0g7+hdR6xhiuBko1oqozaQA0cuybG5E6KRt6ppFJo64EINpLTykC1UKmzujwIDAQAB";
	public String LLAVE_SECRETO = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtfG7oJ0Pj8cRLrj8bqlPfQr08J/hz+xqALm7kh569yU85hkfGMjVn21b2DiE9Hi70KW+d7DxJ9DptaoJhJa8ScWH6dQp4z+DEP3f0XsRptD58cJsoCJQSir3i91GW9Fa9ySlPBWASPJPGneAPnfTjeCsiNMYOBe1Jy0UeCdnjCrkqgbVoPzQMV060eDsCgxSsEQzEcIwNlNrarmUocub+AFeZ/WUiD0zdAsL72urSFlZM8U9zmVbOQShiO6gPDHQfZ+3X+yDgM3hcPDtivmMQGKgmagVoKC6ADS9i6//VuK47dDP+f0fDwQ7IlDFk+l3XN87T9QSKGw82zqEDRmpiQIDAQAB";
	
	public  String cifraNipV2(String nip){  
		String cadenaContrasenia = new StringBuilder()
				.append(new Date().getTime())
				.append("|")
				.append(nip)
				.toString();  
		System.out.println("Antes de cifrar" + cadenaContrasenia);
		return cifraNipV1(cadenaContrasenia);
	}
	
	public  String cifraNipV1(String nip){
		String cadenaCif = null;
		try { 
			byte[] keyBytesPublic = decodeBase64(LLAVE_NIP);
			Key key =  crearCifradorPlano(keyBytesPublic); 
			cadenaCif = encodeBase64(cifra(nip.getBytes("UTF-8"), key));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Truena en cifrado");
			e.printStackTrace();
		}
		return cadenaCif;
	}
	
	public  String cifraClientSecretV2(String clientSecret){  
		String cadenaContrasenia = new StringBuilder()
				.append(new Date().getTime())
				.append("|")
				.append(clientSecret)
				.toString();  
		System.out.println("Antes de cifrar" + cadenaContrasenia);
		return cifraClientSecretV1(cadenaContrasenia);
	}
	
	public  String cifraClientSecretV1(String clientSecret){
		String cadenaCif = null;
		try { 
			byte[] keyBytesPublic = decodeBase64(LLAVE_SECRETO);
			Key key =  crearCifradorPlano(keyBytesPublic); 
			cadenaCif = encodeBase64(cifra(clientSecret.getBytes("UTF-8"), key));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Truena en cifrado");
			e.printStackTrace();
		}
		return cadenaCif;
	}
	
	private  byte[] decodeBase64(String message) {
		return Base64.decodeBase64(message);
	}
	
	private  Key crearCifradorPlano(byte[] publicKeyBytes) {
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);		
	
		PublicKey key;
		try {
			key = keyFactory.generatePublic(publicKeySpec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		
		return key;
	}

	private  byte[] cifra(byte[] messageBytes, Key key) {
		byte[] cipherText = null;
		final String ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}

		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		try {
			cipherText = cipher.doFinal(messageBytes);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}

		return cipherText;
	}
	
	private  String encodeBase64(byte[] message) {
		return byteToString(Base64.encodeBase64(message));
	}
	
	private  String byteToString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
