package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HearingInterpreterTest {

    private HearingInterpreter hearingInterpreter;

    @Before
    public void setUp() {
        this.hearingInterpreter = new HearingInterpreter(new LaurelWordProducer());
    }

    @Test
    public void whatIHear() {
        String actualHearing = this.hearingInterpreter.whatIHear();

        assertEquals(actualHearing, "Laurel");
    }
}