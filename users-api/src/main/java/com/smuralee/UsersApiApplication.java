package com.smuralee;

import codegurushadow.software.amazon.codeguruprofilerjavaagent.Profiler;
import com.smuralee.util.CodeGuruCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UsersApiApplication {

    private final Environment environment;

    public UsersApiApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(UsersApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void init() {
        final String codeguruRoleArn = this.environment.getProperty("CODEGURU_ROLE");
        new Profiler.Builder()
                .profilingGroupName("microservices")
                .awsCredentialsProvider(() -> new CodeGuruCredentials(codeguruRoleArn, "users-profiling"))
                .withHeapSummary(true)
                .build().start();
    }
}
