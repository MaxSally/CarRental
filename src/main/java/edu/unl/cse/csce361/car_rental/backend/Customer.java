package edu.unl.cse.csce361.car_rental.backend;

import java.util.List;

/**
 * Represents any type of customer.
 */
public interface Customer {

    /** The maximum length of each of the customer's name and two street address lines */
    int MAXIMUM_LINE_LENGTH = 48;
    /** The maximum length of the city's name */
    int MAXIMUM_CITY_LENGTH = 32;
    /** The exact length of the state's abbreviation */
    int STATE_LENGTH = 2;
    /** The exact length of the zip code (without the "plus-four") */
    int ZIPCODE_LENGTH = 5;

    /**
     * Provide's the customer's name.
     *
     * @return the customer's name
     */
    String getName();

    /**
     * Provides the customer's complete address.
     *
     * @return the customer's address
     */
    String getAddress();

    /**
     * Provides the description of the customer's payment information. How this appears will be
     * implementation-dependent.
     *
     * @return a description of the customer's payment information
     */
    String getPaymentInformation();

    /**
     * <p>Assigns this customer to a {@link RentalEntity} object, indicating that the customer is renting a {@link Car}
     * (if the rental has not yet ended) or that the customer was renting a {@link Car} between the date bounds of the
     * rental.</p>
     *
     * <p> While you <i>can</i> create an empty {@link RentalEntity} object and call this method directly, it would be
     * better to use {@link #rentCar(PricedItem)} to create and populate the {@link RentalEntity} object.</p>
     *
     * @param rental The {@link RentalEntity} that the car is to be added to
     */
    void addRental(RentalEntity rental);

    /**
     * <p>Associate this customer with the rental package (a {@link Car} plus any wrapping {@link PricedItem}s
     * representing add-ons, fees, and taxes). Provides a {@link RentalEntity} object representing this association.</p>
     *
     * <p>This is the <i>preferred</i> mechanism to create a rental, as opposed to creating an empty.</p>
     * {@link RentalEntity} object and manually populating it.
     *
     * @param rentalPackage the rental package the customer is renting; its "base" {@link PricedItem} must be a
     *                      {@link Car}
     * @return a {@link RentalEntity} representing the customer's rental of the rental package
     */
    RentalEntity rentCar(PricedItem rentalPackage);

    /**
     * Provides the full history of this customer's rentals, both active rentals and closed-out rentals.
     *
     * @return a {@link java.util.List} of {@link RentalEntity}s
     */
    List<RentalEntity> getRentals();
}
