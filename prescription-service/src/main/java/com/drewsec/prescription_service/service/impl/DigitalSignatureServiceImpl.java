package com.drewsec.prescription_service.service.impl;

import com.drewsec.prescription_service.entity.DigitalSignature;
import com.drewsec.prescription_service.service.SignatureService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class DigitalSignatureServiceImpl implements SignatureService {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String serial;
    private static final String SIGNATURE_ALGO = "SHA256withRSA";

    @Value("${security.keystore.private-key}")
    private Resource privKeyResource;

    @Value("${security.keystore.certificate}")
    private Resource certResource;

    @PostConstruct
    private void init() throws Exception {
        // Load private key
        try (InputStream is = privKeyResource.getInputStream()) {
            byte[] keyBytes = is.readAllBytes();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
        }
        // Load certificate & extract public key + serial
        try (InputStream is = certResource.getInputStream()) {
            X509Certificate cert = (X509Certificate) CertificateFactory
                    .getInstance("X.509").generateCertificate(is);
            publicKey = cert.getPublicKey();
            serial = cert.getSerialNumber().toString();
        }
    }

    @Override
    public DigitalSignature sign(byte[] data) {
        try {
            Signature signer = Signature.getInstance(SIGNATURE_ALGO);
            signer.initSign(privateKey);
            signer.update(data);
            String value = Base64.getEncoder().encodeToString(signer.sign());
            return new DigitalSignature(value, SIGNATURE_ALGO, serial);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to sign data", e);
        }
    }

    @Override
    public boolean verify(byte[] data, DigitalSignature signature) {
        if (!SIGNATURE_ALGO.equals(signature.getAlgorithm()) || !serial.equals(signature.getCertificateSerial())) {
            return false;
        }
        try {
            Signature verifier = Signature.getInstance(SIGNATURE_ALGO);
            verifier.initVerify(publicKey);
            verifier.update(data);
            byte[] sigBytes = Base64.getDecoder().decode(signature.getSignatureValueBase64());
            return verifier.verify(sigBytes);
        } catch (Exception e) {
            return false;
        }
    }

}
