package com.connectbundle.connect.filter;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

@Component
public class IdGenerator {

    public String generateUniqueId(String userEmail) throws NoSuchAlgorithmException {
        String rawData = userEmail + Instant.now().toEpochMilli();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

    }
}
