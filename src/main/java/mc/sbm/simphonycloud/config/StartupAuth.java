package mc.sbm.simphonycloud.config;

import static mc.sbm.simphonycloud.service.SimphonyAuthUtils.maskToken;

import jakarta.annotation.PostConstruct;
import mc.sbm.simphonycloud.service.SimphonyAuthService;
import mc.sbm.simphonycloud.service.SimphonyTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupAuth {

    private static final Logger log = LoggerFactory.getLogger(StartupAuth.class);

    private final SimphonyTokenService simphonyTokenService;
    private final SimphonyAuthService simphonyAuthService;

    public StartupAuth(SimphonyTokenService simphonyTokenService, SimphonyAuthService simphonyAuthService) {
        this.simphonyTokenService = simphonyTokenService;
        this.simphonyAuthService = simphonyAuthService;
    }

    @Value("${simphony.bi.client_id}")
    private String bi_clientId;

    @Value("${simphony.bi.username}")
    private String bi_username;

    @Value("${simphony.bi.password}")
    private String bi_password;

    @Value("${simphony.transactional.client_id}")
    private String trans_clientId;

    @Value("${simphony.transactional.username}")
    private String trans_username;

    @Value("${simphony.transactional.password}")
    private String trans_password;

    @PostConstruct
    public void authenticateOnStartup() {
        log.info("Starting automatic authentication on application startup...");

        simphonyTokenService
            .authenticate(bi_clientId, bi_username, bi_password)
            .doOnSuccess(token -> {
                log.info("Startup BI authentication successful. Access token: {}", maskToken(token.getAccess_token()));
            })
            .doOnError(error -> {
                log.error("Startup BI authentication failed: {}", error.getMessage(), error);
            })
            .subscribe();

        simphonyTokenService
            .authenticate(trans_clientId, trans_username, trans_password)
            .doOnSuccess(token -> {
                log.info("Startup transactional authentication successful. Access token: {}", maskToken(token.getAccess_token()));
            })
            .doOnError(error -> {
                log.error("Startup transactional authentication failed: {}", error.getMessage(), error);
            })
            .subscribe();
    }
}
