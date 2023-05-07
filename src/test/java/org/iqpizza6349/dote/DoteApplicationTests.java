package org.iqpizza6349.dote;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.config.location=classpath:application-test.properties"
        }
)
class DoteApplicationTests {

    @Test
    void contextLoads() {
    }

}
