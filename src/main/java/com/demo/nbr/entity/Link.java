package com.demo.nbr.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
@Data
public class Link {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime callTime;

    @TargetNode
    private Human person;

}
