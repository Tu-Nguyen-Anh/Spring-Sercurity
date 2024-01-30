//package com.example.springproject.utils;
//
//import java.math.BigDecimal;
//import java.util.Base64;
//import javax.crypto.Cipher;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.Converter;
//import jakarta.persistence.AttributeConverter;
//import org.springframework.stereotype.Component;
//
//@Component
//@Converter
//public class RSAEncryptor implements AttributeConverter<String, String> {
//
//  private static final String ALGORITHM = "RSA";
//
//  private KeyPair keyPair;
//  private Cipher cipher;
//
////  @PostConstruct
////  public void init() {
////    try {
////      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
////      keyPairGenerator.initialize(2048);
////      this.keyPair = keyPairGenerator.generateKeyPair();
////      this.cipher = Cipher.getInstance(ALGORITHM);
////    } catch (Exception e) {
////      throw new RuntimeException("Error initializing RSAEncryptor", e);
////    }
////  }
//@PostConstruct
//public void init() {
//  try {
//    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
//    keyPairGenerator.initialize(2048);
//    this.keyPair = keyPairGenerator.generateKeyPair();
//    this.cipher = Cipher.getInstance(ALGORITHM + "/ECB/PKCS1Padding");
//  } catch (Exception e) {
//    throw new RuntimeException("Error initializing RSAEncryptor", e);
//  }
//}
//
//  @Override
//  public String convertToDatabaseColumn(String attribute) {
//    try {
//      cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
//      byte[] encryptedBytes = cipher.doFinal(attribute.getBytes());
//      return Base64.getEncoder().encodeToString(encryptedBytes);
//    } catch (Exception e) {
//      throw new RuntimeException("Error encrypting data", e);
//    }
//  }
//
//  @Override
//  public String convertToEntityAttribute(String dbData) {
//    try {
//      cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
//      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dbData));
//      return new String(decryptedBytes);
//    } catch (Exception e) {
//      throw new RuntimeException("Error decrypting data", e);
//    }
//  }
//
//  public String convertBigDecimalToString(BigDecimal decimalValue) {
//    return decimalValue.toString();
//  }
//  public BigDecimal convertStringToBigDecimal(String stringValue) {
//    return new BigDecimal(stringValue);
//  }
//}
