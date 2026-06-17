package com.microbanco.account.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transfers")
public class Transfer extends PanacheEntityBase {

    @Id
    public UUID id;

    @Column(name = "source_account_iban", nullable = false, length = 34)
    public String sourceAccountIban;

    @Column(name = "target_account_iban", nullable = false, length = 34)
    public String targetAccountIban;

    @Column(nullable = false, precision = 19, scale = 4)
    public BigDecimal amount;

    @Column(length = 255)
    public String description;

    @Column(nullable = false)
    public Instant timestamp;

    public Transfer() {}

    public Transfer(UUID id, String sourceAccountIban, String targetAccountIban,
                    BigDecimal amount, String description, Instant timestamp) {
        this.id = id;
        this.sourceAccountIban = sourceAccountIban;
        this.targetAccountIban = targetAccountIban;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    public static List<Transfer> findByAccountIdPaged(String accountIban, int page, int size) {
        return find("sourceAccountIban = ?1 OR targetAccountIban = ?1",
                Sort.by("timestamp").descending(), accountIban)
            .page(page, size).list();
    }

    public static long countByAccountId(String accountIban) {
        return count("sourceAccountIban = ?1 OR targetAccountIban = ?1", accountIban);
    }
}
