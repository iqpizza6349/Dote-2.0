package org.iqpizza6349.dote;

import org.iqpizza6349.dote.global.property.DodamProperties;
import org.iqpizza6349.dote.global.property.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DodamProperties.class, JwtProperties.class})
public class DoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoteApplication.class, args);
    }

}
