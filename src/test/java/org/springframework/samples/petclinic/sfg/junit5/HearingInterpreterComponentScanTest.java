package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ComponentScan feature.
 */
@SpringJUnitConfig(classes = HearingInterpreterInnerClassesTest.TestConfig.class)
class HearingInterpreterComponentScanTest {

    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig {

    }

    @Autowired
    private HearingInterpreter hearingInterpreter;

    @Test
    void whatIHear() {
        String actualHearing = this.hearingInterpreter.whatIHear();

        assertEquals("Laurel", actualHearing);
    }

}