package com.drewsec.prescription_service.util;

import com.drewsec.prescription_service.enumType.PrescriptionType;

import java.security.SecureRandom;
import java.util.Locale;

public class PrescriptionCodeGenerator {

    private static final String RANDOM_POOL = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_LENGTH = 7;
    private static final SecureRandom random = new SecureRandom();

    public static String generate(String facilityCode, PrescriptionType typeCode) {
        if (facilityCode == null || facilityCode.length() != 5) {
            throw new IllegalArgumentException("Facility code must be exactly 5 characters.");
        }

        if (!isValidTypeCode(typeCode)) {
            throw new IllegalArgumentException("Invalid prescription type code. Allowed: C, N, H, Y.");
        }

        String randomPart = generateRandomAlphaNumeric(RANDOM_LENGTH);
        return facilityCode.toLowerCase(Locale.ROOT) +
                randomPart + "-" +
                typeCode.toString().toLowerCase(Locale.ROOT);
    }

    private static boolean isValidTypeCode(PrescriptionType typeCode) {
        return typeCode == PrescriptionType.C ||
                typeCode == PrescriptionType.N ||
                typeCode == PrescriptionType.H ||
                typeCode == PrescriptionType.Y;
    }

    private static String generateRandomAlphaNumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_POOL.charAt(random.nextInt(RANDOM_POOL.length())));
        }
        return sb.toString();
    }
}
