package com.smuralee.util;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

@Slf4j
@XRayEnabled
public final class CrossAccountClient {

    public static AwsCredentialsProvider getCredentials(final String roleArn, final String sessionName) {

        log.info("Building the STS request for roleArn: {} and session: {}", roleArn, sessionName);

        final AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .roleSessionName(sessionName)
                .build();
        return StsAssumeRoleCredentialsProvider.builder()
                .stsClient(StsClient.builder().credentialsProvider(DefaultCredentialsProvider.create()).build())
                .refreshRequest(assumeRoleRequest)
                .build();

    }
}
