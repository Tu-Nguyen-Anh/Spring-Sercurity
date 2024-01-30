package com.example.springproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestEncode {

  private String accountReceive;

  private String accountSend;

  private String amount;

}

