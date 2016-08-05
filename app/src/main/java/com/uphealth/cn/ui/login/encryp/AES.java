package com.uphealth.cn.ui.login.encryp;

import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密算法
 * @description 
 * @data 2016年7月22日

 * @author jun.wang
 */
public class AES {
	
	private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";  
	  
    private final int HASH_ITERATIONS = 10000;  
    private final int KEY_LENGTH = 128;  
  
    private char[] humanPassphrase = {'Q', 'e', 'F', '2', 'v', '1', 'T', 'l', 'u', 'm', ' ', 'g', 'u', 'z', 'e', 's', ' ', 'k', 's', 'b', 'a', 'l', 'i'};  
  
    private byte[] salt = {0x25, 0x01, 0x2a, 0x3d, 0x41, 0x25, 0x16, 0x07, 0x3f, 0x59, 0x2a, 0x4B, 0x26, 0x33, 0x23,  0x43}; 
  
    private PBEKeySpec myKeyspec = new PBEKeySpec(humanPassphrase, salt,  
            HASH_ITERATIONS, KEY_LENGTH);  
    private final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";  
  
    private SecretKeyFactory keyfactory = null;  
    private SecretKey sk = null;  
    private SecretKeySpec skforAES = null;  
    private byte[] iv = {0x1A, 0x11, 0x4B, 0x25, 0x34, 0x1F, 0x27, 0x19, 0x17, 0x23, 0x31, 0x16, 0x28, 0x2C, 0xD, 0x55}; 
  
    private IvParameterSpec IV;  
  
    public AES() {  
  
        try {  
            keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);  
            sk = keyfactory.generateSecret(myKeyspec);  
  
        } catch (NoSuchAlgorithmException nsae) {  
            Log.e("AESdemo",  
                    "no key factory support for PBEWITHSHAANDTWOFISH-CBC");  
        } catch (InvalidKeySpecException ikse) {  
            Log.e("AESdemo", "invalid key spec for PBEWITHSHAANDTWOFISH-CBC");  
        }  
  
        // This is our secret key. We could just save this to a file instead of  
        // regenerating it  
        // each time it is needed. But that file cannot be on the device (too  
        // insecure). It could  
        // be secure if we kept it on a server accessible through https.  
        byte[] skAsByteArray = sk.getEncoded();  
        // Log.d("",  
        // "skAsByteArray=" + skAsByteArray.length + ","  
        // + Base64Encoder.encode(skAsByteArray));  
        skforAES = new SecretKeySpec(skAsByteArray, "AES");  
  
        ;  
  
        IV = new IvParameterSpec(iv);  
  
    }  
  
    public String encrypt(byte[] plaintext) {  
  
        byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);  
        String base64_ciphertext = Base64Encoder.encode(ciphertext);  
        return base64_ciphertext;  
    }  
  
    public String decrypt(String ciphertext_base64) {  
        byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);  
        String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV,  
                s));  
        return decrypted;  
    }  
    
    private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,  
            byte[] msg) {  
        try {  
            Cipher c = Cipher.getInstance(cmp);  
            c.init(Cipher.ENCRYPT_MODE, sk, IV);  
            return c.doFinal(msg);  
        } catch (NoSuchAlgorithmException nsae) {  
            Log.e("AESdemo", "no cipher getinstance support for " + cmp);  
        } catch (NoSuchPaddingException nspe) {  
            Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);  
        } catch (InvalidKeyException e) {  
            Log.e("AESdemo", "invalid key exception");  
        } catch (InvalidAlgorithmParameterException e) {  
            Log.e("AESdemo", "invalid algorithm parameter exception");  
        } catch (IllegalBlockSizeException e) {  
            Log.e("AESdemo", "illegal block size exception");  
        } catch (BadPaddingException e) {  
            Log.e("AESdemo", "bad padding exception");  
        }  
        return null;  
    }  
  
    private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,  
            byte[] ciphertext) {  
        try {  
            Cipher c = Cipher.getInstance(cmp);  
            c.init(Cipher.DECRYPT_MODE, sk, IV);  
            return c.doFinal(ciphertext);  
        } catch (NoSuchAlgorithmException nsae) {  
            Log.e("AESdemo", "no cipher getinstance support for " + cmp);  
        } catch (NoSuchPaddingException nspe) {  
            Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);  
        } catch (InvalidKeyException e) {  
            Log.e("AESdemo", "invalid key exception");  
        } catch (InvalidAlgorithmParameterException e) {  
            Log.e("AESdemo", "invalid algorithm parameter exception");  
        } catch (IllegalBlockSizeException e) {  
            Log.e("AESdemo", "illegal block size exception");  
        } catch (BadPaddingException e) {  
            Log.e("AESdemo", "bad padding exception");  
            e.printStackTrace();  
        }  
        return null;  
    }  

}
