package com.microbanco.account.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@RegisterForReflection

public class TransferRequest {

    @NotBlank(message = "Source account IBAN is required")
    private String sourceAccountIban;

    @NotBlank(message = "Target account IBAN is required")
    private String targetAccountIban;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;

    public TransferRequest() {}

    public TransferRequest(String sourceAccountIban, String targetAccountIban, BigDecimal amount, String description) {
        this.sourceAccountIban = sourceAccountIban;
        this.targetAccountIban = targetAccountIban;
        this.amount = amount;
        this.description = description;
    }

    public String getSourceAccountIban() { return sourceAccountIban; }
    public void setSourceAccountIban(String sourceAccountIban) { this.sourceAccountIban = sourceAccountIban; }
    public String getTargetAccountIban() { return targetAccountIban; }
    public void setTargetAccountIban(String targetAccountIban) { this.targetAccountIban = targetAccountIban; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
