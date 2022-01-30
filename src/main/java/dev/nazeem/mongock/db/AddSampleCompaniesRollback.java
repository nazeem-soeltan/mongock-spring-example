package dev.nazeem.mongock.db;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import dev.nazeem.mongock.company.Company;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChangeUnit(id = "add-sample-companies-rollback", order = "2", author = "nazeem.soeltan")
public class AddSampleCompaniesRollback {

    private static final String COMPANY_NAME = "sample-mongock-rollback";

    @Execution
    public void execute(final MongoTemplate mongoTemplate) {
        final var company = Company.builder()
                .name(COMPANY_NAME)
                .build();

        mongoTemplate.save(company);

        throw new RuntimeException("Let's rollback!");
    }

    @RollbackExecution
    public void executeRollback(final MongoTemplate mongoTemplate) {
        log.info("Rolling back creation of company {}", COMPANY_NAME);

        mongoTemplate.remove(query(where("name").is(COMPANY_NAME)));
    }

}
