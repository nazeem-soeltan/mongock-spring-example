package dev.nazeem.mongock.db;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.mongodb.core.MongoTemplate;

import dev.nazeem.mongock.company.Company;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ChangeUnit(id = "add-sample-companies", order = "1", author = "nazeem.soeltan")
public class AddSampleCompanies {

    private static final String NAME_FORMAT = "sample-mongock-%s";

    @Execution
    public void execute(final MongoTemplate mongoTemplate) {
        mongoTemplate.insertAll(IntStream.range(0, 5)
                .mapToObj(number -> createCompany(Integer.toString(number)))
                .collect(Collectors.toList()));
    }

    @RollbackExecution
    public void executeRollback(final MongoTemplate mongoTemplate) {
        log.info("Rolling back changes triggered - add-sample-companies");

        mongoTemplate.remove(Company.class);
    }

    private Company createCompany(final String name) {
        return Company.builder()
                .name(String.format(NAME_FORMAT, name))
                .build();
    }
}
