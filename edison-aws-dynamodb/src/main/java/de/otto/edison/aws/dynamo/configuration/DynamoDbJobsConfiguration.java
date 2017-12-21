package de.otto.edison.aws.dynamo.configuration;

import de.otto.edison.aws.configuration.AwsProperties;
import de.otto.edison.aws.dynamo.jobs.DynamoDbJobMetaRepository;
import de.otto.edison.aws.dynamo.jobs.DynamoDbJobRepoProperties;
import de.otto.edison.aws.dynamo.jobs.DynamoDbJobRepository;
import de.otto.edison.jobs.repository.JobMetaRepository;
import de.otto.edison.jobs.repository.JobRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;

@Configuration
@ConditionalOnClass(name = "de.otto.edison.jobs.configuration.JobsConfiguration")
@EnableConfigurationProperties({DynamoDbJobRepoProperties.class, AwsProperties.class})
public class DynamoDbJobsConfiguration {

    @Bean
    @ConditionalOnProperty(name = "edison.aws.dynamodb.jobs.info-table-name")
    public JobRepository jobRepository(final DynamoDBClient dynamoDBClient,
                                       final DynamoDbJobRepoProperties dynamoDbJobRepoProperties) {
        return new DynamoDbJobRepository(dynamoDBClient, dynamoDbJobRepoProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "edison.aws.dynamodb.jobs.meta-table-name")
    public JobMetaRepository jobMetaRepository(final DynamoDBClient dynamoDBClient,
                                               final DynamoDbJobRepoProperties dynamoDbJobRepoProperties) {
        return new DynamoDbJobMetaRepository(dynamoDBClient, dynamoDbJobRepoProperties);
    }

}