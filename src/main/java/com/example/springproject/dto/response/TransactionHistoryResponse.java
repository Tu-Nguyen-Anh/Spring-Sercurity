package com.example.springproject.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

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
