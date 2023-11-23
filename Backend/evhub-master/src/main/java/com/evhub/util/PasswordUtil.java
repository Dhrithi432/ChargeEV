package com.evhub.util;

import com.evhub.exception.AppsException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    public static String generateEncodedPassword(PasswordEncoder passwordEncoder, String plainPassword) throws AppsException {
        String encodedPassword;

        try {
            encodedPassword = passwordEncoder.encode(plainPassword);
        } catch (Exception e) {
            throw new AppsException("User is unauthorized for action");
        }

        return encodedPassword;
    }
}
