package com.smuralee.util;


import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smuralee.config.AppConfig;
import com.smuralee.config.model.RDSSecret;
import com.smuralee.errors.SecretsNotRetrievedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@XRayEnabled
@Configuration
public class SecretsClient {

    private final AppConfig appConfig;

    @Autowired
    public SecretsClient(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public RDSSecret getSecret() {

        String secretId = this.appConfig.getRdsSecret();
        RDSSecret rdsSecret = null;
        SecretsManagerClient client = null;

        client = SecretsManagerClient.builder()
                .build();

        // Creating the secret value for the secretId
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder().secretId(secretId).build();
        GetSecretValueResponse secretValueResponse = client.getSecretValue(secretValueRequest);

        // Initialize secret value holders
        String secret = null;
        String decodedBinarySecret = null;

        // Decrypts secret using the associated KMS CMK.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (secretValueResponse.secretString() != null) {
            secret = secretValueResponse.secretString();
        } else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(secretValueResponse.secretBinary().asByteArray()));
        }

        // Your code goes here
        // ==================================================================
        if (null != secret) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                rdsSecret = objectMapper.readValue(secret, RDSSecret.class);
                log.info("Fetched the secret from AWS secrets manager");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new SecretsNotRetrievedException();
            }
        }
        // ==================================================================

        return rdsSecret;
    }

}
