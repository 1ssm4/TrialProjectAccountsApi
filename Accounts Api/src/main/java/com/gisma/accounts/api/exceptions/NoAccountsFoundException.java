package com.gisma.accounts.api.exceptions;

public class NoAccountsFoundException extends RuntimeException
{
    public NoAccountsFoundException(String message)
    {
        super(message);
    }
}