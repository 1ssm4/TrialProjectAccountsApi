package com.gisma.accounts.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse
{
    private Long beneficiaryId;
    private Double balance;
    private String message;
}
