package edu.unl.cse.csce361.car_rental.backend;

import java.util.Optional;
import java.util.Set;

/**
 * Represents car models.
 */
public interface Model extends Comparable<Model> {

    /**
     * Provides the name of the car model.
     *
     * @return The name of the car model
     */
    String getModel();

    /**
     * Provides the name of the model's manufacturer (aka, the "make").
     *
     * @return The name of the model's manufacturer
     */
    String getManufacturer();

    /**
     * Provides the model's "class," a broad category to describe types of vehicles.
     *
     * @return The model's {@link VehicleClass}
     */
    VehicleClass getClassType();

    /**
     * Provides the number of doors for this model. Passenger doors between the A & B pillars and between the B & C
     * pillars (if present) are considered doors. Trunk lids that access dedicated cargo space are not considered doors;
     * however, hatchbacks that access shared passenger/cargo space are considered doors.
     *
     * @return The number of doors that this model has
     */
    Optional<Integer> getNumberOfDoors();

    /**
     * Provides the type of transmission for this model.
     *
     * @return The {@link Transmission} for this model
     */
    Transmission getTransmission();

    /**
     * Provides the type of fuel used by this model.
     *
     * @return The model's {@link Fuel}
     */
    Fuel getFuel();

    /**
     * Provides the fuel economy for this vehicle, measured in miles per gallon. In the case of electric vehicles,
     * provides the "MPG-equivalent" energy economy.
     *
     * @return The fuel economy in miles per gallon
     */
    Optional<Integer> getFuelEconomyMPG();

    /**
     * Provides the fuel economy for this vehicle, measured in kilometers per liter. In the case of electric vehicles,
     * provides the "KPL-equivalent" energy economy.
     *
     * @return The fuel economy in kilometers per liter
     */
    Optional<Integer> getFuelEconomyKPL();

    /**
     * Provides the fuel economy for this vehicle, measured in liters per 100 kilometers. In the case of electric
     * vehicles, provides the "L/100K-equivalent" energy economy.
     *
     * @return The fuel economy in liters per 100 kilometers
     */
    Optional<Double> getFuelEconomyLP100K();

    /**
     * Associates a {@link Car} to this car model.
     *
     * @param car The {@link Car} to be assigned to this car model
     */
    void addCar(Car car);

    /**
     * Returns the collection of cars for this model.
     *
     * @return Cars of this model
     */
    Set<Car> getCars();

    enum VehicleClass {
        UNKNOWN,
        OTHER,
        ECONOMY,
        COMPACT,
        MIDSIZED,
        LARGE,
        MINIVAN,
        SUV,
        TRUCK
    }

    enum Transmission {
        UNKNOWN,
        OTHER,
        AUTOMATIC,
        MANUAL
    }

    enum Fuel {
        UNKNOWN,
        OTHER,
        GASOLINE,
        DIESEL,
        PLUGIN_ELECTRIC
    }
}
