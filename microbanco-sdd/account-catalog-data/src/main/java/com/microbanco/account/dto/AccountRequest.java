package com.microbanco.account.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@RegisterForReflection

public class AccountRequest {

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Currency is required")
    private String currency;

    @PositiveOrZero(message = "Initial balance cannot be negative")
    private BigDecimal initialBalance;

    public AccountRequest() {}

    public AccountRequest(String ownerName, String currency, BigDecimal initialBalance) {
        this.ownerName = ownerName;
        this.currency = currency;
        this.initialBalance = initialBalance;
    }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}
