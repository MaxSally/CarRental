package edu.unl.cse.csce361.car_rental.backend;

import java.util.HashSet;
import java.util.Set;

public class ValidationUtil {
    public static Set<Character> containsOnlyDigits(String stringToInspect) {
        Set<Character> illegalCharacters = new HashSet<>();
        for (char c : stringToInspect.toCharArray()) {
            if (!Character.isDigit(c)) {
                illegalCharacters.add(c);
            }
        }
        return illegalCharacters;
    }

    public static Set<Character> containsOnlyAlphabeticLetters(String stringToInspect) {
        Set<Character> illegalCharacters = new HashSet<>();
        for (char c : stringToInspect.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                illegalCharacters.add(c);
            }
        }
        return illegalCharacters;
    }
}
