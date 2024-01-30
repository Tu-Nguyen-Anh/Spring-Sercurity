package com.example.springproject.configuration;


import com.example.springproject.repository.TransactionHistoryRepository;
import com.example.springproject.service.TransactionHistoryService;
import com.example.springproject.service.base.MessageService;
import com.example.springproject.service.base.MessageServiceImpl;
import com.example.springproject.service.impl.TransactionHistoryServiceImpl;
import com.example.springproject.utils.AESEncryptor;
import com.example.springproject.utils.RSAEncryptorUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * This class define all service component in application
 */
@Configuration
public class ServiceConfiguration {

  @Bean
  public MessageService messageService(MessageSource messageSource) {
    return new MessageServiceImpl(messageSource);
  }
  @Bean
  public TransactionHistoryService transactionHistoryService(
        TransactionHistoryRepository repository,
        AESEncryptor aesEncryptor,
        RSAEncryptorUtils rsaEncryptorUtils) {
    return new TransactionHistoryServiceImpl(repository,aesEncryptor, rsaEncryptorUtils);
  }
}