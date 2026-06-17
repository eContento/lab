package com.microbanco.account.entities;

import com.microbanco.account.exceptions.InsufficientBalanceException;
import com.microbanco.account.util.IbanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(IbanUtils.generateIban(), "John Doe", "EUR", BigDecimal.valueOf(1000));
    }

    @Test
    void shouldOpenAccountWithInitialBalance() {
        assertEquals("John Doe", account.ownerName);
        assertEquals("EUR", account.currency);
        assertEquals(BigDecimal.valueOf(1000), account.balance);
        assertEquals(Account.Status.ACTIVE, account.status);
        assertNotNull(account.iban);
        assertNotNull(account.createdAt);
        assertNotNull(account.updatedAt);
    }

    @Test
    void shouldDebitAccount() {
        account.debit(BigDecimal.valueOf(300));
        assertEquals(BigDecimal.valueOf(700), account.balance);
    }

    @Test
    void shouldThrowWhenDebitExceedsBalance() {
        assertThrows(InsufficientBalanceException.class, () ->
            account.debit(BigDecimal.valueOf(2000)));
    }

    @Test
    void shouldThrowWhenDebitAmountIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            account.debit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () ->
            account.debit(BigDecimal.valueOf(-100)));
    }

    @Test
    void shouldCreditAccount() {
        account.credit(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(1500), account.balance);
    }

    @Test
    void shouldThrowWhenCreditAmountIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            account.credit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () ->
            account.credit(BigDecimal.valueOf(-50)));
    }

    @Test
    void shouldAllowCloseWhenBalanceIsZero() {
        account.debit(BigDecimal.valueOf(1000));
        assertTrue(account.canClose());
        account.close();
        assertEquals(Account.Status.CLOSED, account.status);
    }

    @Test
    void shouldNotAllowCloseWithPositiveBalance() {
        assertFalse(account.canClose());
    }

    @Test
    void shouldThrowWhenDebitingClosedAccount() {
        account.debit(BigDecimal.valueOf(1000));
        account.close();
        assertThrows(IllegalStateException.class, () ->
            account.debit(BigDecimal.valueOf(100)));
    }

    @Test
    void shouldThrowWhenCreditingClosedAccount() {
        account.debit(BigDecimal.valueOf(1000));
        account.close();
        assertThrows(IllegalStateException.class, () ->
            account.credit(BigDecimal.valueOf(100)));
    }
}
