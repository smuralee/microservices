package com.smuralee;

import com.smuralee.util.CrossAccountClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.codeguruprofilerjavaagent.Profiler;

@SpringBootApplication
public class ProductOrdersApiApplication {

    public static void main(String[] args) {
        final String roleArn = System.getenv("CODEGURU_ROLE");
        Profiler.builder()
                .profilingGroupName("microservices")
                .awsCredentialsProvider(CrossAccountClient.getCredentials(roleArn, "orders-profiling"))
                .withHeapSummary(true)
                .build()
                .start();
        SpringApplication.run(ProductOrdersApiApplication.class, args);
    }

}
