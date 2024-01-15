package com.example.springproject.configuration;


import com.example.springproject.service.base.MessageService;
import com.example.springproject.service.base.MessageServiceImpl;
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
}