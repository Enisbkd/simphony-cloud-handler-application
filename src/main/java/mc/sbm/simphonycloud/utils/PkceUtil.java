package mc.sbm.simphonycloud.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PkceUtil {

    private static final String CODE_VERIFIER;
    private static final String CODE_CHALLENGE;

    static {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] code = new byte[32];
            secureRandom.nextBytes(code);
            CODE_VERIFIER = Base64.getUrlEncoder().withoutPadding().encodeToString(code);

            byte[] verifierBytes = CODE_VERIFIER.getBytes(StandardCharsets.US_ASCII);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(verifierBytes);

            CODE_CHALLENGE = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PKCE", e);
        }
    }

    public static Map<String, String> getCodeVerifierAndChallenge() {
        Map<String, String> result = new HashMap<>();
        result.put("code_verifier", CODE_VERIFIER);
        result.put("code_challenge", CODE_CHALLENGE);
        return result;
    }

    public static String getCodeVerifier() {
        return CODE_VERIFIER;
    }

    public static String getCodeChallenge() {
        return CODE_CHALLENGE;
    }
}
