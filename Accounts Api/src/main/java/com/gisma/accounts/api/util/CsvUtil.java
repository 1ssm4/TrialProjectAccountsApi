package com.gisma.accounts.api.util;

import com.gisma.accounts.api.model.Account;
import com.gisma.accounts.api.model.Beneficiary;
import com.gisma.accounts.api.model.Transaction;
import com.opencsv.CSVReader;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil
{

    public static List<Beneficiary> readBeneficiaries() throws Exception
    {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("beneficiaries.csv").getInputStream())))
        {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null)
            {
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setBeneficiaryId(Long.parseLong(line[0]));
                beneficiary.setFirstName(line[1]);
                beneficiary.setLastName(line[2]);
                beneficiaries.add(beneficiary);
            }
        }
        return beneficiaries;
    }

    public static List<Account> readAccounts() throws Exception
    {
        List<Account> accounts = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("accounts.csv").getInputStream())))
        {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null)
            {
                Account account = new Account();
                account.setAccountId(Long.parseLong(line[0]));
                account.setBeneficiaryId(Long.parseLong(line[1]));
                accounts.add(account);
            }
        }
        return accounts;
    }

    public static List<Transaction> readTransactions() throws Exception
    {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("transactions.csv").getInputStream())))
        {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null)
            {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(Long.parseLong(line[0]));
                transaction.setAccountId(Long.parseLong(line[1]));
                transaction.setAmount(Double.parseDouble(line[2]));
                transaction.setType(line[3]);
                transaction.setDate(LocalDate.parse(line[4], formatter));
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}