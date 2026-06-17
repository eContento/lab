package com.microbanco.account.dto;

import com.microbanco.account.entities.Account;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
import java.time.Instant;

@RegisterForReflection

public class AccountResponse {

    private String iban;
    private String ownerName;
    private String currency;
    private BigDecimal balance;
    private Account.Status status;
    private Instant createdAt;
    private Instant updatedAt;

    public AccountResponse() {}

    public static AccountResponse fromEntity(Account entity) {
        AccountResponse response = new AccountResponse();
        response.iban = entity.iban;
        response.ownerName = entity.ownerName;
        response.currency = entity.currency;
        response.balance = entity.balance;
        response.status = entity.status;
        response.createdAt = entity.createdAt;
        response.updatedAt = entity.updatedAt;
        return response;
    }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public Account.Status getStatus() { return status; }
    public void setStatus(Account.Status status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
