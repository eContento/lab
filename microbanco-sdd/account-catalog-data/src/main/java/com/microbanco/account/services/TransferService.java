package com.microbanco.account.services;

import com.microbanco.account.entities.Account;
import com.microbanco.account.entities.Transfer;
import com.microbanco.account.exceptions.AccountNotFoundException;
import com.microbanco.account.util.IbanUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransferService {

    @Transactional
    public Transfer executeTransfer(String sourceAccountIban, String targetAccountIban,
                                     BigDecimal amount, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (sourceAccountIban.equals(targetAccountIban)) {
            throw new IllegalArgumentException("Source and target accounts must be different");
        }
        if (!IbanUtils.validateIban(sourceAccountIban)) {
            throw new IllegalArgumentException("Invalid source IBAN format: " + sourceAccountIban);
        }
        if (!IbanUtils.validateIban(targetAccountIban)) {
            throw new IllegalArgumentException("Invalid target IBAN format: " + targetAccountIban);
        }

        Account source = Account.<Account>findByIdOptional(sourceAccountIban)
            .orElseThrow(() -> new AccountNotFoundException(sourceAccountIban));

        source.debit(amount);
        source.persist();

        if (IbanUtils.isEntityAccount(targetAccountIban)) {
            Account target = Account.<Account>findByIdOptional(targetAccountIban)
                .orElseThrow(() -> new AccountNotFoundException(targetAccountIban));
            target.credit(amount);
            target.persist();
        }

        Transfer transfer = new Transfer(
            UUID.randomUUID(), sourceAccountIban, targetAccountIban,
            amount, description != null ? description : "", Instant.now()
        );
        transfer.persist();
        return transfer;
    }

    public List<Transfer> getTransferHistory(String accountIban, int page, int size) {
        if (!IbanUtils.validateIban(accountIban)) {
            throw new IllegalArgumentException("Invalid IBAN format: " + accountIban);
        }
        if (Account.findByIdOptional(accountIban).isEmpty()) {
            throw new AccountNotFoundException(accountIban);
        }
        return Transfer.findByAccountIdPaged(accountIban, page, size);
    }

    public long countTransfers(String accountIban) {
        return Transfer.countByAccountId(accountIban);
    }
}
