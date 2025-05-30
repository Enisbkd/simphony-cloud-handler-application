package mc.sbm.simphonycloud.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${simphony.oidc.hostname}")
    private String oidc_hostname;

    public CookieUtils() {}

    public void writeCookiesToCurlFormat(List<String> setCookieHeaders, String clientId) {
        Path cookiePath = Paths.get("cookies-" + clientId + ".txt");
        String host = getHostDomain(oidc_hostname);

        try (BufferedWriter writer = Files.newBufferedWriter(cookiePath)) {
            for (String header : setCookieHeaders) {
                String[] cookieParts = header.split(";", 2); // Take only name=value
                if (cookieParts.length > 0 && cookieParts[0].contains("=")) {
                    String[] nameValue = cookieParts[0].split("=", 2);
                    String name = nameValue[0].trim();
                    String value = nameValue.length > 1 ? nameValue[1].trim() : "";

                    // Format: #HttpOnly_HOST  FALSE / FALSE 0 name value
                    writer.write(String.format("#HttpOnly_%s\tFALSE\t/\tFALSE\t0\t%s\t%s", host, name, value));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write cookies to file", e);
        }
    }

    private String getHostDomain(String fullHostUrl) {
        try {
            URI uri = new URI(fullHostUrl);
            return uri.getHost();
        } catch (Exception e) {
            throw new RuntimeException("Invalid HOST URL: " + fullHostUrl, e);
        }
    }

    public String readCookiesFromCurlFile(String clientId) {
        Path path = Paths.get("cookies-" + clientId + ".txt");
        try {
            return Files.lines(path)
                .filter(line -> line.startsWith("#"))
                .map(line -> {
                    String[] parts = line.split("\t");
                    return parts.length >= 7 ? parts[5] + "=" + parts[6] : "";
                })
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("; "));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read cookies from file", e);
        }
    }
}
