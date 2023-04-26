package org.iqpizza6349.dote.global.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(
        basePackageClasses = {MongoDatabaseConfiguration.class}
)
public class MongoDatabaseConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    @SuppressWarnings("NullableProblems")
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected String getDatabaseName() {
        return database;
    }
}
