package com.gisma.accounts.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account
{
    private Long accountId;
    private Long beneficiaryId;
}
