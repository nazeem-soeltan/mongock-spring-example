package dev.nazeem.mongock.configuration;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import io.mongock.driver.api.driver.ConnectionDriver;
import io.mongock.driver.mongodb.springdata.v3.SpringDataMongoV3Driver;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockApplicationRunner;


@Configuration
@ConditionalOnProperty(prefix = "example.config-bean", name = "enabled", havingValue = "true")
public class MongockConfiguration {

    @Bean
    public MongockApplicationRunner mongockApplicationRunner(
            final ConnectionDriver connectionDriver,
            final ApplicationContext springContext
    ) {
        return MongockSpringboot.builder()
                .setDriver(connectionDriver)
                .addMigrationScanPackage("dev.nazeem.mongock.db")
                .setSpringContext(springContext)
                .setEnabled(true)
                .setTransactionEnabled(true)
                .buildApplicationRunner();
    }

    @Bean
    SpringDataMongoV3Driver connectionDriver(final MongoTemplate mongoTemplate) {
        final SpringDataMongoV3Driver driver = SpringDataMongoV3Driver.withDefaultLock(mongoTemplate);
        driver.enableTransaction();

        return driver;
    }

}
