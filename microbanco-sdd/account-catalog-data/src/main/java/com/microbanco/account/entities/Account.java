package com.microbanco.account.entities;

import com.microbanco.account.exceptions.InsufficientBalanceException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends PanacheEntityBase {

    public enum Status {
        ACTIVE,
        CLOSED
    }

    @Id
    @Column(length = 34)
    public String iban;

    @Column(name = "owner_name", nullable = false)
    public String ownerName;

    @Column(nullable = false, length = 3)
    public String currency;

    @Column(nullable = false, precision = 19, scale = 4)
    public BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    public Status status;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;

    @Version
    public long version;

    public Account() {}

    public Account(String iban, String ownerName, String currency, BigDecimal balance) {
        this.iban = iban;
        this.ownerName = ownerName;
        this.currency = currency;
        this.balance = balance;
        this.status = Status.ACTIVE;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void debit(BigDecimal amount) {
        if (status != Status.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        this.balance = balance.subtract(amount);
        this.updatedAt = Instant.now();
    }

    public void credit(BigDecimal amount) {
        if (status != Status.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.balance = balance.add(amount);
        this.updatedAt = Instant.now();
    }

    public boolean canClose() {
        return status == Status.ACTIVE && balance.compareTo(BigDecimal.ZERO) == 0;
    }

    public void close() {
        if (!canClose()) {
            throw new IllegalStateException("Account cannot be closed: balance must be zero and account must be ACTIVE");
        }
        this.status = Status.CLOSED;
        this.updatedAt = Instant.now();
    }

    public static List<Account> findAllPaged(int page, int size) {
        return findAll(Sort.by("createdAt").descending()).page(page, size).list();
    }
}
