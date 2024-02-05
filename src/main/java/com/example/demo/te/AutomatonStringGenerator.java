package com.example.demo.te;

 import dk.brics.automaton.*;
import java.util.HashSet;
import java.util.Set;

public class AutomatonStringGenerator {

    private Automaton automaton;
    private Set<String> generatedStrings = new HashSet<>();

    public AutomatonStringGenerator(String regex) {
        this.automaton = new RegExp(regex).toAutomaton();
    }

    public void generateStrings(State currentState, StringBuilder currentString) {
        if (generatedStrings.size() >= 500 || currentState == null) {
            return;
        }

        if (currentState.isAccept() && currentString.length() == 2) {
            generatedStrings.add(currentString.toString());
            if (generatedStrings.size() >= 500) {
                return; // Stop if limit is reached
            }
        }

        for (Transition transition : currentState.getTransitions()) {
            for (char c = transition.getMin(); c <= transition.getMax(); c++) {
                StringBuilder nextString = new StringBuilder(currentString);
                System.out.println("current string " + currentString);
                System.out.println("state dest : " + transition.getDest());
                nextString.append(c);
                generateStrings(transition.getDest(), nextString);
            }
        }
    }

    public Set<String> getGeneratedStrings() {
        generateStrings(automaton.getInitialState(), new StringBuilder());
        return generatedStrings;
    }


}