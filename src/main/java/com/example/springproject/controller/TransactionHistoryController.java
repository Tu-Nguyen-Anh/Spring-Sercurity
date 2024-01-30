package com.example.springproject.controller;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.TransactionHistoryRequest;
import com.example.springproject.dto.request.TransactionRequestEncode;
import com.example.springproject.dto.response.TransactionHistoryResponse;
import com.example.springproject.service.TransactionHistoryService;
import com.example.springproject.service.base.MessageService;
import com.example.springproject.utils.RSAEncryptorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.springproject.constant.CommonConstants.DEFAULT_LANGUAGE;
import static com.example.springproject.constant.CommonConstants.LANGUAGE;
import static com.example.springproject.constant.MessageCodeConstant.TRANSACTION;

@ResponseStatus(HttpStatus.CREATED)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionHistoryController {
  private final TransactionHistoryService service;
  private final MessageService messageService;
  private final RSAEncryptorUtils encryptor;

  @PostMapping()
  public ResponseGeneral<TransactionHistoryResponse> create(
        @Validated
        @RequestBody TransactionHistoryRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    TransactionRequestEncode transactionRequestEncode = new TransactionRequestEncode(
          encryptor.encrypt(request.getAccountReceive()),
          encryptor.encrypt(request.getAccountSend()),
          encryptor.encrypt(encryptor.convertBigDecimalToString(request.getAmount()))
    );

    log.info("(create) transactionRequestEncode:{}", transactionRequestEncode);

    return ResponseGeneral.ofCreated(messageService.getMessage(TRANSACTION, language),
          service.transactionHistory(transactionRequestEncode));
  }
  //    TransactionRequestEncode transactionRequestEncode = new TransactionRequestEncode(
//          rsaEncryptor.convertToDatabaseColumn(request.getAccountReceive()),
//          rsaEncryptor.convertToDatabaseColumn(request.getAccountSend()),
//          rsaEncryptor.convertToDatabaseColumn()
//    );
}
