package com.example.springproject.utils;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import jakarta.persistence.AttributeConverter;

import java.security.Key;
import java.util.Base64;

import static com.example.springproject.constant.CommonConstants.SYMMETRIC_ALGORITHM;

@Component
@Converter
public class AESEncryptor implements AttributeConverter<String, String> {


  private Key key;
  private Cipher cipher;

  @PostConstruct
  public void init() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
      keyGenerator.init(256);
      this.key = keyGenerator.generateKey();

      this.cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
    } catch (Exception e) {
      throw new RuntimeException("Error initializing AESEncryptor", e);
    }
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] encryptedBytes = cipher.doFinal(attribute.getBytes());
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException("Error encrypting data", e);
    }
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dbData));
      return new String(decryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException("Error decrypting data", e);
    }
  }

}
