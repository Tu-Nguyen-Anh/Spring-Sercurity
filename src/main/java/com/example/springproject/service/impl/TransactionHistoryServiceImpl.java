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


  public TransactionHistoryServiceImpl(TransactionHistoryRepository repository, AESEncryptor aesEncryptor, RSAEncryptorUtils rsaEncryptorUtils) {
    super(repository);
    this.repository = repository;
    this.aesEncryptor = aesEncryptor;
    this.rsaEncryptorUtils = rsaEncryptorUtils;
  }

  @Transactional
  @Override
  public TransactionHistoryResponse transactionHistory(TransactionRequestEncode requestEncode) {

    String accountReceive = rsaEncryptorUtils.decrypt(requestEncode.getAccountReceive());
    String accountSend = rsaEncryptorUtils.decrypt(requestEncode.getAccountSend());
    String amount = rsaEncryptorUtils.decrypt(requestEncode.getAmount());

    log.info("(chua giai ma) TransactionRequestEncode:{}", requestEncode);

    TransactionHistoryRequest request = new TransactionHistoryRequest(
          accountReceive,
          accountSend,
          rsaEncryptorUtils.convertStringToBigDecimal(amount)
    );
    log.info("(da giai ma) request:{}", request);

    BigDecimal have = request.getAmount();
    BigDecimal inDebt = request.getAmount().negate();

    String encryptedAccountReceive = aesEncryptor.convertToDatabaseColumn(request.getAccountReceive());
    String encryptedAccountSend = aesEncryptor.convertToDatabaseColumn(request.getAccountSend());

    String transactionId = generateTransactionId();

    TransactionHistory receive = new TransactionHistory(
          transactionId,
          encryptedAccountReceive,
          BigDecimal.ZERO,
          have,
          getCurrentDateString(),
          request.getAmount()
    );
    repository.save(receive);

    TransactionHistory send = new TransactionHistory(
          transactionId,
          encryptedAccountSend,
          inDebt,
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

  private String generateTransactionId() {
    return UUID.randomUUID().toString();
  }

//    String accountReceive = rsaEncryptor.convertToDatabaseColumn(requestEncode.getAccountReceive());
//    String accountSend = rsaEncryptor.convertToDatabaseColumn(requestEncode.getAccountSend());
//    String amount = rsaEncryptor.convertToDatabaseColumn(requestEncode.getAmount());
}
