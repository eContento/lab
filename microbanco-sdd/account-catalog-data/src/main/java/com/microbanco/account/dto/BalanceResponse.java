package com.microbanco.account.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;

@RegisterForReflection

public class BalanceResponse {

    private String accountIban;
    private BigDecimal balance;
    private String currency;

    public BalanceResponse() {}

    public BalanceResponse(String accountIban, BigDecimal balance, String currency) {
        this.accountIban = accountIban;
        this.balance = balance;
        this.currency = currency;
    }

    public String getAccountIban() { return accountIban; }
    public void setAccountIban(String accountIban) { this.accountIban = accountIban; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
