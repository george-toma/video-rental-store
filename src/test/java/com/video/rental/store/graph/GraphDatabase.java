package com.video.rental.store.graph;

import org.junit.rules.ExternalResource;
import org.neo4j.server.CommunityBootstrapper;
import org.neo4j.server.NeoServer;
import org.neo4j.server.ServerBootstrapper;
import org.neo4j.server.database.Database;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GraphDatabase extends ExternalResource {
    private NeoServer neoServer;

    @Override
    protected void before() {
        File storeDir = new File("target/tmp/neo4j");

        ServerBootstrapper serverBootstrapper = new CommunityBootstrapper();

        //serverBootstrapper.start() also registered shutdown hook
        serverBootstrapper.start(storeDir, Optional.empty(), createConfigs());

        neoServer = serverBootstrapper.getServer();
    }

    public void deleteAllNodesAndRelationships() {
        Database database = neoServer.getDatabase();
        database.getGraph().execute("MATCH (n) DETACH DELETE n");
    }

    private Map<String, String> createConfigs() {
        Map<String, String> configs = new HashMap<>();
        configs.put("dbms.connector.http.address", "127.0.0.1:7474");
        configs.put("dbms.connector.http.enabled", "true");
        configs.put("dbms.connector.bolt.enabled", "true");
        //no authentication required
        configs.put("dbms.security.auth_enabled", "false");
        return configs;
    }

    @Override
    protected void after() {
        super.after();
    }
}
