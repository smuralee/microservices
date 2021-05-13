package com.smuralee;

import codegurushadow.software.amazon.codeguruprofilerjavaagent.Profiler;
import com.smuralee.util.CodeGuruCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ProductOrdersApiApplication {

    private final Environment environment;

    public ProductOrdersApiApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductOrdersApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        final String codeguruRoleArn = this.environment.getProperty("CODEGURU_ROLE");
        new Profiler.Builder()
                .profilingGroupName("microservices")
                .awsCredentialsProvider(() -> new CodeGuruCredentials(codeguruRoleArn, "orders-profiling"))
                .withHeapSummary(true)
                .build().start();
    }


}
