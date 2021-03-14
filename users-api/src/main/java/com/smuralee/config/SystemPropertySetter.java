package com.smuralee.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class SystemPropertySetter {

    @PostConstruct
    public void setProperty() {
        log.info("Setting the DNS cache TTL to 60 seconds");
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
    }

}
