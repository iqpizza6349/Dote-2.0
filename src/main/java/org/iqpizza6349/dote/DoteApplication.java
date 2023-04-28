package org.iqpizza6349.dote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
public class DoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoteApplication.class, args);
    }

}
