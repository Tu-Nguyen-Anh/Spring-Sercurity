package com.example.springproject.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static com.example.springproject.constant.CommonConstants.*;

@Component

public class RSAEncryptorUtils {

  private final KeyPair keyPair;
  private final Cipher rsaCipher;
  private final Cipher symmetricCipher;

  public RSAEncryptorUtils() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
      keyPairGenerator.initialize(2048);
      this.keyPair = keyPairGenerator.generateKeyPair();
      this.rsaCipher = Cipher.getInstance(TRANSFORMATION);
      this.symmetricCipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
    } catch (Exception e) {
      throw new RuntimeException("Error initializing RSAEncryptor", e);
    }
  }

  public String encrypt(String data) {
    try {
      // Tạo khóa đối xứng và mã hóa dữ liệu
      SecretKey symmetricKey = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM).generateKey();
      symmetricCipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
      byte[] encryptedData = symmetricCipher.doFinal(data.getBytes(CHARSET_NAME));

      // Mã hóa khóa đối xứng bằng khóa công khai RSA
      rsaCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
      byte[] encryptedSymmetricKey = rsaCipher.doFinal(symmetricKey.getEncoded());

      // Kết hợp khóa đối xứng và dữ liệu đã mã hóa thành một chuỗi
      byte[] result = new byte[encryptedSymmetricKey.length + encryptedData.length];
      System.arraycopy(encryptedSymmetricKey, 0, result, 0, encryptedSymmetricKey.length);
      System.arraycopy(encryptedData, 0, result, encryptedSymmetricKey.length, encryptedData.length);

      return Base64.getEncoder().encodeToString(result);
    } catch (Exception e) {
      throw new RuntimeException("Error encrypting data", e);
    }
  }

  public String decrypt(String encryptedData) {
    try {
      // Giải mã khóa đối xứng từ khóa công khai RSA
      byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
      byte[] encryptedSymmetricKey = new byte[256];
      System.arraycopy(encryptedBytes, 0, encryptedSymmetricKey, 0, 256);
      rsaCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
      byte[] symmetricKeyBytes = rsaCipher.doFinal(encryptedSymmetricKey);

      // Khôi phục khóa đối xứng
      SecretKey symmetricKey = new SecretKeySpec(symmetricKeyBytes, SYMMETRIC_ALGORITHM);

      // Giải mã dữ liệu bằng khóa đối xứng
      symmetricCipher.init(Cipher.DECRYPT_MODE, symmetricKey);
      byte[] decryptedData = symmetricCipher.doFinal(encryptedBytes, 256, encryptedBytes.length - 256);

      return new String(decryptedData, CHARSET_NAME);
    } catch (Exception e) {
      throw new RuntimeException("Error decrypting data", e);
    }
  }


  public String convertBigDecimalToString(BigDecimal decimalValue) {
    return decimalValue.toString();
  }

  public BigDecimal convertStringToBigDecimal(String stringValue) {
    return new BigDecimal(stringValue);
  }

}