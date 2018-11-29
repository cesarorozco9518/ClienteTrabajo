package com.bancoazteca.bdm.cliente.commons;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class EncripcionCoreComponent {

	private static final String ENCODEMODE = "UTF-8";
	private static final String CIPHER_TRANSFORM_ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM_ENCRYPT_METHOD = "AES";
	private static String kyCipher = "";
	private static String ivParameter = "";

	public EncripcionCoreComponent() {
		this.kyCipher = "B2A0A0R2N1Q8C0U7O2I0A1T6ZETCETCU";
		this.ivParameter = "02 A6 81 F1 19 8B 87 60 E6 56 81 C1 65 88 5D 34";
	}

	public EncripcionCoreComponent(String kyCipher, String iv) {
		this.kyCipher = kyCipher; // "B2A0A0R2N1Q8C0|U7O2I0A1T6ZETCETCU"
		this.ivParameter = iv;// "02 A6 81 F1 19 8B 87 60 E6 56 81 C1 65 88 5D 34"
	}

	private static String encryptSafeUrl(String message)
			throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {

		if (message != null) {
			if (!message.equals("")) {
				kyCipher = (kyCipher.length() > 32 ? kyCipher.substring(0, 32) : kyCipher);
				SecretKeySpec skeySpec = new SecretKeySpec(StringUtils.leftPad(kyCipher, 32, "0").getBytes(ENCODEMODE),
						ALGORITHM_ENCRYPT_METHOD);
				IvParameterSpec iv = new IvParameterSpec(xeh(ivParameter), 0, 16);
				// Instantiate the cipher
				Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM_ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
				byte[] encrypted = cipher.doFinal(message.getBytes(ENCODEMODE));
				message = new String(Base64.encodeBase64(encrypted));
			}
		}
		return message;
	}

	private static String decryptSafeUrl(String message)
			throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {

		if (message != null) {
			if (!message.equals("")) {
				kyCipher = (kyCipher.length() > 32 ? kyCipher.substring(0, 32) : kyCipher);
				SecretKeySpec skeySpec = new SecretKeySpec(StringUtils.leftPad(kyCipher, 32, "0").getBytes(ENCODEMODE),
						ALGORITHM_ENCRYPT_METHOD);
				IvParameterSpec iv = new IvParameterSpec(xeh(ivParameter), 0, 16);
				// Instantiate the cipher
				Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM_ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
				byte[] decrypted = cipher.doFinal(Base64.decodeBase64(message.getBytes()));
				message = new String(decrypted);
			}
		}

		return message;

	}

	static byte[] xeh(String in) {
		in = StringUtils.leftPad(in.replaceAll(" ", ""), 32, "0");
		int len = in.length() / 2;
		byte[] out = new byte[len];
		for (int i = 0; i < len; i++) {
			out[i] = (byte) Integer.parseInt(in.substring(i * 2, i * 2 + 2), 16);
		}
		return out;
	}

	public String encryptString(String text)
			throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {
		String crypt = "";
		crypt = encryptSafeUrl(text);
		crypt = crypt.replace("=", "");
		crypt = crypt.replace("+", "-");
		crypt = crypt.replace("/", "_");
		return crypt;
	}

	public String decryptString(String text)
			throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {
		String crypt = "";
		crypt = text.replace("-", "+");
		crypt = crypt.replace("_", "/");
		crypt = crypt.replace("==", "");
		crypt = decryptSafeUrl(crypt);
		return crypt;
	}

}
