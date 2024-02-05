package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Ssl;

import com.example.demo.te.AutomatonStringGenerator;

import dk.brics.automaton.*;
import nl.flotsam.xeger.Xeger;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// 	"[A-Z0-9_\-.!"#$%&'()*+,/;<=>?@^|~]{2}"
		
	// "[A-Z][A-Z][A-Z]"

    public static void generateStrings(State state, StringBuilder currentString, Set<String> generatedStrings, int maxDepth) {
        if (maxDepth <= 0 || state == null) return; // Base case to avoid infinite recursion

        if (state.isAccept()) {
            generatedStrings.add(currentString.toString());
            // You might want to return; here if you only want strings of exactly maxDepth length
        }

        if (state.getTransitions().isEmpty() && state.isAccept()) {
            generatedStrings.add(currentString.toString());
        } else {
            for (Transition transition : state.getTransitions()) {
                // Iterate through the entire range of characters in the transition
                for (char c = transition.getMin(); c <= transition.getMax(); c++) {
                    StringBuilder nextString = new StringBuilder(currentString);
                    nextString.append(c);
                    generateStrings(transition.getDest(), nextString, generatedStrings, maxDepth - 1);
                }
            }
        }
    }


	@Override
	public void run(String... args) throws Exception {
		// var l = new ArrayList<>();
		
        // // Define a simple regex pattern
        // String regexPattern = "[A-Z0-9_\\-.!\"#$%&'()*+,/;<=>?@^|~]{2}";
        // Automaton automaton = new RegExp(regexPattern).toAutomaton();

        // // Get all states and transitions
        // for (State state : automaton.getStates()) {
        //     for (Transition transition : state.getTransitions()) {
        //         //Print details of each transition
        //         System.out.println("Transition from State " + state.hashCode() + 
        //                            " to State " + transition.getDest().hashCode() + 
        //                            " on characters '" + transition.getMin() + 
        //                            "' to '" + transition.getMax() + "'");				
        //     }
        // }

		// String regexPattern = "[A-Z0-9_\\-.!\"#$%&'()*+,/;<=>?@^|~]{2}";
		// AutomatonStringGenerator generator = new AutomatonStringGenerator(regexPattern);
		// Set<String> strings = generator.getGeneratedStrings();
		// strings.forEach(System.out::println);


		// String regexPattern ="ab";

		// var automaton = new RegExp(regexPattern).toAutomaton();

		// for ( State state : automaton.getStates()) {
		// 	System.out.println(state);
		// }
		

		// String regexPattern = "[A-D]{1,2}"; // Exemple avec une regex plus simple
        // Xeger generator = new Xeger(regexPattern);
        // Set<String> generatedStrings = new TreeSet<>(); // TreeSet pour trier automatiquement

        // while (generatedStrings.size() < 500) {
        //     String generated = generator.generate();
        //     generatedStrings.add(generated);
        // }

        // for (String str : generatedStrings) {
        //     System.out.println(str);
        // }

		// Comparator<String> customComparator = (o1, o2) -> {
        //     // Vérifie si les chaînes sont strictement alphabétiques
        //     boolean o1IsAlpha = o1.matches("^[A-Za-z]+$");
        //     boolean o2IsAlpha = o2.matches("^[A-Za-z]+$");

        //     // Vérifie si les chaînes sont strictement numériques
        //     boolean o1IsNumeric = o1.matches("^\\d+$");
        //     boolean o2IsNumeric = o2.matches("^\\d+$");

        //     // Priorité aux chaînes alphabétiques
        //     if (o1IsAlpha && !o2IsAlpha) return -1;
        //     if (!o1IsAlpha && o2IsAlpha) return 1;

        //     // Les chaînes numériques viennent après les alphabétiques mais avant les caractères spéciaux
        //     if (o1IsNumeric && !o2IsNumeric) return o2IsAlpha ? 1 : -1;
        //     if (!o1IsNumeric && o2IsNumeric) return o1IsAlpha ? -1 : 1;

        //     // Enfin, les chaînes contenant des caractères spéciaux sont comparées lexicographiquement
        //     return o1.compareTo(o2);
        // };

        // Set<String> generatedStrings = new TreeSet<>(customComparator);

        // generatedStrings.addAll(List.of("A", "BB", "AA", "EE", "RR", "C", "10", "2", "A1", "A!", "!@#", "1A","11A"));

        // for (String str : generatedStrings) {
        //     System.out.println(str);
        // }

		String regex = "[0-9A-Z!#]+"; // Votre expression régulière
        int maxResults = 500;

        Automaton automaton = new RegExp(regex).toAutomaton();
        RunAutomaton runAutomaton = new RunAutomaton(automaton);

		for (State state : automaton.getStates()) {
			System.out.println(state);
		}

		// Set<String> generatedStrings = new HashSet<>();
		// generateStrings(automaton.getInitialState(), new StringBuilder(), generatedStrings, 2);

		// for (String str : generatedStrings) {
		// 	System.out.println(str);
		// }
	}
}
