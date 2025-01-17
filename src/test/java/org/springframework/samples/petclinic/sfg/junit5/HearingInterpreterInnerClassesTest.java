package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Inner class inside a test.
 */
@ActiveProfiles("inner-class")
@SpringJUnitConfig(classes = HearingInterpreterInnerClassesTest.TestConfig.class)
class HearingInterpreterInnerClassesTest {

    @Profile("inner-class")
    @Configuration
    static class TestConfig {
        @Bean
        HearingInterpreter hearingInterpreter() {
            return new HearingInterpreter(new LaurelWordProducer());
        }
    }

    @Autowired
    private HearingInterpreter hearingInterpreter;

    @Test
    void whatIHear() {
        String actualHearing = this.hearingInterpreter.whatIHear();

        assertEquals("Laurel", actualHearing);
    }
}