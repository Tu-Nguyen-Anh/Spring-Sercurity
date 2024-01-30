package com.example.springproject.service.impl;

import com.example.springproject.dto.request.TransactionHistoryRequest;
import com.example.springproject.dto.request.TransactionRequestEncode;
import com.example.springproject.dto.response.TransactionHistoryResponse;
import com.example.springproject.entity.TransactionHistory;
import com.example.springproject.repository.TransactionHistoryRepository;
import com.example.springproject.service.TransactionHistoryService;
import com.example.springproject.service.base.BaseServiceImpl;
import com.example.springproject.utils.AESEncryptor;
import com.example.springproject.utils.RSAEncryptorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.springproject.utils.DateUtils.getCurrentDateString;

@Slf4j
public class TransactionHistoryServiceImpl extends BaseServiceImpl<TransactionHistory> implements TransactionHistoryService {
  private final TransactionHistoryRepository repository;
  private final AESEncryptor aesEncryptor;
  private final RSAEncryptorUtils rsaEncryptorUtils;


  public TransactionHistoryServiceImpl(
        TransactionHistoryRepository repository,
        AESEncryptor aesEncryptor,
        RSAEncryptorUtils rsaEncryptorUtils
  ) {
    super(repository);
    this.repository = repository;
    this.aesEncryptor = aesEncryptor;
    this.rsaEncryptorUtils = rsaEncryptorUtils;
  }

  @Transactional
  @Override
  public TransactionHistoryResponse transactionHistory(TransactionRequestEncode requestEncode) {
    TransactionHistoryRequest request = this.decryptRequest(requestEncode);

    String encryptedAccountReceive = this.encryptAccount(request.getAccountReceive());
    String encryptedAccountSend = this.encryptAccount(request.getAccountSend());

    String transactionId = generateTransactionId();

    TransactionHistory receive = new TransactionHistory(
          transactionId,
          encryptedAccountReceive,
          BigDecimal.ZERO,
          request.getAmount(),
          getCurrentDateString(),
          request.getAmount()
    );
    repository.save(receive);

    TransactionHistory send = new TransactionHistory(
          transactionId,
          encryptedAccountSend,
          request.getAmount().negate(),
          BigDecimal.ZERO,
          getCurrentDateString(),
          request.getAmount()
    );
    repository.save(send);

    return new TransactionHistoryResponse(
          send.getTransactionID(),
          aesEncryptor.convertToEntityAttribute(encryptedAccountReceive),
          receive.getInDebt(),
          receive.getHave(),
          aesEncryptor.convertToEntityAttribute(encryptedAccountSend),
          send.getInDebt(),
          send.getHave(),
          getCurrentDateString()
    );
  }

  private TransactionHistoryRequest decryptRequest(TransactionRequestEncode requestEncode) {
    String accountReceive = rsaEncryptorUtils.decrypt(requestEncode.getAccountReceive());
    String accountSend = rsaEncryptorUtils.decrypt(requestEncode.getAccountSend());
    String amount = rsaEncryptorUtils.decrypt(requestEncode.getAmount());

    return new TransactionHistoryRequest(
          accountReceive,
          accountSend,
          rsaEncryptorUtils.convertStringToBigDecimal(amount)
    );
  }

  private String encryptAccount(String accountId) {
    return aesEncryptor.convertToDatabaseColumn(accountId);
  }

  private String generateTransactionId() {
    return UUID.randomUUID().toString();
  }
}
