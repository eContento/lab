package com.microbanco.account.services;

import com.microbanco.account.entities.Account;
import com.microbanco.account.entities.Transfer;
import com.microbanco.account.exceptions.AccountNotFoundException;
import com.microbanco.account.exceptions.InsufficientBalanceException;
import com.microbanco.account.util.IbanUtils;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransferServiceTest {

    @Inject
    TransferService transferService;

    @Inject
    AccountService accountService;

    private Account source;
    private Account target;

    @BeforeEach
    void setUp() {
        source = accountService.openAccount("Alice", "EUR", BigDecimal.valueOf(1000));
        target = accountService.openAccount("Bob", "EUR", BigDecimal.valueOf(500));
    }

    @Test
    void shouldExecuteTransfer() {
        Transfer transfer = transferService.executeTransfer(
            source.iban, target.iban, BigDecimal.valueOf(300), "Payment");

        assertNotNull(transfer.id);
        assertEquals(source.iban, transfer.sourceAccountIban);
        assertEquals(target.iban, transfer.targetAccountIban);
        assertEquals(BigDecimal.valueOf(300), transfer.amount);
        assertEquals(0, BigDecimal.valueOf(700).compareTo(accountService.getAccount(source.iban).balance));
        assertEquals(0, BigDecimal.valueOf(800).compareTo(accountService.getAccount(target.iban).balance));
    }

    @Test
    void shouldThrowWhenSourceAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () ->
            transferService.executeTransfer(IbanUtils.generateIban(), target.iban,
                BigDecimal.valueOf(100), ""));
    }

    @Test
    void shouldThrowWhenTargetAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () ->
            transferService.executeTransfer(source.iban, IbanUtils.generateIban(),
                BigDecimal.valueOf(100), ""));
    }

    @Test
    void shouldAllowTransferToExternalAccount() {
        String externalBban = "12345678901234567890";
        String externalIban = IbanUtils.generateIbanFromBban(externalBban);

        Transfer transfer = transferService.executeTransfer(
            source.iban, externalIban, BigDecimal.valueOf(200), "To external");

        assertNotNull(transfer.id);
        assertEquals(externalIban, transfer.targetAccountIban);
        assertEquals(0, BigDecimal.valueOf(800).compareTo(accountService.getAccount(source.iban).balance));
    }

    @Test
    void shouldThrowWhenTransferToSameAccount() {
        assertThrows(IllegalArgumentException.class, () ->
            transferService.executeTransfer(source.iban, source.iban,
                BigDecimal.valueOf(100), ""));
    }

    @Test
    void shouldThrowWhenAmountIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            transferService.executeTransfer(source.iban, target.iban,
                BigDecimal.ZERO, ""));
        assertThrows(IllegalArgumentException.class, () ->
            transferService.executeTransfer(source.iban, target.iban,
                BigDecimal.valueOf(-50), ""));
    }

    @Test
    void shouldThrowWhenInsufficientBalance() {
        assertThrows(InsufficientBalanceException.class, () ->
            transferService.executeTransfer(source.iban, target.iban,
                BigDecimal.valueOf(2000), ""));
    }

    @Test
    void shouldGetTransferHistory() {
        String accountIban = source.iban;
        transferService.executeTransfer(source.iban, target.iban, BigDecimal.valueOf(100), "");

        var history = transferService.getTransferHistory(accountIban, 0, 20);
        assertFalse(history.isEmpty());
    }

    @Test
    void shouldThrowWhenHistoryForNonExistentAccount() {
        assertThrows(AccountNotFoundException.class, () ->
            transferService.getTransferHistory(IbanUtils.generateIban(), 0, 20));
    }
}
