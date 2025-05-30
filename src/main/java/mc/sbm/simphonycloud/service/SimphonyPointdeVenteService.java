package mc.sbm.simphonycloud.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class SimphonyPointdeVenteService {

    @Value("${simphony.transactional.api_key}")
    private String simphonyTransApiKey;

    @Value("${simphony.config-and-content.api_key}")
    private String simphony_content_api_key;

    @Value("${simphony.transactional.client_id}")
    private String simphony_trans_clientid;

    @Value("${simphony.confing-and-content.client_id}")
    private String simphony_content_clientid;

    private static final Logger log = LoggerFactory.getLogger(SimphonyPointdeVenteService.class);

    private final HttpClientService httpClientService;
    private final SimphonyTokenService simphonyTokenService;
    private final KafkaProducerService kafkaProducerService;

    public SimphonyPointdeVenteService(
        HttpClientService httpClientService,
        SimphonyTokenService simphonyTokenService,
        KafkaProducerService kafkaProducerService
    ) {
        this.httpClientService = httpClientService;
        this.simphonyTokenService = simphonyTokenService;
        this.kafkaProducerService = kafkaProducerService;
    }

    public CompletableFuture getPointdeVenteFromTransactionsAPI(Map<String, String> pathParams, Map<String, String> queryParams) {
        return httpClientService
            .executeHttpRequest(
                HttpMethod.GET,
                "/simphonytransactionservices/v1/api/v1",
                "/organizations/MOC/locations/{locRef}/revenueCenters",
                pathParams,
                queryParams,
                null,
                simphonyTransApiKey,
                simphonyTokenService.getCurrentTokenByClientId(simphony_trans_clientid).getAccess_token(),
                null,
                String.class
            )
            .thenAccept(response -> kafkaProducerService.sendMessage("PointdeVente", "key", response));
    }

    public CompletableFuture getPointdeVenteFromContentAPI(Map<String, Object> requestBody) {
        return httpClientService
            .executeHttpRequest(
                HttpMethod.POST,
                "/simphonycontent/v1",
                "/config/sim/v2/hierarchy/getRevenueCenters",
                null,
                null,
                null,
                simphonyTransApiKey,
                simphonyTokenService.getCurrentTokenByClientId(simphony_content_clientid).getAccess_token(),
                requestBody,
                String.class
            )
            .thenAccept(response -> kafkaProducerService.sendMessage("PointdeVente", "key", response));
    }
}
