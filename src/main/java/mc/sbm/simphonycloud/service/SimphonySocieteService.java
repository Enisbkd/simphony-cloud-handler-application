package mc.sbm.simphonycloud.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class SimphonySocieteService {

    @Value("${simphony.transactional.hostname}")
    private String simphonyTransHost;

    @Value("${simphony.transactional.api_key}")
    private String simphonyTransApiKey;

    @Value("${simphony.transactional.client_id}")
    private String simphony_trans_clientid;

    private static final Logger log = LoggerFactory.getLogger(SimphonySocieteService.class);

    private final HttpClientService httpClientService;
    private final SimphonyTokenService simphonyTokenService;
    private final KafkaProducerService kafkaProducerService;

    public SimphonySocieteService(
        HttpClientService httpClientService,
        SimphonyTokenService simphonyTokenService,
        KafkaProducerService kafkaProducerService
    ) {
        this.httpClientService = httpClientService;
        this.simphonyTokenService = simphonyTokenService;
        this.kafkaProducerService = kafkaProducerService;
    }

    public CompletableFuture getOrganizations(String limit, String offset) {
        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("limit", limit);
        queryParams.put("offset", offset);

        return httpClientService
            .executeHttpRequest(
                HttpMethod.GET,
                "/simphonytransactionservices/v1/api/v1",
                "/organizations",
                null,
                queryParams,
                null,
                simphonyTransApiKey,
                simphonyTokenService.getCurrentTokenByClientId(simphony_trans_clientid).getAccess_token(),
                null,
                String.class
            )
            .thenAccept(response -> kafkaProducerService.sendMessage("societe", "key", response));
    }
}
