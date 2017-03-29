/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repoes;

import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode.RETURN;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 *
 * @author jonassimonsen
 */
public class Neo4JRepo implements IRepo {

    Driver driver = GraphDatabase.driver(
            "bolt://localhost:7687",
            AuthTokens.basic("neo4j", "class"));

    @Override
    public List<String> getPersonEndorsementsLevelOne(String name) {
        return runQuery("MATCH(p:Person {name: \"" + name + "\"})"
                + "-[:ENDORSES]->(b:Person)"
                + " RETURN DISTINCT b.name as name");
    }

    @Override
    public List<String> getPersonEndorsementsLevelTwo(String name) {
        return runQuery("MATCH(p:Person {name: \"" + name + "\"})"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(b:Person) "
                + "RETURN DISTINCT b.name as name");
    }

    @Override
    public List<String> getPersonEndorsementsLevelThree(String name) {
        return runQuery("MATCH(p:Person {name: \"" + name + "\"})"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(b:Person) "
                + "RETURN DISTINCT b.name as name");
    }

    @Override
    public List<String> getPersonEndorsementsLevelFour(String name) {
        return runQuery("MATCH(p:Person {name: \"" + name + "\"})"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(b:Person) "
                + "RETURN DISTINCT b.name as name");
    }

    @Override
    public List<String> getPersonEndorsementsLevelFive(String name) {
        return runQuery("MATCH(p:Person {name: \"" + name + "\"})"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(:Person)"
                + "-[:ENDORSES]->(b:Person) "
                + "RETURN DISTINCT b.name as name");
    }

    @Override
    public List<String> getAllNames() {
        return runQuery("MATCH (p:Person) "
                + "RETURN DISTINCT p.name as name");
    }

    private List<String> runQuery(String query) {
        StatementResult result;
        try (Session session = driver.session()) {
            result = session.run(query);
        }
        
        List<String> names = new ArrayList<>();

        while (result.hasNext()) {
            Record record = result.next();
            names.add(record.get("name").asString());
        }

        return names;
    }

    @Override
    public String getRepoName() {
        return "Neo4J";
    }

}
