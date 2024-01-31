package com.example.springproject.controller;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.TransactionHistoryRequest;
import com.example.springproject.dto.response.TransactionHistoryResponse;
import com.example.springproject.service.TransactionHistoryService;
import com.example.springproject.service.base.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
  @PostMapping()
  public ResponseGeneral<TransactionHistoryResponse> create(
        @Valid
        @RequestBody TransactionHistoryRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    return ResponseGeneral.ofCreated(messageService.getMessage(TRANSACTION, language),
          service.transactionHistory(service.encrypt(request)));
  }
}
