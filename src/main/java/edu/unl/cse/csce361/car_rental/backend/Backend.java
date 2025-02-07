package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

/**
 * A façade for the Backend subsystem.
 */
public class Backend {

    private static Backend instance;
    private Customer currentCustomer;
    private PricedItem currentPricedItem;

    private Backend() {
        super();
    }

    public static Backend getInstance() {
        if (instance == null) {
            instance = new Backend();
        }
        return instance;
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

    public Customer getCurrentCustomer() {
        return currentCustomer;
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

    public Model getModelByName(String name){
        return ModelEntity.getModelByName(name);
    }

    /**
     * Retrieves the model that has the specified name, if such a model exists.
     *
     * @param name The name of the model
     * @return The specified model if it is present in the database; <code>null</code> otherwise
     */

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
        Model carModel = null;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            carModel = new ModelEntityBuilder().setManufacturer(manufacturer).setModel(model).setVehicleClass(classType)
                    .setNumberOfDoors(numberOfDoors).setTransmission(transmission)
                    .setFuelType(fuel).setFuelEconomy(fuelEconomyMPG).build();
            session.saveOrUpdate(carModel);
            session.getTransaction().commit();
        } catch (PersistenceException exception) {
            carModel = ModelEntity.getModelByName(model);
            if (carModel == null) {
                throw new IllegalStateException("Could not create new car model " + model + ": " +
                        exception.getMessage() + ". Could not retrieve existing car model " + model +
                        " from database.", exception);
            }
            session.getTransaction().rollback();
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
    public Car createCar(String model, String color, String licensePlate, String vin, Boolean isRemoved, Boolean isUnderMaintenance) {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        Car car = null;
        try {
            car = new CarEntityBuilder().setModel(model).setColor(color).setLicensePlate(licensePlate).setVin(vin)
                    .setIsRemoved(isRemoved).setUnderMaintenance(isUnderMaintenance).build();
            session.saveOrUpdate(car);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("encounter problems while creating car: " + e);
            session.getTransaction().rollback();
        }
        return car;
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
                                            String corporateAccount, Double negotiatedRate, boolean isManager)
            throws IllegalStateException, NullPointerException {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        CorporateCustomerEntity customer = null;
        try {
            if (!isEmptyString(name)) {
                CorporateCustomerEntityBuilder customerBuilder = new CorporateCustomerEntityBuilder(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2)
                        .setCity(city).setState(state).setZipCode(zipCode).setBankAccount(corporateAccount);
                if (isManager) {
                    customerBuilder.setNegotiatedRate(negotiatedRate);
                }
                customer = customerBuilder.build();
                session.saveOrUpdate(customer);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.err.println("Enounter problems while adding corporate customer: " + e);
            session.getTransaction().rollback();
        }
        return getCustomer(name);
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
     * {@link #createIndividualCustomer(String, String, String, String, String, String)}</p>
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
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        CustomerEntity customer = null;
        try {
            if (!isEmptyString(name)) {
                customer = new IndividualCustomerEntityBuilder(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2)
                        .setCity(city).setState(state).setZipCode(zipCode).build();
                session.saveOrUpdate(customer);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.err.println("Encounter problems while adding individual customer " + e);
            session.getTransaction().rollback();
        }
        return customer;
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
     * {@link #createIndividualCustomer(String, String, String, String, String, String, String, Integer, Integer, String)}</p>
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
                                             String paymentCardNumber, Integer paymentCardExpirationMonth,
                                             Integer paymentCardExpirationYear, String paymentCardCvv)
            throws DateTimeException, IllegalArgumentException, NullPointerException {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        Customer customer = null;
        try {
            if (!isEmptyString(name)) {
                customer = new IndividualCustomerEntityBuilder(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2)
                        .setCity(city).setState(state).setZipCode(zipCode).setCardNumber(paymentCardNumber).setCardCVV(paymentCardCvv)
                        .setPaymentCardExpirationMonth(paymentCardExpirationMonth).setPaymentCardExpirationYear(paymentCardExpirationYear).build();
                session.saveOrUpdate(customer);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.err.println("Encounter problems while creating individual customer: " + e);
            session.getTransaction().rollback();
        }
        return customer;
    }

    public boolean updateNegotiatedRateForCorporateCustomer(String name, double negotiatedRate) {
        return CustomerEntity.getCustomerByName(name) != null
                && ((CorporateCustomerEntity) CustomerEntity.getCustomerByName(name)).updateNegotiatedRate(negotiatedRate);
    }

    public boolean updateBankAccountForCorporationCustomer(String name, String bankAccountNumber) {
        return CustomerEntity.getCustomerByName(name) != null
                && ((CorporateCustomerEntity) CustomerEntity.getCustomerByName(name)).updateCorporateAccount(bankAccountNumber);
    }

    public boolean updateCardInformationForIndividualCustomer(String name, String cardNumber, String cvv,
                                                              Integer expirationMonth, Integer expirationYear) {

        IndividualCustomer customer = ((IndividualCustomerEntity) CustomerEntity.getCustomerByName(name));
        if (customer == null) {
            return false;
        } else {
            if (customer.getPaymentCard() != null && customer.getPaymentCard().isActive()) {
                customer.updatePaymentCard(expirationMonth, expirationYear, cvv);
            } else {
                customer.setPaymentCard(cardNumber, expirationMonth, expirationYear, cvv);
            }
            return true;
        }
    }

    public boolean updateAddressForCustomer(String name, String streetAddress1, String streetAddress2, String city, String state, String zipCode) {
        return getCustomer(name) != null && ((CustomerEntity) getCustomer(name)).updateAddress(streetAddress1,
                streetAddress2, city, state, zipCode);
    }

    public boolean updateDailyRateByManager(Model.VehicleClass classType, Integer dailyRate) {
        VehicleClassRateEntity vehicleClassRate = VehicleClassRateEntity.getVehicleRateEntityByClassType(classType);
        if (vehicleClassRate != null) {
            vehicleClassRate.setDailyRate(dailyRate);
            return true;
        }
        return false;
    }

    public List<Car> getAllCar() {
        return CarEntity.getAllCars();
    }

    public Model getModelEntityByName(String name) {
        return ModelEntity.getModelByName(name);
    }

    public String getFinalPriceSummary() {
        return new TotalPriceItem(currentPricedItem).getLineItemSummary();
    }

    public PricedItem addFees(String name, int cost) {
        currentPricedItem = new Fees(currentPricedItem, name, cost);
        return currentPricedItem;
    }

    public PricedItem addTax(String name, double rate) {
        currentPricedItem = new Tax(currentPricedItem, name, rate);
        return currentPricedItem;
    }

    public PricedItem addAddon(String name, int cost) {
        currentPricedItem = new AddOn(currentPricedItem, name, cost);
        return currentPricedItem;
    }

    public List<String> getAllColors() {
        return CarEntity.getAllColors();
    }

    public List<String> getAllModels() {
        List<Model> models = ModelEntity.getAllModels();
        List<String> modelAsString = new ArrayList<>();
        for (Model model : models) {
            modelAsString.add(model.getModel());
        }
        return modelAsString;
    }

    public boolean logIn(String name) {
        currentCustomer = getCustomer(name);
        return currentCustomer != null;
    }

    public boolean addSelectedCar(Car userSelectedCar) {
        if (userSelectedCar.isAvailable()) {
            for (Car car : ((CustomerEntity) currentCustomer).getSelectedCars()) {
                if (userSelectedCar.equals(car)) {
                    return false;
                }
            }
        }
        ((CustomerEntity) currentCustomer).addToSelectedCar(userSelectedCar);
        currentPricedItem = userSelectedCar;
        return true;
    }

    public Car getSelectedCar() {
        List<Car> userSelectedCars = ((CustomerEntity) currentCustomer).getSelectedCars();
        return (userSelectedCars.size() > 0 ? userSelectedCars.get(userSelectedCars.size() - 1) : null);
    }

    public String getRentedCarDescription() {
        List<RentalEntity> rentedCars = currentCustomer.getRentals();
        return (rentedCars.size() > 0 && !rentedCars.get(rentedCars.size() - 1).hasBeenReturned() ? rentedCars.get(rentedCars.size() - 1).getCar().getDescription() : "");
    }

    public boolean moveToGarage(Car selectedCar) {
        if (selectedCar.isAvailable()) {
            selectedCar.moveToGarage();
            return true;
        } else {
            return false;
        }
    }

    public boolean moveOutOfGarage(Car selectedCar) {
        if (selectedCar.isAvailable()) {
            selectedCar.moveOutOfGarage();
            return true;
        } else {
            return false;
        }
    }

    public boolean removeCar(Car selectedCar) {
        if (selectedCar.isAvailable()) {
            selectedCar.removeCar();
            return true;
        } else {
            return false;
        }
    }

    public boolean getIfRemoved(CarEntity car) {
        return car.getIfRemoved();
    }

    public boolean isIndividualCustomer() {
        return currentCustomer instanceof IndividualCustomerEntity;
    }

    public boolean isCorporateCustomer() {
        return currentCustomer instanceof CorporateCustomerEntity;
    }

    public void resetSelectedCars() {
        ((CustomerEntity) currentCustomer).setSelectedCars(new ArrayList<Car>());
    }

    public boolean rentCar() {
        if (((CustomerEntity) currentCustomer).canRent()) {
            RentalEntity rental = currentCustomer.rentCar(currentPricedItem);
            return rental != null;
        }
        return false;
    }

    public Long returnCarGetLeftoverCosts() {
        List<RentalEntity> currentRentals = currentCustomer.getRentals();
        Long amountDue = null;
        if (currentRentals.size() > 0 && !currentRentals.get(currentRentals.size() - 1).hasBeenReturned()) {
            amountDue = currentRentals.get(currentRentals.size() - 1).returnCar();
        }
        return amountDue;
    }
}