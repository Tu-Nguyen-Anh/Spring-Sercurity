package com.example.springproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryRequest {

  private String accountReceive;

  private String accountSend;

  private BigDecimal amount;

}
