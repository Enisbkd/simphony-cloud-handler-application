package mc.sbm.simphonycloud.web.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import mc.sbm.simphonycloud.service.SimphonyEtablissementService;
import mc.sbm.simphonycloud.service.SimphonySocieteService;
import mc.sbm.simphonycloud.service.SimphonyTokenService;
import mc.sbm.simphonycloud.service.dto.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class TokenResource {

    private static final Logger log = LoggerFactory.getLogger(TokenResource.class);

    @Value("${simphony.bi.client_id}")
    private String simphony_bi_clientid;

    @Value("${simphony.transactional.client_id}")
    private String simphony_trans_clientid;

    @Value("${simphony.bi.username}")
    private String simphony_bi_username;

    @Value("${simphony.bi.password}")
    private String simphony_bi_password;

    private final SimphonyTokenService simphonyTokenService;
    private final SimphonySocieteService simphonySocieteService;
    private final SimphonyEtablissementService simphonyEtablissementService;

    public TokenResource(
        SimphonyTokenService simphonyTokenService,
        SimphonySocieteService simphonySocieteService,
        SimphonyEtablissementService simphonyEtablissementService
    ) {
        this.simphonyTokenService = simphonyTokenService;
        this.simphonySocieteService = simphonySocieteService;
        this.simphonyEtablissementService = simphonyEtablissementService;
    }

    @GetMapping("/sim-authenticate")
    public Mono<TokenResponse> complete() {
        return simphonyTokenService.authenticate(simphony_bi_clientid, simphony_bi_username, simphony_bi_password);
    }

    @GetMapping("/sim-refresh")
    public Mono<TokenResponse> refresh() {
        return simphonyTokenService.refreshCurrentToken(simphony_bi_clientid);
    }

    @GetMapping("/getCurrentBIToken")
    public TokenResponse currentBIToken() {
        return simphonyTokenService.getCurrentTokenByClientId(simphony_bi_clientid);
    }

    @GetMapping("/getCurrentTransToken")
    public TokenResponse currentTransToken() {
        return simphonyTokenService.getCurrentTokenByClientId(simphony_trans_clientid);
    }

    @GetMapping("/societesmy")
    public CompletableFuture societesResponseMono() {
        return simphonySocieteService.getOrganizations("50", "1").toCompletableFuture();
    }

    @PostMapping("/EtabContent")
    public CompletableFuture EtablissementResponse(@RequestBody Map<String, Object> requestBody) {
        return simphonyEtablissementService.getEtablissementFromContentAPI(requestBody).toCompletableFuture();
    }
}
