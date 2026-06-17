package com.microbanco.account.dto;

import com.microbanco.account.entities.Transfer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RegisterForReflection

public class TransferResponse {

    private UUID id;
    private String sourceAccountIban;
    private String targetAccountIban;
    private BigDecimal amount;
    private String description;
    private Instant timestamp;

    public TransferResponse() {}

    public static TransferResponse fromEntity(Transfer entity) {
        TransferResponse response = new TransferResponse();
        response.id = entity.id;
        response.sourceAccountIban = entity.sourceAccountIban;
        response.targetAccountIban = entity.targetAccountIban;
        response.amount = entity.amount;
        response.description = entity.description;
        response.timestamp = entity.timestamp;
        return response;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSourceAccountIban() { return sourceAccountIban; }
    public void setSourceAccountIban(String sourceAccountIban) { this.sourceAccountIban = sourceAccountIban; }
    public String getTargetAccountIban() { return targetAccountIban; }
    public void setTargetAccountIban(String targetAccountIban) { this.targetAccountIban = targetAccountIban; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
