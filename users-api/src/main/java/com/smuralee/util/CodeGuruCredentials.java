package com.smuralee.util;

import codegurushadow.software.amazon.awssdk.auth.credentials.AwsCredentials;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

@Slf4j
public class CodeGuruCredentials implements AwsCredentials {

    private final AwsCredentialsProvider credentials;

    public CodeGuruCredentials(String roleArn, String sessionName) {
        log.info("Fetching the credentials for roleArn: {} and session: {}", roleArn, sessionName);
        this.credentials = CrossAccountClient.getCredentials(roleArn, sessionName);
    }

    @Override
    public String accessKeyId() {
        return this.credentials.resolveCredentials().accessKeyId();
    }

    @Override
    public String secretAccessKey() {
        return this.credentials.resolveCredentials().secretAccessKey();
    }
}
