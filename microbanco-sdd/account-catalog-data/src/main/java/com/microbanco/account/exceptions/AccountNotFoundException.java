package com.microbanco.account.exceptions;

public class AccountNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String accountIban;

    public AccountNotFoundException(String accountIban) {
        super("Account not found: " + accountIban);
        this.accountIban = accountIban;
    }

    public String getAccountIban() { return accountIban; }
}
