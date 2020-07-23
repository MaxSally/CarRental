package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Hibernate implementation of {@link Model}.
 */
@Entity
public class ModelEntity implements Model {

    /* Database code */

    /**
     * Retrieves a collection of car models that are of the specified vehicle class.
     *
     * @param vehicleClass The vehicle class of interest
     * @return A set of car models
     */
    static Set<Model> getModelsByClass(VehicleClass vehicleClass) {
        Session session = HibernateUtil.getSession();
        List<Model> models = session
                .createQuery("from ModelEntity where classType=" + vehicleClass.ordinal(), Model.class)
                .list();
        return Set.copyOf(models);
    }

    /**
     * Retrieves the car model that has the specified name, if such a model exists.
     *
     * @param name The name of the car model
     * @return The specified model if it is present in the database; <code>null</code> otherwise
     */
    static Model getModelByName(String name) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Model model = null;
        try {
            model = session.bySimpleNaturalId(ModelEntity.class).load(name);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Model " + name + ". " + exception.getMessage());
        }
        return model;
    }

    static List<Model> getAllModels() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Model> lstModels = null;
        try {
            lstModels = session.createQuery("SELECT model from ModelEntity model",Model.class).list();
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load all Models " + exception.getMessage());
        }
        return lstModels;
    }

    /* POJO code */

    /**
     * The upper bound on the length of a model name
     */
    public static final int MAXIMUM_NAME_LENGTH = 48;

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(length = MAXIMUM_NAME_LENGTH)   // apparently we have to define the size to enforce NaturalID's uniqueness?
    private String model;

    @Column(length = MAXIMUM_NAME_LENGTH)
    private String manufacturer;
    @Column
    private VehicleClass classType;
    @Column
    private Integer numberOfDoors;
    @Column
    private Transmission transmission;
    @Column
    private Fuel fuel;
    @Column
    private Integer fuelEconomyMPG;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private Set<CarEntity> cars;        // depends on concretion for database purposes


    public ModelEntity() {      // required 0-argument constructor
        super();
    }

    public ModelEntity(String manufacturer, String model, VehicleClass classType, Integer numberOfDoors,
                       Transmission transmission, Fuel fuel, Integer fuelEconomyMPG) {
        super();
        this.manufacturer = Objects.requireNonNullElse(manufacturer, "Unknown");
        this.model = model;
        this.classType = classType;
        this.numberOfDoors = numberOfDoors;
        this.transmission = transmission;
        this.fuel = fuel;
        setFuelEconomyMPG(fuelEconomyMPG);
        cars = new HashSet<>();
    }

    public static final double KILOMETERS_PER_MILE = 1.609344;
    public static final double LITERS_PER_GALLON = 3.785411784;


    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public VehicleClass getClassType() {
        return classType;
    }

    @Override
    public Optional<Integer> getNumberOfDoors() {
        return Optional.ofNullable(numberOfDoors);
    }

    @Override
    public Transmission getTransmission() {
        return transmission;
    }

    @Override
    public Fuel getFuel() {
        return fuel;
    }

    @Override
    public Optional<Integer> getFuelEconomyMPG() {
        return Optional.ofNullable(fuelEconomyMPG);
    }

    private void setFuelEconomyMPG(Integer fuelEconomyMPG) {
        if ((fuelEconomyMPG != null) && (fuelEconomyMPG <= 0)) {
            throw new IllegalArgumentException("Inappropriate fuel economy: " + fuelEconomyMPG +
                    " MPG. Fuel economy must be a positive value.");
        }
        this.fuelEconomyMPG = fuelEconomyMPG;
    }

    @Override
    public Optional<Integer> getFuelEconomyKPL() {
        return Optional.ofNullable(fuelEconomyMPG)
                .map(economyMPG -> Math.round((float) (economyMPG * KILOMETERS_PER_MILE / LITERS_PER_GALLON)));
    }

    private void setFuelEconomyKPL(Integer fuelEconomyKPL) {
        if ((fuelEconomyKPL != null) && (fuelEconomyKPL <= 0)) {
            throw new IllegalArgumentException("Inappropriate fuel economy: " + fuelEconomyKPL +
                    " km per liter. Fuel economy must be a positive value.");
        }
        if (fuelEconomyKPL == null) {
            fuelEconomyMPG = null;
        } else {
            double economy = fuelEconomyKPL * LITERS_PER_GALLON / KILOMETERS_PER_MILE;
            fuelEconomyMPG = Math.round((float) economy);
        }
    }

    @Override
    public Optional<Double> getFuelEconomyLP100K() {
        return Optional.ofNullable(fuelEconomyMPG)
                .map(economyMPG -> (100 * LITERS_PER_GALLON) / (fuelEconomyMPG * KILOMETERS_PER_MILE));
    }

    private void setFuelEconomyLP100K(Double fuelEconomyLP100K) {
        if ((fuelEconomyLP100K != null) && (fuelEconomyLP100K <= 0)) {
            throw new IllegalArgumentException("Inappropriate fuel economy: " + fuelEconomyLP100K +
                    " L/100km. Fuel economy must be a positive value.");
        }
        if (fuelEconomyLP100K == null) {
            fuelEconomyMPG = null;
        } else {
            double economy = (100 * LITERS_PER_GALLON) / (fuelEconomyLP100K * KILOMETERS_PER_MILE);
            fuelEconomyMPG = Math.round((float) economy);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (manufacturer.equalsIgnoreCase("unknown")) {
            stringBuilder.append(model).append(" (Unknown manufacturer)");
        } else {
            stringBuilder.append(manufacturer).append(" ").append(model);
        }
        stringBuilder.append(" -- ").append(Objects.requireNonNullElse(numberOfDoors, "unknown")).append("-door")
                .append(", ").append(transmission.toString().toLowerCase()).append(" transmission")
                .append(", ").append(classType.toString().toLowerCase()).append(" vehicle")
                .append(" -- ").append("fuel: ").append(fuel.toString().toLowerCase())
                .append(", ").append(Objects.requireNonNullElse(fuelEconomyMPG, "unknown")).append(" MPG");
        if (fuel == Fuel.PLUGIN_ELECTRIC) {
            stringBuilder.append("e");
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Model that) {
        // When sorted, we want to list by class type, then by manufacturer, then by model
        VehicleClass thisClassType = this.getClassType();
        VehicleClass thatClassType = that.getClassType();
        if (thisClassType != thatClassType) {
            return thisClassType.compareTo(thatClassType);
        }
        String thisManufacturer = this.getManufacturer();
        String thatManufacturer = that.getManufacturer();
        if ((thisManufacturer != null) && (thatManufacturer != null)
                && !thisManufacturer.equalsIgnoreCase(thatManufacturer)) {
            return thisManufacturer.compareToIgnoreCase(thatManufacturer);
        }
        return this.getModel().compareTo(that.getModel());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ModelEntity)) return false;
        ModelEntity that = (ModelEntity) other;
        return this.getModel().equals(that.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel());
    }

    @Override
    public void addCar(Car car) {
        if (car instanceof CarEntity) {
            CarEntity carEntity = (CarEntity) car;
            cars.add(carEntity);
            carEntity.setModel(this);
        } else {
            throw new IllegalArgumentException("Expected CarEntity, got " + car.getClass().getSimpleName());
        }
    }

    @Override
    public Set<Car> getCars() {
        return Collections.unmodifiableSet(cars);
    }

    /**
     * Dissociates the designated {@link Car} from this car model. Under normal usage, there should be no reason for
     * this dissociation, but this method is provided to manage the dissociation if it should become necessary.
     *
     * @param car The {@link Car} to detach from this car model
     */
    public void removeCar(Car car) {
        if (car instanceof CarEntity) {
            CarEntity carEntity = (CarEntity) car;
            cars.add(carEntity);
            carEntity.setModel(null);
        } else {
            throw new IllegalArgumentException("Expected CarEntity, got " + car.getClass().getSimpleName());
        }
    }
}