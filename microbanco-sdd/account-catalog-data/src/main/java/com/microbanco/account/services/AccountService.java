package com.microbanco.account.services;

import com.microbanco.account.entities.Account;
import com.microbanco.account.exceptions.AccountNotFoundException;
import com.microbanco.account.util.IbanUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class AccountService {

    @Transactional
    public Account openAccount(String ownerName, String currency, BigDecimal initialBalance) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name is required");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        Account account = new Account(IbanUtils.generateIban(), ownerName, currency, initialBalance);
        account.persist();
        return account;
    }

    public Account getAccount(String iban) {
        if (!IbanUtils.validateIban(iban)) {
            throw new IllegalArgumentException("Invalid IBAN format: " + iban);
        }
        return Account.<Account>findByIdOptional(iban)
            .orElseThrow(() -> new AccountNotFoundException(iban));
    }

    public List<Account> listAccounts(int page, int size) {
        return Account.findAllPaged(page, size);
    }

    public long countAccounts() {
        return Account.count();
    }

    @Transactional
    public void closeAccount(String iban) {
        Account account = getAccount(iban);
        if (account.balance.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("Cannot close account with non-zero balance");
        }
        if (account.status != Account.Status.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        account.close();
        account.persist();
    }

    public BigDecimal getBalance(String iban) {
        Account account = getAccount(iban);
        return account.balance;
    }
}
