package com.gisma.accounts.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class Transaction
{
    private Long transactionId;
    private Long accountId;
    private Double amount;
    private String type;
    private LocalDate date;
}
