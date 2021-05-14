package com.smuralee;

import com.smuralee.util.CrossAccountClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import software.amazon.codeguruprofilerjavaagent.Profiler;

@SpringBootApplication
public class UsersApiApplication {

    public static void main(String[] args) {
        final String roleArn = System.getenv("CODEGURU_ROLE");
        Profiler.builder()
                .profilingGroupName("microservices")
                .awsCredentialsProvider(CrossAccountClient.getCredentials(roleArn, "users-profiling"))
                .withHeapSummary(true)
                .build()
                .start();
        SpringApplication.run(UsersApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
