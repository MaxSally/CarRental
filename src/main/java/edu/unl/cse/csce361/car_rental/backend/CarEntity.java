package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Hibernate implementation of {@link Car}.
 */
@Entity
public class CarEntity extends PricedItemDecorator implements Car  {

    /**
     * The minimum number of characters on a license plate
     */
    public static final int LICENSE_PLATE_MINIMUM_SIZE = 1;
    /**
     * The maximum number of characters on a license plate
     */
    public static final int LICENSE_PLATE_MAXIMUM_SIZE = 7;

    /**
     * The minimum number of characters on a Vehicle Identification Number
     */
    public static final int VIN_MINIMUM_SIZE = 17;
    /**
     * The maximum number of characters on a Vehicle Identification Number
     */
    public static final int VIN_MAXIMUM_SIZE = 17;
    /**
     * Characters that cannot be part of a VIN to prevent confusion (<i>e.g.</i> visual similarity between 'I' and '1')
     */
    public static final Set<Character> DISALLOWED_VIN_LETTERS = Set.of('I', 'O', 'Q');
    /**
     * Characters that are allowed to be part of a VIN
     */
    public static final Set<Character> ALLOWABLE_VIN_CHARACTERS;

    static {
        Set<Character> characters = new HashSet<>(36);
        for (char c = 'A'; c <= 'Z'; c++) {
            characters.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            characters.add(c);
        }
        characters.removeAll(DISALLOWED_VIN_LETTERS);
        ALLOWABLE_VIN_CHARACTERS = Collections.unmodifiableSet(characters);
    }

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(length = VIN_MAXIMUM_SIZE)
    private String vin;

    @Column(unique = true, length = LICENSE_PLATE_MAXIMUM_SIZE)
    private String licensePLate;
    @Column
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private ModelEntity model;              // depends on concretion for database purposes

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<RentalEntity> rentals;     // depends on concretion for database purposes

    @Column (nullable = false)
    private boolean isRemoved;
    @Column (nullable = false)
    private boolean isUnderMaintenance;


    public CarEntity() {    // required 0-argument constructor
        super();
    }

    public CarEntity(String model, String color, String licensePlate, String vin,
                     Boolean isRemoved, Boolean isUnderMaintenance) {
        super();
        setVin(vin);
        this.color = color;
        setLicensePlate(licensePlate);
        setModel(model);
        rentals = new ArrayList<>();
        this.isRemoved = isRemoved;
        this.isUnderMaintenance = isUnderMaintenance;
    }

    @Override
    public int getDailyRate() {
        return 0;
    }

    @Override
    public String getVin() {
        return vin;
    }

    @Override
    public String getMake() {
        return Optional.ofNullable(model).map(ModelEntity::getManufacturer).orElse("");
    }

    @Override
    public String getModel() {
        return Optional.ofNullable(model).map(ModelEntity::getModel).orElse("Unknown model");
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getLicensePlate() {
        return licensePLate;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!getMake().equalsIgnoreCase("unknown")) {
            stringBuilder.append(getMake()).append(" ");
        }
        stringBuilder.append(getModel());
        stringBuilder.append(" (")
                .append((Optional.ofNullable(licensePLate).orElse("unknown or missing license plate")))
                .append(")");
        if (color != null) {
            stringBuilder.append(", ").append(getColor());
        }
//        stringBuilder.append(", VIN: ").append(vin);
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Car that) {
        // When sorted, we want to list by make, then by model, then by VIN
        String thisMake = this.getMake();
        String thatMake = that.getMake();
        if ((thisMake != null) && (thatMake != null) && (!thisMake.equalsIgnoreCase(thatMake))) {
            return thisMake.compareToIgnoreCase(thatMake);
        }
        String thisModel = this.getModel();
        String thatModel = that.getModel();
        if ((thisModel != null) && (thatModel != null) && (!thisModel.equalsIgnoreCase(thatModel))) {
            return thisModel.compareToIgnoreCase(thatModel);
        }
        return this.getVin().compareTo(that.getVin());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Car)) return false;
        Car that = (Car) other;
        return this.getVin().equals(that.getVin());
    }

    @Override
    public int hashCode() {
        return vin.hashCode();
    }

    @Override
    public void addRental(RentalEntity rental) {
        rentals.add(rental);
        rental.setCar(this);
    }

    @Override
    public boolean isAvailable() {
        return rentals.size() == 0 || rentals.get(rentals.size() - 1).hasBeenReturned();
    }

    @Override
    public String getLineItemSummary() {
        return getIndividualLineSummary(toString(), getDailyRate());
    }

    @Override
    public PricedItem getBasePricedItem() {
        return this;
    }

    private void setVin(String vin) {
        if (vin == null) {
            throw new NullPointerException("VIN cannot be null.");
        }
        if ((vin.length() < VIN_MINIMUM_SIZE) || (vin.length() > VIN_MAXIMUM_SIZE)) {
            throw new IllegalArgumentException("VIN must be between " + VIN_MINIMUM_SIZE + " and " +
                    VIN_MAXIMUM_SIZE + " characters, not " + vin.length() + ".");
        }
        Set<Character> illegalCharacters = new HashSet<>();
        for (char vinCharacter : vin.toCharArray()) {
            if (!ALLOWABLE_VIN_CHARACTERS.contains(vinCharacter)) {
                illegalCharacters.add(vinCharacter);
            }
        }
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("Illegal characters found in VIN: " + illegalCharacters);
        }
        this.vin = vin;
    }

    private void setLicensePlate(String licensePlate) {
        if ((licensePlate != null) &&
                ((licensePlate.length() < LICENSE_PLATE_MINIMUM_SIZE)
                        || (licensePlate.length() > LICENSE_PLATE_MAXIMUM_SIZE))) {
            throw new IllegalArgumentException("License plate must have between " + LICENSE_PLATE_MINIMUM_SIZE + " " +
                    "and " + LICENSE_PLATE_MAXIMUM_SIZE + " characters, not " + licensePlate.length() + ".");
        }
        this.licensePLate = licensePlate;
    }

    /* hooks for establishing the many-to-one relationship */

    private void setModel(String model) {
        ModelEntity modelEntity = null;
        try {
            modelEntity = HibernateUtil.getSession().bySimpleNaturalId(ModelEntity.class).load(model);
        } catch (Exception e) {
            System.err.println("Error while loading model: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    CarEntity.setModel(String).");
            modelEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned model.");
        } finally {
            if (modelEntity != null) {
                modelEntity.addCar(this);
            } else {
                this.model = null;
            }
        }
    }

    void setModel(ModelEntity model) {
        this.model = model;
    }

    public String getDescription(){
        return String.format("(%s %s)\n Vehicle class: %s\n %s %s\n FuelType: %s\nMPG:%s Door:%s\nDaily rate: %d",
                getMake(), getModel(), model.getClassType().toString(), getColor(), model.getTransmission().toString(), model.getFuel().toString(),
                (model.getFuelEconomyMPG() == null?"":model.getFuelEconomyMPG().get().toString()),
                (model.getNumberOfDoors() == null?"":model.getNumberOfDoors().get().toString()),
                getDailyRate());
    }

    public String getDescriptionForManager(){
        return String.format("(%s %s)\n Vehicle class: %s\n %s %s\n FuelType: %s\nMPG:%s Door:%s\nDaily rate: %d\n" +
                        "Under Maintenance: %b\n Removed: %b\n" +
                        "Rented: %b",
                getMake(), getModel(), model.getClassType().toString(), getColor(), model.getTransmission().toString(), model.getFuel().toString(),
                (model.getFuelEconomyMPG() == null?"":model.getFuelEconomyMPG().get().toString()),
                (model.getNumberOfDoors() == null?"":model.getNumberOfDoors().get().toString()),
                getDailyRate(), isUnderMaintenance, isRemoved, isAvailable());
    }

    public static List<Car> getAllCars(){
        List<Car> allCars = null;
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        try {
            allCars = session.createQuery("SELECT car FROM CarEntity car").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("error: " + e);
            session.getTransaction().rollback();
        }
        return allCars;
    }

    public static List<String> getAllColors(){
        List<String> colors = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        try {
            colors.addAll(new HashSet<String>(session.createQuery("SELECT color FROM CarEntity car").getResultList()));
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("error: " + e);
            session.getTransaction().rollback();
        }
        return colors;
    }

    public void moveToGarage(){
        isUnderMaintenance = true;
    }

    public void moveOutOfGarage(){
        isUnderMaintenance = false;
    }

    public void removeCar(){
        isRemoved = true;
    }

    public Boolean getIfRemoved() {
        return isRemoved;
    }

    public Boolean getIfUnderMaintenance() {
        return isUnderMaintenance;
    }
}