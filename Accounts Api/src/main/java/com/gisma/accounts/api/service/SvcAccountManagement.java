package com.gisma.accounts.api.service;

import com.gisma.accounts.api.exceptions.NoAccountsFoundException;
import com.gisma.accounts.api.model.Account;
import com.gisma.accounts.api.model.Beneficiary;
import com.gisma.accounts.api.model.CustomResponse;
import com.gisma.accounts.api.model.Transaction;
import com.gisma.accounts.api.util.CsvUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SvcAccountManagement
{
    private List<Beneficiary> beneficiaries;
    private List<Account> accounts;
    private List<Transaction> transactions;

    @PostConstruct
    public void init() throws Exception
    {
        this.beneficiaries = CsvUtil.readBeneficiaries();
        this.accounts = CsvUtil.readAccounts();
        this.transactions = CsvUtil.readTransactions();
    }

    public Beneficiary getBeneficiary(Long beneficiaryId)
    {
        return beneficiaries.stream()
                .filter(b -> b.getBeneficiaryId().equals(beneficiaryId))
                .findFirst()
                .orElse(null);
    }

    public List<Account> getBeneficiaryAccounts(Long beneficiaryId)
    {
        return accounts.stream()
                .filter(a -> a.getBeneficiaryId().equals(beneficiaryId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getBeneficiaryTransactions(Long beneficiaryId)
    {
        List<Long> accountIds = getBeneficiaryAccounts(beneficiaryId).stream()
                .map(Account::getAccountId)
                .toList();
        return transactions.stream()
                .filter(t -> accountIds.contains(t.getAccountId()))
                .collect(Collectors.toList());
    }

    public Double getAccountBalance(Long accountId)
    {
        return transactions.stream()
                .filter(t -> t.getAccountId().equals(accountId))
                .mapToDouble(t -> "deposit".equalsIgnoreCase(t.getType()) ? t.getAmount() : -t.getAmount())
                .sum();
    }

    public Double getBeneficiaryBalance(Long beneficiaryId)
    {
        List<Long> accountIds = getBeneficiaryAccounts(beneficiaryId).stream()
                .map(Account::getAccountId)
                .toList();

        if (accountIds.isEmpty())
        {
            throw new NoAccountsFoundException("No accounts found for beneficiary ID: " + beneficiaryId);
        }

        return accountIds.stream()
                .mapToDouble(this::getAccountBalance)
                .sum();
    }

    public Object getLargestWithdrawalLastMonth(Long beneficiaryId)
    {
        List<Long> accountIds = getBeneficiaryAccounts(beneficiaryId).stream()
                .map(Account::getAccountId)
                .toList();
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        Optional<Transaction> largestWithdrawal = transactions.stream()
                .filter(t -> accountIds.contains(t.getAccountId()) &&
                        "withdrawal".equalsIgnoreCase(t.getType()) &&
                        (t.getDate().isEqual(firstDayOfLastMonth) || t.getDate().isAfter(firstDayOfLastMonth)) &&
                        (t.getDate().isEqual(lastDayOfLastMonth) || t.getDate().isBefore(lastDayOfLastMonth)))
                .max(Comparator.comparingDouble(Transaction::getAmount));

        if (largestWithdrawal.isPresent())
        {
            return largestWithdrawal.get();
        }
        else
        {
            return new CustomResponse("No withdrawals made in the last month");
        }
    }
}
