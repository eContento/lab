package com.microbanco.account.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IbanUtilsTest {

    @Test
    void shouldGenerateValidIban() {
        String iban = IbanUtils.generateIban();
        assertNotNull(iban);
        assertEquals(24, iban.length());
        assertTrue(iban.startsWith("ES"));
        assertTrue(IbanUtils.validateIban(iban));
    }

    @Test
    void shouldGenerateIbanWithEntityPrefix() {
        String iban = IbanUtils.generateIban();
        assertEquals(24, iban.length());
        String bban = iban.substring(4);
        assertTrue(bban.startsWith("00830001"));
        assertEquals(20, bban.length());
        String digits = bban.substring(8);
        assertTrue(digits.matches("\\d{12}"));
    }

    @Test
    void shouldGenerateUniqueIbans() {
        String iban1 = IbanUtils.generateIban();
        String iban2 = IbanUtils.generateIban();
        assertNotEquals(iban1, iban2);
    }

    @Test
    void shouldValidateCorrectIban() {
        String iban = IbanUtils.generateIban();
        assertTrue(IbanUtils.validateIban(iban));
    }

    @Test
    void shouldRejectNullIban() {
        assertFalse(IbanUtils.validateIban(null));
    }

    @Test
    void shouldRejectEmptyIban() {
        assertFalse(IbanUtils.validateIban(""));
    }

    @Test
    void shouldRejectShortIban() {
        assertFalse(IbanUtils.validateIban("ES00000000000000000000"));
    }

    @Test
    void shouldRejectIbanWithInvalidCharacters() {
        assertFalse(IbanUtils.validateIban("ES00000000000000000000!"));
        assertFalse(IbanUtils.validateIban("es0000 0000 0000 0000 0000"));
    }

    @Test
    void shouldRejectIbanWithWrongCountry() {
        String iban = IbanUtils.generateIban();
        String wrongCountry = "FR" + iban.substring(2);
        assertFalse(IbanUtils.validateIban(wrongCountry));
    }

    @Test
    void shouldRejectIbanWithInvalidCheckDigits() {
        String iban = IbanUtils.generateIban();
        String tampered = iban.substring(0, 2) + "00" + iban.substring(4);
        assertFalse(IbanUtils.validateIban(tampered));
    }

    @Test
    void isEntityAccountShouldReturnTrueForEntityIban() {
        String iban = IbanUtils.generateIban();
        assertTrue(IbanUtils.isEntityAccount(iban));
    }

    @Test
    void isEntityAccountShouldReturnFalseForExternalIban() {
        String externalBban = "12345678901234567890";
        String externalIban = IbanUtils.generateIbanFromBban(externalBban);
        assertFalse(IbanUtils.isEntityAccount(externalIban));
        assertTrue(IbanUtils.validateIban(externalIban));
    }

    @Test
    void isEntityAccountShouldReturnFalseForNull() {
        assertFalse(IbanUtils.isEntityAccount(null));
    }
}
