package com.gisma.accounts.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Beneficiary
{
    private Long beneficiaryId;
    private String firstName;
    private String lastName;
}
