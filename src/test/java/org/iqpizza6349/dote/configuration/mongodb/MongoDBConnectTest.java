package org.iqpizza6349.dote.configuration.mongodb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.properties"})
@ExtendWith(SpringExtension.class)
public class MongoDBConnectTest {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @BeforeEach
    public void propertyCheck() {
        System.out.println("if this is blank... occurred exception caused by database name " +
                "or uri is empty");
        System.out.println(database);
    }

    @DisplayName("connect to embedded mongoDB")
    @Test
    void connectionTest(@Autowired MongoTemplate mongoTemplate) {
        Assertions.assertThat(mongoTemplate.getDb()).isNotNull();
    }
}
