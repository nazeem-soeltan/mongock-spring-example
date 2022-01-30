package dev.nazeem.mongock.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

import io.mongock.driver.mongodb.springdata.v3.SpringDataMongoV3Driver;
import io.mongock.runner.springboot.EnableMongock;

@EnableMongock
@Configuration
@ConditionalOnProperty(prefix = "example.config-bean", name = "enabled", havingValue = "false")
public class EnableMongockConfiguration {

    @Bean
    SpringDataMongoV3Driver mongoV3Driver(final MongoTemplate mongoTemplate) {
        return SpringDataMongoV3Driver.withDefaultLock(mongoTemplate);
    }

    @Bean
    MongoTransactionManager transactionManager(final MongoTemplate mongoTemplate) {
        return new MongoTransactionManager(mongoTemplate.getMongoDatabaseFactory());
    }

}
