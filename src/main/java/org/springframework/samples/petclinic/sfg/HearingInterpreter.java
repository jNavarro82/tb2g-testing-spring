package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HearingInterpreter {
    private final WordProducer wordProducer;

    @Autowired
    public HearingInterpreter(WordProducer wordProducer) {
        this.wordProducer = wordProducer;
    }

    public String whatIHear() {
        String word = this.wordProducer.getWord();
        System.out.println(word);
        return word;
    }
}
