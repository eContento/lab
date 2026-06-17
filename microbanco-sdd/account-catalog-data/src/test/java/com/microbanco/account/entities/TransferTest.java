package com.microbanco.account.entities;

import com.microbanco.account.util.IbanUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    @Test
    void shouldCreateTransfer() {
        String sourceIban = IbanUtils.generateIban();
        String targetIban = IbanUtils.generateIban();
        Transfer transfer = new Transfer(UUID.randomUUID(), sourceIban, targetIban,
            BigDecimal.valueOf(500), "Test transfer", Instant.now());

        assertEquals(sourceIban, transfer.sourceAccountIban);
        assertEquals(targetIban, transfer.targetAccountIban);
        assertEquals(BigDecimal.valueOf(500), transfer.amount);
        assertEquals("Test transfer", transfer.description);
        assertNotNull(transfer.id);
        assertNotNull(transfer.timestamp);
    }

    @Test
    void shouldAllowEmptyDescription() {
        Transfer transfer = new Transfer(UUID.randomUUID(), IbanUtils.generateIban(), IbanUtils.generateIban(),
            BigDecimal.valueOf(100), "", Instant.now());
        assertEquals("", transfer.description);
    }

    @Test
    void shouldAllowNullDescription() {
        Transfer transfer = new Transfer(UUID.randomUUID(), IbanUtils.generateIban(), IbanUtils.generateIban(),
            BigDecimal.valueOf(100), null, Instant.now());
        assertNull(transfer.description);
    }
}
