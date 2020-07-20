package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.time.DateTimeException;
import java.util.Set;

/**
 * A façade for the Backend subsystem.
 */
public class Backend {

    private static Backend instance;

    public static Backend getInstance() {
        if (instance == null) {
            instance = new Backend();
        }
        return instance;
    }

    private Backend() {
        super();
    }

    /* RETRIEVES EXISTING OBJECTS */

    /**
     * Retrieves the customer that has the specified name, if such a customer exists.
     *
     * @param name The name of the customer
     * @return The specified customer if it is present in the database; <code>null</code> otherwise
     */
    public Customer getCustomer(String name) {
        return CustomerEntity.getCustomerByName(name);
    }

    /**
     * Retrieves a collection of car models that are of the specified vehicle class.
     *
     * @param vehicleClass The name of the vehicle class
     * @return A set of car models
     */
    public Set<Model> getModels(String vehicleClass) {
        Set<Model> models;
        try {
            Model.VehicleClass vehicleClassEnum = Model.VehicleClass.valueOf(vehicleClass.toUpperCase());
            models = ModelEntity.getModelsByClass(vehicleClassEnum);
        } catch (IllegalArgumentException | NullPointerException exception) {
            System.err.println("No such vehicle class: " + vehicleClass.toUpperCase() + ". " + exception.getMessage());
            models = Set.of();
        }
        return models;
    }

    /* CREATES  NEW OBJECTS */

    /**
     * <p>Creates a new car model with the specified parameters. The model's name must be unique. If there is a notional
     * model that has different options (such as available with an automatic transmission and also is available with
     * a manual transmission, it is common to provide "qualifiers" in the model name, such as "SUX 2000-A" and "SUX
     * 2000-M". If the model already exists, then the existing model will be returned (with the existing parameters,
     * not the specified parameters).</p>
     * <p>The model name cannot be <code>null</code>. Any other <code>null</code>able parameters may be
     * <code>null</code>, which indicates their actual values are unknown. The <code>enum</code> parameters may be
     * <code>UNKNOWN</code> to indicate their actual values are unknown.</p>
     *
     * @param manufacturer   The name of the car model's manufacturer, also known as its "make"
     * @param model          The name of the model
     * @param classType      The vehicle class, such as SUV or ECONOMY
     * @param numberOfDoors  The number of doors the vehicle has
     * @param transmission   The transmission type, such as AUTOMATIC or MANUAL
     * @param fuel           The type of fuel the vehicle uses, such as GASOLINE or PLUGIN_ELECTRIC
     * @param fuelEconomyMPG The car's fuel efficiency, measured in miles per gallon (or miles per gallon equivalent)
     * @return a new car model with the specified parameters, or an existing car model with the same model name
     * @throws IllegalStateException if a new car model by the specified model name cannot be added to the data
     *                               store, and also an existing car model by the specified name cannot be retrieved
     *                               from the data store
     */
    public Model createModel(String manufacturer, String model, Model.VehicleClass classType, Integer numberOfDoors,
                             Model.Transmission transmission, Model.Fuel fuel, Integer fuelEconomyMPG)
            throws IllegalStateException {
        Model carModel;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            carModel = new ModelEntity(manufacturer, model, classType, numberOfDoors, transmission, fuel,
                    fuelEconomyMPG);
            session.saveOrUpdate(carModel);
            session.getTransaction().commit();
        } catch (PersistenceException exception) {
            session.getTransaction().rollback();
            carModel = ModelEntity.getModelByName(model);
            if (carModel == null) {
                throw new IllegalStateException("Could not create new car model " + model + ": " +
                        exception.getMessage() + ". Could not retrieve existing car model " + model +
                        " from database.");
            }
        }
        return carModel;
    }

    /**
     * <p>Creates a new car with the specified parameters. The car's Vehicle Identification Number (VIN) must be unique.
     * The license plate must also be unique if it is not <code>null</code>. If the car already exists  (based on the
     * VIN), then the existing car will be returned (with the existing parameters, not the specified parameters)</p>
     *
     * <p>The VIN cannot be <code>null</code>. Any other  parameters may be <code>null</code>, which indicates their
     * actual values are unknown.</p>
     *
     * @param model        The name of the car's model
     * @param color        The name of the car's color
     * @param licensePlate The car's license plate number
     * @param vin          The car's Vehicle Identification Number
     * @return a new car with the specified parameters, or an existing car model with the same VIN
     */
    public Car createCar(String model, String color, String licensePlate, String vin) {
        return null;
    }

    /**
     * <p>Creates a new corporate customer with the specified parameters. The customer's name must be unique. Only
     * the second street address may be <code>null</code> (which is treated as the equivalent of a blank line);
     * similarly, only the second street address may be an empty string. If the customer already exists (based on the
     * name), then the existing customer will be returned (with the existing parameters, not the specified
     * parameters).</p>
     * <p>If a new customer is created, it is guaranteed to be a {@link CorporateCustomer}. If an existing customer is
     * retrieved, it is only guaranteed to be a {@link Customer}.</p>
     *
     * @param name             The customer's name
     * @param streetAddress1   The first line of the customer's street address
     * @param streetAddress2   The second line of the customer's street address
     * @param city             The city of the customer's address
     * @param state            The abbreviation for the state of the customer's address
     * @param zipCode          The customer's 5-digit zip code
     * @param corporateAccount The customer's corporate account number
     * @param negotiatedRate   The customer's negotiated rate
     * @return a new corporate customer with the specified parameters, or an existing customer with the same name
     * @throws IllegalStateException if an argument is too long, too short, contains illegal characters, or has an
     *                               invalid value
     * @throws NullPointerException  if an argument (other than <code>streetAddress2</code>) is <code>null</code>
     */
    public Customer createCorporateCustomer(String name, String streetAddress1, String streetAddress2,
                                            String city, String state, String zipCode,
                                            String corporateAccount, double negotiatedRate)
            throws IllegalStateException, NullPointerException {
        return null;
    }

    /**
     * <p>Creates a new individual customer with the specified parameters. The customer's name must be unique. Only
     * the second street address may be <code>null</code> (which is treated as the equivalent of a blank line);
     * similarly, only the second street address may be an empty string. If the customer already exists (based on the
     * name), then the existing customer will be returned (with the existing parameters, not the specified
     * parameters).</p>
     * <p>If a new customer is created, it is guaranteed to be a {@link IndividualCustomer}. If an existing customer is
     * retrieved, it is only guaranteed to be a {@link Customer}.</p>
     * <p>Use this method only if the payment card information is not yet known. If the payment card information is
     * known, use
     * {@link #createIndividualCustomer(String, String, String, String, String, String, String, int, int, String)}</p>
     *
     * @param name           The customer's name
     * @param streetAddress1 The first line of the customer's street address
     * @param streetAddress2 The second line of the customer's street address
     * @param city           The city of the customer's address
     * @param state          The abbreviation for the state of the customer's address
     * @param zipCode        The customer's 5-digit zip code
     * @return a new individual customer with the specified parameters, or an existing customer with the same name
     * @throws IllegalArgumentException if an argument is too long, too short, or contains illegal characters
     * @throws NullPointerException     if an argument (other than <code>streetAddress2</code>) is <code>null</code>
     */
    public Customer createIndividualCustomer(String name, String streetAddress1, String streetAddress2,
                                             String city, String state, String zipCode)
            throws IllegalArgumentException, NullPointerException {
        return null;
    }

    /**
     * <p>Creates a new individual customer with the specified parameters. The customer's name must be unique. Only
     * the second street address may be <code>null</code> (which is treated as the equivalent of a blank line);
     * similarly, only the second street address may be an empty string. If the customer already exists (based on the
     * name), then the existing customer will be returned (with the existing parameters, not the specified
     * parameters).</p>
     * <p>If a new customer is created, it is guaranteed to be a {@link IndividualCustomer}. If an existing customer is
     * retrieved, it is only guaranteed to be a {@link Customer}.</p>
     * <p>If the payment card information is not yet known, use
     * {@link #createIndividualCustomer(String, String, String, String, String, String)}</p>
     *
     * @param name                       The customer's name
     * @param streetAddress1             The first line of the customer's street address
     * @param streetAddress2             The second line of the customer's street address
     * @param city                       The city of the customer's address
     * @param state                      The abbreviation for the state of the customer's address
     * @param zipCode                    The customer's 5-digit zip code
     * @param paymentCardNumber          The customer's payment card number
     * @param paymentCardExpirationMonth The month of the payment card's expiration date
     * @param paymentCardExpirationYear  The year of the payment card's expiration date
     * @param paymentCardCvv             The payment card's Card Verification Value
     * @return a new individual customer with the specified parameters, or an existing customer with the same name
     * @throws DateTimeException        if the payment card's month or year are invalid
     * @throws IllegalArgumentException if an argument is too long, too short, or contains illegal characters
     * @throws NullPointerException     if an argument (other than <code>streetAddress2</code>) is <code>null</code>
     */
    public Customer createIndividualCustomer(String name, String streetAddress1, String streetAddress2,
                                             String city, String state, String zipCode,
                                             String paymentCardNumber, int paymentCardExpirationMonth,
                                             int paymentCardExpirationYear, String paymentCardCvv)
            throws DateTimeException, IllegalArgumentException, NullPointerException {
        return null;
    }
}
