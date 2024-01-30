package com.example.springproject.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryRequest {
  @NotBlank(message = "Account Receive Not Blank")
  private String accountReceive;

  @NotBlank(message = "Account Send Not Blank")
  private String accountSend;

  @NotNull(message = "Amount Not Blank")
  private BigDecimal amount;

}
