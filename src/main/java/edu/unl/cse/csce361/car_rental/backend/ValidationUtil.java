package edu.unl.cse.csce361.car_rental.backend;

import edu.unl.cse.csce361.car_rental.backend.Model;
import org.dom4j.rule.Mode;

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

    public static boolean isEmptyString(String checker) {
        return (checker.equals("") || checker == null);
    }

    public static boolean availabilityCriteriaChecker(String invalidCase, String criteriaFilter, String input) {
        invalidCase = invalidCase.toLowerCase();
        criteriaFilter = criteriaFilter.toLowerCase();
        input = input.toLowerCase();
        return (criteriaFilter.equals(invalidCase)) || (!criteriaFilter.equals(invalidCase) && input.equals(criteriaFilter));
    }

    //I dont know how to do generalize.
    public static Model.VehicleClass checkIfItemExistInVehicleClass(String value) {
        for (Model.VehicleClass vehicleClass : Model.VehicleClass.values()) {
            if (vehicleClass.toString().toLowerCase().equals(value.toLowerCase())) {
                return vehicleClass;
            }
        }
        return Model.VehicleClass.UNKNOWN;
    }

    public static Model.Fuel checkIfItemExistInFuelType(String value) {
        for (Model.Fuel fuel : Model.Fuel.values()) {
            if (fuel.toString().toLowerCase().equals(value.toLowerCase())) {
                return fuel;
            }
        }
        return Model.Fuel.UNKNOWN;
    }

    public static Model.Transmission checkIfItemExistInTransmission(String value) {
        for (Model.Transmission transmission : Model.Transmission.values()) {
            if (transmission.toString().toLowerCase().equals(value.toLowerCase())) {
                return transmission;
            }
        }
        return Model.Transmission.UNKNOWN;
    }
}
