package edu.unl.cse.csce361.car_rental.backend;

/**
 * Represents cars that can be rented. Car implementations are "base" {@link PricedItem}s that do not wrap other
 * {@link PricedItem} objects.
 */
public interface Car extends PricedItem, Comparable<Car> {

    /**
     * Provides the unique, unchanging Vehicle Identifier Number (VIN) for the car.
     *
     * @return The Vehicle's VIN
     */
    String getVin();

    /**
     * Provides the name of the car's "make" (aka, the manufacturer).
     *
     * @return The name of the car's manufacturer
     */
    String getMake();

    /**
     * Provides the name of the car's model. Does <i>not</i> return a {@link Model} object.
     *
     * @return The name of the car's model
     */
    String getModel();

    /**
     * Provides the car's color. Because manufacturers use different names for the similar colors (<i>i.e.</i>,
     * "seafoam" vs. "cyan", the providing a meaningful sort by color may be challenging.
     *
     * @return The car's color
     */
    String getColor();

    /**
     * Provides the car's license plate number (which, of course, may also contain letters and spaces). While unique,
     * the license plate for a particular car may change.
     *
     * @return The car's license plate number
     */
    String getLicensePlate();

    /**
     * Indicates whether a car is available to be rented.
     *
     * @return <code>true</code> if the car is available to be rented by a {@link Customer};
     * <code>false</code> otherwise
     */
    boolean isAvailable();

    /**
     * Assigns this car to a {@link RentalEntity} object, indicating that the car is being rented by a {@link Customer}
     * (if the rental has not yet ended) or that the car was rented by a {@link Customer} between the date bounds of the
     * rental.
     * <p>
     * While you <i>can</i> create an empty {@link RentalEntity} object and call this method directly, it would be
     * better to use {@link Customer#rentCar(PricedItem)} to create and populate the {@link RentalEntity} object.
     *
     * @param rental The {@link RentalEntity} that the car is to be added to
     */
    void addRental(RentalEntity rental);

    String getDescription();

    boolean moveToGarage();

    boolean moveOutOfGarage();

    boolean removeCar();
}
