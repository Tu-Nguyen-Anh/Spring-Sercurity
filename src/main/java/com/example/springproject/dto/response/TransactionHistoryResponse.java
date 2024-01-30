package com.example.springproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionHistoryResponse {
  private String transactionID;

  private String accountReceive;

  private BigDecimal inDebtReceive;

  private BigDecimal haveReceive;

  private String accountSend;

  private BigDecimal inDebtSend;

  private BigDecimal haveSend;

  private String time;

}
