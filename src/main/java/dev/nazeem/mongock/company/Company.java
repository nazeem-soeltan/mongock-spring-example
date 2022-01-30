package dev.nazeem.mongock.company;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Company {

    @Id
    private ObjectId id;

    private String name;

}
