package com.microbanco.account.util;

import java.math.BigInteger;
import java.util.UUID;

public final class IbanUtils {

    private static final int IBAN_LENGTH_ES = 24;
    private static final String COUNTRY_CODE = "ES";
    private static final BigInteger MODULUS = BigInteger.valueOf(97);
    private static final BigInteger CHECK_VALUE = BigInteger.valueOf(98);

    private IbanUtils() {}

    public static String generateIban() {
        String uuidBase = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String accountPart = uuidBase.substring(0, 20);
        String partialIban = COUNTRY_CODE + "00" + accountPart;
        int checkDigits = calculateCheckDigits(partialIban);
        return COUNTRY_CODE + String.format("%02d", checkDigits) + accountPart;
    }

    public static boolean validateIban(String iban) {
        if (iban == null || iban.length() != IBAN_LENGTH_ES) {
            return false;
        }
        String cleaned = iban.toUpperCase();
        if (!cleaned.matches("^[A-Z0-9]+$")) {
            return false;
        }
        if (!cleaned.startsWith(COUNTRY_CODE)) {
            return false;
        }
        String rearranged = cleaned.substring(4) + cleaned.substring(0, 4);
        StringBuilder numeric = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                numeric.append(c - 'A' + 10);
            } else {
                numeric.append(c);
            }
        }
        return new BigInteger(numeric.toString()).mod(MODULUS).equals(BigInteger.ONE);
    }

    private static int calculateCheckDigits(String partialIban) {
        String rearranged = partialIban.substring(4) + partialIban.substring(0, 4);
        StringBuilder numeric = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                numeric.append(c - 'A' + 10);
            } else {
                numeric.append(c);
            }
        }
        BigInteger remainder = new BigInteger(numeric.toString()).mod(MODULUS);
        return CHECK_VALUE.subtract(remainder).intValue();
    }
}
