package mc.sbm.simphonycloud.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SignInResponseUtils {

    public static String extractAuthCode(String redirectUrl) {
        try {
            URI uri = new URI(redirectUrl);
            String query = uri.getQuery(); // e.g. "code=...&state=..."

            if (query != null) {
                Map<String, String> params = new HashMap<>();
                for (String param : query.split("&")) {
                    String[] pair = param.split("=", 2); // limit to 2 to support '=' in value
                    if (pair.length == 2) {
                        params.put(pair[0], pair[1]);
                    }
                }
                return params.get("code"); // returns null if not found
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null; // return null if extraction fails
    }
}
