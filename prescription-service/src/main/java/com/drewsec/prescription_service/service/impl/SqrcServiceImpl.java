package com.drewsec.prescription_service.service.impl;

import com.drewsec.prescription_service.dto.request.DecryptedPayload;
import com.drewsec.prescription_service.entity.DigitalSignature;
import com.drewsec.prescription_service.service.SqrcService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SqrcServiceImpl implements SqrcService {

    private SecretKeySpec aesKey;
    private static final String AES_ALGO = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH_BITS = 128;

    @Value("${security.aes.key-base64}")
    private String aesKeyBase64;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Base64.getDecoder().decode(aesKeyBase64);
        aesKey = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String generateEncryptedPayload(String code, DigitalSignature signature) {
        try {
            // Build JSON
            String payload = String.format(
                    "{\"code\":\"%s\",\"sig\":\"%s\",\"alg\":\"%s\",\"serial\":\"%s\"}",
                    code, signature.getSignatureValueBase64(), signature.getAlgorithm(), signature.getCertificateSerial());
            byte[] plaintext = payload.getBytes(StandardCharsets.UTF_8);

            // Generate IV
            byte[] iv = new byte[IV_LENGTH];
            java.security.SecureRandom.getInstanceStrong().nextBytes(iv);

            // Encrypt
            Cipher cipher = Cipher.getInstance(AES_ALGO);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BITS, iv);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, spec);
            byte[] cipherText = cipher.doFinal(plaintext);

            // Combine IV + ciphertext
            ByteBuffer buffer = ByteBuffer.allocate(iv.length + cipherText.length);
            buffer.put(iv);
            buffer.put(cipherText);
            return Base64.getEncoder().encodeToString(buffer.array());
        } catch (Exception e) {
            throw new IllegalStateException("Encryption failed", e);
        }
    }

    @Override
    public String generateQrBase64(String encryptedPayload) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(encryptedPayload, BarcodeFormat.QR_CODE, 300, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("QR generation failed", e);
        }
    }

    @Override
    public DecryptedPayload decrypt(String encryptedPayload) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedPayload);
            ByteBuffer buffer = ByteBuffer.wrap(combined);
            byte[] iv = new byte[IV_LENGTH];
            buffer.get(iv);
            byte[] cipherText = new byte[buffer.remaining()];
            buffer.get(cipherText);

            Cipher cipher = Cipher.getInstance(AES_ALGO);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BITS, iv);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);
            byte[] plain = cipher.doFinal(cipherText);
            String json = new String(plain, StandardCharsets.UTF_8);
            // Simple parse (consider using Jackson/Gson in production)
            var node = new ObjectMapper().readTree(json);
            return new DecryptedPayload(
                    node.get("code").asText(),
                    node.get("sig").asText(),
                    node.get("alg").asText(),
                    node.get("serial").asText()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Decryption failed", e);
        }
    }
}

