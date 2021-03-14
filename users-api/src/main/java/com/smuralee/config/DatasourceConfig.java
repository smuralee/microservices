package com.smuralee.config;


import com.amazonaws.xray.AWSXRay;
import com.smuralee.config.model.RDSSecret;
import com.smuralee.util.SecretsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    private final SecretsClient secretsClient;

    @Autowired
    public DatasourceConfig(SecretsClient secretsClient) {
        this.secretsClient = secretsClient;
    }

    @Bean
    public DataSource getDataSource() {
        AWSXRay.beginSegment("users-init");

        RDSSecret secret = secretsClient.getSecret();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(secret.getJdbcDriverClassName());
        dataSourceBuilder.url(secret.getDatasourceURL());
        dataSourceBuilder.username(secret.getUsername());
        dataSourceBuilder.password(secret.getPassword());

        AWSXRay.endSegment();

        return dataSourceBuilder.build();
    }
}
