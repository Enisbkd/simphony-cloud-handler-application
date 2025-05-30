package mc.sbm.simphonycloud.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class SimphonyHierarchieService {

    @Value("${simphony.confing-and-content.api-key}")
    private String simphony_content_api_key;

    @Value("${simphony.confing-and-content.client_id}")
    private String simphony_content_clientid;

    private static final Logger log = LoggerFactory.getLogger(SimphonyHierarchieService.class);

    private final HttpClientService httpClientService;
    private final SimphonyTokenService simphonyTokenService;
    private final KafkaProducerService kafkaProducerService;

    public SimphonyHierarchieService(
        HttpClientService httpClientService,
        SimphonyTokenService simphonyTokenService,
        KafkaProducerService kafkaProducerService
    ) {
        this.httpClientService = httpClientService;
        this.simphonyTokenService = simphonyTokenService;
        this.kafkaProducerService = kafkaProducerService;
    }

    public CompletableFuture getHierarchieFromContentAPI(Map<String, Object> requestBody) {
        return httpClientService
            .executeHttpRequest(
                HttpMethod.POST,
                "/simphonycontent/v1",
                "/config/sim/v2/hierarchy/getHierarchy",
                null,
                null,
                null,
                simphony_content_api_key,
                simphonyTokenService.getCurrentTokenByClientId(simphony_content_clientid).getAccess_token(),
                requestBody,
                String.class
            )
            .thenAccept(response -> kafkaProducerService.sendMessage("Hierarchie", "key", response));
    }
}
