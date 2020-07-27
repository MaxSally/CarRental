package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static edu.unl.cse.csce361.car_rental.backend.ModelEntity.getModelByName;

/**
 * Hibernate implementation of {@link Customer}.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CustomerEntity implements Customer {

    private static String INVALID_STRING_INPUT = "UNKNOWN";
    /* Database code */

    /**
     * Retrieves the customer that has the specified name, if such a customer exists.
     *
     * @param name The name of the customer
     * @return The specified customer if it is present in the database; <code>null</code> otherwise
     */
    static Customer getCustomerByName(String name) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Customer customer = null;
        try {
            customer = session.bySimpleNaturalId(CustomerEntity.class).load(name);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Customer " + name + ". " + exception.getMessage());
        }
        return customer;
    }


    /* POJO code */

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long customerId;

    @NaturalId
    @Column(length = Customer.MAXIMUM_LINE_LENGTH)   // apparently we have to define the size to enforce NaturalID's uniqueness?
    private String name;

    @Column(nullable = false, length = Customer.MAXIMUM_LINE_LENGTH)
    private String streetAddress1;
    @Column(nullable = false, length = Customer.MAXIMUM_LINE_LENGTH)
    private String streetAddress2;
    @Column(nullable = false, length = Customer.MAXIMUM_CITY_LENGTH)
    private String city;
    @Column(nullable = false, length = Customer.STATE_LENGTH)
    private String state;
    @Column(nullable = false, length = Customer.ZIPCODE_LENGTH)
    private String zipCode;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<RentalEntity> rentals;   // depends on concretion for database purposes

    @Transient
    private List<Car> selectedCars;

    public CustomerEntity() {        // required 0-argument constructor
        super();
    }

    public CustomerEntity(String name){
        this.name = name;
    }

    public CustomerEntity(String name, String streetAddress1, String streetAddress2,
                          String city, String state, String zipCode)
            throws IllegalArgumentException, NullPointerException {
        this.name = name;
        setAddress(streetAddress1, streetAddress2, city, state, zipCode);
        rentals = new ArrayList<>();
        selectedCars = new ArrayList<>();
    }

    public List<Car> getSelectedCars() {
        if(selectedCars == null) {
            selectedCars = new ArrayList<>();
        }
        return selectedCars;
    }

    public void setSelectedCars(List<Car> selectedCars) {
        this.selectedCars = selectedCars;
    }

    public void addToSelectedCar(Car selectedCar) {
        selectedCars.add(selectedCar);
    }

    @Override
    public String getName() {
        return name;
    }

    public CustomerEntity setName(String name){
        this.name = name;
        return this;
    }

    @Override
    public String getAddress() {
        StringBuilder address = new StringBuilder();
        address.append(streetAddress1).append(System.lineSeparator());
        if (!streetAddress2.equals("")) {
            address.append(streetAddress2).append(System.lineSeparator());
        }
        address.append(city).append(", ").append(state).append("  ").append(zipCode);
        return address.toString();
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public CustomerEntity setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
        return this;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public CustomerEntity setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CustomerEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public CustomerEntity setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public CustomerEntity setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public CustomerEntity setAddress(String streetAddress1, String streetAddress2, String city, String state, String zipCode)
            throws IllegalArgumentException, NullPointerException {
        if ((streetAddress1 == null) || (city == null) || state == null || zipCode == null) {
            throw new NullPointerException("In an address, only the second street address line can be null.");
        }
        if (streetAddress1.isBlank() || city.isBlank() || state.isBlank() || zipCode.isBlank()) {
            throw new IllegalArgumentException("In an address, only the second street address line can be blank.");
        }
        if (streetAddress1.length() > Customer.MAXIMUM_LINE_LENGTH) {
            throw new IllegalArgumentException("First address line is " + streetAddress1.length() +
                    " characters long, exceeding maximum length of " + Customer.MAXIMUM_LINE_LENGTH + " characters.");
        }
        if ((streetAddress2 != null) && (streetAddress2.length() > Customer.MAXIMUM_LINE_LENGTH)) {
            throw new IllegalArgumentException("First address line is " + streetAddress2.length() +
                    " characters long, exceeding maximum length of " + Customer.MAXIMUM_LINE_LENGTH + " characters.");
        }
        if (city.length() > Customer.MAXIMUM_CITY_LENGTH) {
            throw new IllegalArgumentException("First address line is " + city.length() +
                    " characters long, exceeding maximum length of " + Customer.MAXIMUM_CITY_LENGTH + " characters.");
        }
        if (state.length() != Customer.STATE_LENGTH) {
            throw new IllegalArgumentException("State abbreviation must be exactly " + Customer.STATE_LENGTH + " letters");
        }
        Set<Character> illegalCharacters = ValidationUtil.containsOnlyAlphabeticLetters(state);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("State can contain only alphabetic letters. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        if (zipCode.length() != Customer.ZIPCODE_LENGTH) {
            throw new IllegalArgumentException("Zip code must contain exactly " + Customer.ZIPCODE_LENGTH + " digits");
        }
        illegalCharacters = ValidationUtil.containsOnlyDigits(zipCode);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("Zip code can contain only decimal digits. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = Optional.ofNullable(streetAddress2).orElse("");
        this.city = city;
        this.state = state.toUpperCase();
        this.zipCode = zipCode;
        return this;
    }

    @Override
    public RentalEntity rentCar(PricedItem rentalPackage)
            throws IllegalArgumentException {
        PricedItem car = rentalPackage.getBasePricedItem();
        RentalEntity newRental = null;
        if (!(car instanceof Car)) {
            throw new IllegalArgumentException(getName() + " wants to rent a Car, not a " +
                    car.getClass().getSimpleName());
        }else{
            Session session = HibernateUtil.getSession();
            System.out.println("Starting Hibernate transaction...");
            session.beginTransaction();

            try {
                newRental = new RentalEntity(this, (Car) car, rentalPackage.getDailyRate());
                newRental.setDailyRate(VehicleClassRateEntity.getVehicleRateEntityByClassType(getModelByName(((Car) car).getModel()).getClassType()).getDailyRate());
                newRental.setRentalStart(LocalDate.now());
                session.saveOrUpdate(newRental);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.err.println("error: " + e);
                session.getTransaction().rollback();
            }
        }

        return newRental;
    }

    @Override
    public void addRental(RentalEntity rental) {
        rentals.add(rental);
        rental.setCustomer(this);
    }

    public boolean updateAddress(String streetAddress1, String streetAddress2, String city, String state, String zipCode){
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        try {
            setAddress((streetAddress1.equals("") ? getStreetAddress1() : streetAddress1),
                    (streetAddress2.equals("") ? getStreetAddress2() : streetAddress2),
                    (city.equals("") ? getCity() : city), (state.equals("") ? getState() : state),
                    (zipCode.equals("") ? getZipCode() : zipCode));
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("error: " + e);
            session.getTransaction().rollback();
        }
        return true;

    }

    @Override
    public List<RentalEntity> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

    /**
     * Indicates whether this customer is eligible to rent a vehicle
     *
     * @return <code>true</code> if the customer can rent a car; <code>false</code> otherwise
     */
    abstract boolean canRent();
}
