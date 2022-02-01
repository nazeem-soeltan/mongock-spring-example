package dev.nazeem.mongock.db;

import static org.springframework.data.domain.Sort.DEFAULT_DIRECTION;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import dev.nazeem.mongock.company.Company;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ChangeUnit(id = "add-index-on-company-name", transactional = false, order = "2", author = "nazeem.soeltan")
public class AddIndexOnCompanyName {

    @Execution
    public void execute(final MongoTemplate mongoTemplate) {
        final var index = new Index()
                .background()
                .named("name_index")
                .on("name", DEFAULT_DIRECTION);

        mongoTemplate.indexOps(Company.class).ensureIndex(index);
    }

    @RollbackExecution
    public void executeRollback(final MongoTemplate mongoTemplate) {
        // omit.
    }

}
