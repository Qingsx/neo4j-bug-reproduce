package com.demo.nbr.service;

import com.demo.nbr.entity.Human;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.BiFunction;

@Service

@RequiredArgsConstructor
public class HumanService {
    private final Neo4jClient neo4jClient;
    private final Neo4jMappingContext context;

    public Collection<Human> errorMapping() {
        BiFunction<TypeSystem, MapAccessor, Human> mappingFunctionFor = context.getRequiredMappingFunctionFor(Human.class);
        return neo4jClient.query("MATCH(b:human)-[r]-(e) return b,collect(r),collect(e)")
                .fetchAs(Human.class)
                .mappedBy((t, r) -> mappingFunctionFor.apply(t, r))
                .all();
    }

    public Collection<Human> correctMapping() {
        return neo4jClient.query("MATCH(b:human)-[r]-(e) return b,collect(r),collect(e)")
                .fetchAs(Human.class)
                .mappedBy((t, r) -> context.getRequiredMappingFunctionFor(Human.class).apply(t, r))
                .all();
    }
}
