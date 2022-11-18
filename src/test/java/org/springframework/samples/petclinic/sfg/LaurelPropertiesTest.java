package org.springframework.samples.petclinic.sfg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("/laurel.properties")
@ActiveProfiles("laurel-properties")
@SpringJUnitConfig(classes = LaurelPropertiesTest.TestConfig.class)
class LaurelPropertiesTest {
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig {

    }

    @Autowired
    private HearingInterpreter hearingInterpreter;

    @Test
    void whatIHear() {
        String actualHearing = this.hearingInterpreter.whatIHear();

        assertEquals("LaUrEl", actualHearing);
    }
}