package com.example.springproject.entity;

import java.math.BigDecimal;
import com.example.springproject.entity.base.BaseEntityWithUpdater;
import com.example.springproject.utils.AESEncryptor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TransactionHistory")
public class TransactionHistory extends BaseEntityWithUpdater {

  @Column(name = "TransactionID")
  private String transactionID;

  @Convert(converter = AESEncryptor.class)
  @Column(name = "Account", columnDefinition = "VARCHAR(512)")
  private String account;

  @Column(name = "InDebt")
  private BigDecimal inDebt;

  @Column(name = "Have")
  private BigDecimal have;

  @Column(name = "Time")
  private String time;

  @Column(name = "Amount")
  private BigDecimal amount;

  public TransactionHistory(String transactionID,String account, BigDecimal inDebt, BigDecimal have, String time, BigDecimal amount) {
    this.transactionID = transactionID;
    this.account = account;
    this.inDebt = inDebt;
    this.have = have;
    this.time = time;
    this.amount = amount;
  }
}
