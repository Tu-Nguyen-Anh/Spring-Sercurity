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

import static com.example.springproject.utils.DateUtils.getCurrentDateTimeString;

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
          getCurrentDateTimeString(),
          request.getAmount()
    );
    repository.save(receive);

    TransactionHistory send = new TransactionHistory(
          transactionId,
          encryptedAccountSend,
          request.getAmount().negate(),
          BigDecimal.ZERO,
          getCurrentDateTimeString(),
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
          getCurrentDateTimeString()
    );
  }

  @Override
  public TransactionRequestEncode encrypt(TransactionHistoryRequest request) {
    return new TransactionRequestEncode(
          rsaEncryptorUtils.encrypt(request.getAccountReceive()),
          rsaEncryptorUtils.encrypt(request.getAccountSend()),
          rsaEncryptorUtils.encrypt(rsaEncryptorUtils.convertBigDecimalToString(request.getAmount()))
    );
  }

  private TransactionHistoryRequest decryptRequest(TransactionRequestEncode requestEncode) {
    return new TransactionHistoryRequest(
          rsaEncryptorUtils.decrypt(requestEncode.getAccountReceive()),
          rsaEncryptorUtils.decrypt(requestEncode.getAccountSend()),
          rsaEncryptorUtils.convertStringToBigDecimal(rsaEncryptorUtils.decrypt(requestEncode.getAmount()))
    );
  }

  private String encryptAccount(String accountId) {
    return aesEncryptor.convertToDatabaseColumn(accountId);
  }

  private String generateTransactionId() {
    return UUID.randomUUID().toString();
  }
}
