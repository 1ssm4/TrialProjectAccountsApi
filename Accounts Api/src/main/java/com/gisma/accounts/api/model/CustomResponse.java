package com.gisma.accounts.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse
{
    private String message;

    public CustomResponse(String message)
    {
        this.message = message;
    }
}