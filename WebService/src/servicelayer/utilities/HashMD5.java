package servicelayer.utilities;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import shared.exceptions.MD5Exception;

public class HashMD5 {

	public static String Encrypt(String text) throws MD5Exception {
		 
        String secretKey = "qualityinfosolutions";
        String base64EncryptedString = "";
 
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = text.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 	
        } catch (Exception e) {
			throw new MD5Exception("Problemas al procesar la contraseña del usuario, vuelva intentarlo mas tarde, si el error persiste comuniquese con el Administrador");
		}
        return base64EncryptedString;
    }
 
    public static String Decrypt(String text) throws MD5Exception {
 
        String secretKey = "qualityinfosolutions";
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(text.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        	throw new MD5Exception("Problemas al procesar la contraseña del usuario, vuelva intentarlo mas tarde, si el error persiste comuniquese con el Administrador");
        }
        return base64EncryptedString;
    }
}
