package com.gisma.accounts.api.controllers;

import com.gisma.accounts.api.exceptions.NoAccountsFoundException;
import com.gisma.accounts.api.model.Account;
import com.gisma.accounts.api.model.BalanceResponse;
import com.gisma.accounts.api.model.Beneficiary;
import com.gisma.accounts.api.model.Transaction;
import com.gisma.accounts.api.service.SvcAccountManagement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CtrlAccountManagement
{

    private final SvcAccountManagement svcAccountManagement;

    public CtrlAccountManagement(SvcAccountManagement svcAccountManagement)
    {
        this.svcAccountManagement = svcAccountManagement;
    }

    @GetMapping("/beneficiaries/{beneficiaryId}")
    public ResponseEntity<Object> getBeneficiary(@PathVariable Long beneficiaryId) throws Exception
    {
        Object beneficiary = svcAccountManagement.getBeneficiary(beneficiaryId);
        if (beneficiary == null)
        {
            return new ResponseEntity<>(String.format("Beneficiary with id {%s} not found", beneficiaryId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(beneficiary, HttpStatus.OK);
    }

    @GetMapping("/beneficiaries/{beneficiaryId}/accounts")
    public ResponseEntity<Object> getBeneficiaryAccounts(@PathVariable Long beneficiaryId)
    {
        List<Account> accounts = svcAccountManagement.getBeneficiaryAccounts(beneficiaryId);
        if (accounts == null || accounts.isEmpty())
        {
            return new ResponseEntity<>(String.format("No accounts found for the beneficiary with id {%s}", beneficiaryId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/beneficiaries/{beneficiaryId}/transactions")
    public ResponseEntity<Object> getBeneficiaryTransactions(@PathVariable Long beneficiaryId) throws Exception
    {
        List<Transaction> transactions = svcAccountManagement.getBeneficiaryTransactions(beneficiaryId);
        if (transactions == null || transactions.isEmpty())
        {
            return new ResponseEntity<>(String.format("No transactions found for the beneficiary with id {%s}", beneficiaryId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/beneficiaries/{beneficiaryId}/balance")
    public ResponseEntity<BalanceResponse> getBeneficiaryBalance(@PathVariable Long beneficiaryId)
    {
        try
        {
            Double balance = svcAccountManagement.getBeneficiaryBalance(beneficiaryId);
            BalanceResponse response = new BalanceResponse(beneficiaryId, balance, null);
            return ResponseEntity.ok(response);
        }
        catch (NoAccountsFoundException e)
        {
            return ResponseEntity.status(404).body(new BalanceResponse(beneficiaryId, null, e.getMessage()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/beneficiaries/{beneficiaryId}/largest-withdrawal-last-month")
    public Object getLargestWithdrawalLastMonth(@PathVariable Long beneficiaryId) throws Exception
    {
        return svcAccountManagement.getLargestWithdrawalLastMonth(beneficiaryId);
    }
}
