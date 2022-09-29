package com.demo.nbr.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Node("human")
public class Human {
    @Id
    private String name;
    private LocalDateTime birth;


    @Relationship(type = "CALL_TO", direction = Relationship.Direction.OUTGOING)
    private List<Link> callList;
    @Relationship(type = "CALL_TO", direction = Relationship.Direction.INCOMING)
    private List<Link> beCallList;


    public Human() {
        callList = new ArrayList<>();
        beCallList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", callList=" + callList.size() +
                ", beCallList=" + beCallList.size() +
                '}';
    }
}
