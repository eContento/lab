package com.microbanco.account.services;

import com.microbanco.account.entities.Account;
import com.microbanco.account.exceptions.AccountNotFoundException;
import com.microbanco.account.util.IbanUtils;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AccountServiceTest {

    @Inject
    AccountService accountService;

    @Test
    void shouldOpenAccount() {
        Account result = accountService.openAccount("Jane Doe", "USD", BigDecimal.valueOf(500));

        assertEquals("Jane Doe", result.ownerName);
        assertEquals("USD", result.currency);
        assertEquals(0, BigDecimal.valueOf(500).compareTo(result.balance));
        assertNotNull(result.iban);
    }

    @Test
    void shouldOpenAccountWithZeroBalance() {
        Account result = accountService.openAccount("Jane Doe", "USD", BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, result.balance);
    }

    @Test
    void shouldThrowWhenOpeningWithNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () ->
            accountService.openAccount("Jane", "USD", BigDecimal.valueOf(-100)));
    }

    @Test
    void shouldThrowWhenOpeningWithBlankOwner() {
        assertThrows(IllegalArgumentException.class, () ->
            accountService.openAccount("", "USD", BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () ->
            accountService.openAccount(null, "USD", BigDecimal.ZERO));
    }

    @Test
    void shouldThrowWhenOpeningWithBlankCurrency() {
        assertThrows(IllegalArgumentException.class, () ->
            accountService.openAccount("Jane", "", BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () ->
            accountService.openAccount("Jane", null, BigDecimal.ZERO));
    }

    @Test
    void shouldGetAccountByIban() {
        Account account = accountService.openAccount("Jane Doe", "USD", BigDecimal.valueOf(500));

        Account result = accountService.getAccount(account.iban);

        assertEquals(account.iban, result.iban);
        assertEquals(account.ownerName, result.ownerName);
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () ->
            accountService.getAccount(IbanUtils.generateIban()));
    }

    @Test
    void shouldListAccounts() {
        accountService.openAccount("Jane Doe", "USD", BigDecimal.valueOf(500));

        var accounts = accountService.listAccounts(0, 20);
        assertTrue(accounts.size() >= 1);
    }

    @Test
    void shouldCloseAccount() {
        Account account = accountService.openAccount("Jane Doe", "USD", BigDecimal.ZERO);

        accountService.closeAccount(account.iban);

        Account closed = accountService.getAccount(account.iban);
        assertEquals("CLOSED", closed.status.name());
    }

    @Test
    void shouldThrowWhenClosingWithBalance() {
        Account account = accountService.openAccount("Jane Doe", "USD", BigDecimal.valueOf(500));

        assertThrows(IllegalStateException.class, () ->
            accountService.closeAccount(account.iban));
    }

    @Test
    void shouldGetBalance() {
        Account account = accountService.openAccount("Jane Doe", "USD", BigDecimal.valueOf(500));

        BigDecimal balance = accountService.getBalance(account.iban);

        assertEquals(0, BigDecimal.valueOf(500).compareTo(balance));
    }
}
