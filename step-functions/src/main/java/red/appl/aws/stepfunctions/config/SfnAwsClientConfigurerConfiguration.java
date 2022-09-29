package red.appl.aws.stepfunctions.config;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

/**
 * Configuration for step function client. Spring Cloud AWS is not
 * fully integrated with step functions, but will still provide an
 * injected AwsCredentialsProvider.
 */
@Configuration
@RequiredArgsConstructor
public class SfnAwsClientConfigurerConfiguration {

    private final AwsCredentialsProvider defaultSpringCloudAwsCredentialsProvider;

    @Bean
    SfnClient defaultStepFunctionClient(@Value("${spring.cloud.aws.endpoint}") String awsEndpointUri) {
        return software.amazon.awssdk.services.sfn.SfnClient.builder()
            .endpointOverride(URI.create(awsEndpointUri))
            .credentialsProvider(defaultSpringCloudAwsCredentialsProvider)
            .region(Region.US_EAST_1)
            .build();
    }

}
