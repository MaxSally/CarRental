package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Hibernate representation of rental association between a {@link Customer} and a {@link Car} between certain dates.
 */
@Entity
public class RentalEntity implements Comparable<RentalEntity> {

    @Id
    @GeneratedValue
    private Long rentalId;

    @Column(nullable = false)
    private LocalDate rentalStart;
    @Column
    private LocalDate rentalEnd;
    @Column
    private int dailyRate;
    @Column
    private long paidSoFar;

    @ManyToOne(fetch = FetchType.EAGER)
    private CarEntity car;              // depends on concretion for database purposes
    @ManyToOne(fetch = FetchType.EAGER)
    private CustomerEntity customer;    // depends on concretion for database purposes


    public RentalEntity() {     // required 0-argument constructor
    }

    RentalEntity(Customer customer, Car car, int dailyRate)
            throws IllegalArgumentException, NullPointerException {
        if (dailyRate < 0) {
            throw new IllegalArgumentException("The daily rate for a rental cannot be a negative value.");
        }
        if (customer == null) {
            throw new NullPointerException("Customer cannot be null.");
        }
        if (car == null) {
            throw new NullPointerException("Car cannot be null.");
        }
        this.dailyRate = dailyRate;
        rentalStart = LocalDate.now();
        rentalEnd = null;
        paidSoFar = 0;
        customer.addRental(this);
        car.addRental(this);
    }

    /**
     * Provides the amount that the {@link Customer} owes. If the rental is complete, then this is computed through
     * the end of the rental. If the rental is still on-going, then this is computed through the current date.
     *
     * @return The amount that the customer owes so far
     */
    public long amountDue() {
        LocalDate computationEndDate = Optional.ofNullable(rentalEnd).orElseGet(LocalDate::now);
        long totalPrice;
        try {
            long duration = DAYS.between(rentalStart, computationEndDate);
            totalPrice = dailyRate * duration;
        } catch (ArithmeticException | DateTimeException exception) {
            System.err.println("An impossible error occurred: " + exception.getMessage());
            System.err.println("Please inform the programmer that a " + exception.getClass().getSimpleName());
            System.err.println("    occurred in RentalEntity.makePayment(long).");
            System.err.println("    rentalStart = " + rentalStart + "\t\tcomputationEndDate = " + computationEndDate);
            System.err.println("Resuming with bogus total price of the rental.");
            totalPrice = Long.MAX_VALUE;
        }
        return totalPrice - paidSoFar;
    }

    /**
     * Applies a complete or partial payment to the rental, reducing the amount owed.
     *
     * @param payment The amount to be applied to the customer's bill
     * @return The amount the customer still owes after this payment
     * @throws IllegalArgumentException if the payment is a negative value
     */
    public long makePayment(long payment)
            throws IllegalArgumentException {
        if (payment < 0) {
            throw new IllegalArgumentException("A payment cannot be a negative value.");
        }
        paidSoFar += payment;
        return amountDue();
    }

    /**
     * Returns the car to the rental agency, closing-out the rental. The association remains for auditing purposes,
     * but the rental's end date is set.
     *
     * @return The amount the customer owes for the rental
     */
    public long returnCar() {
        rentalEnd = LocalDate.now();
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        try {
            session.beginTransaction();
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("error: " + e);
            session.getTransaction().rollback();
        }
        return amountDue();
    }

    public CarEntity getCar() {
        return car;
    }

    /**
     * Indicates whether or not this is still an active rental or if the car has been returned.
     *
     * @return <code>true</code> if this rental has been closed-out; <code>false</code> otherwise
     */
    public boolean hasBeenReturned() {
        return (rentalEnd != null);
    }

    @Override
    public int compareTo(RentalEntity that) {
        if (this.rentalStart != that.rentalStart) return this.rentalStart.compareTo(that.rentalStart);
        if ((this.rentalEnd != null) && (that.rentalEnd == null)) return -1;
        if ((this.rentalEnd == null) && (that.rentalEnd != null)) return 1;
        if (this.rentalEnd != that.rentalEnd) return this.rentalEnd.compareTo(that.rentalEnd);
        if (this.car != that.car) return this.car.compareTo(that.car);
        return this.customer.getName().compareToIgnoreCase(that.customer.getName());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof RentalEntity)) return false;
        RentalEntity that = (RentalEntity) other;
        return ((this.customer == that.customer) && (this.car == that.car) && (this.rentalStart == that.rentalStart));
    }

    @Override
    public int hashCode() {
        return customer.hashCode() + car.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(car.toString())
                .append(", rented by ")
                .append(customer)
                .append(" on ")
                .append(rentalStart);
        if (rentalEnd != null) {
            stringBuilder.append(" and returned on ")
                    .append(rentalEnd);
        }
        return stringBuilder.toString();
    }

    /* hooks for establishing the many-to-one relationships */

    void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    void setCar(CarEntity car) {
        this.car = car;
    }

    /* test hooks */

    void setDailyRate(int dailyRate)
            throws IllegalArgumentException {
        if (dailyRate >= 0) {
            this.dailyRate = dailyRate;
        } else {
            throw new IllegalArgumentException("The daily rate for a rental cannot be a negative value.");
        }
    }

    void setRentalStart(LocalDate startDate)
            throws IllegalArgumentException {
        if ((rentalEnd == null) || rentalEnd.isAfter(startDate)) {
            rentalStart = startDate;
        } else {
            throw new IllegalArgumentException("The rental start date (" + startDate +
                    ") must be before the rental end date (" + rentalEnd + ").");
        }
    }

    void setRentalEnd(LocalDate endDate)
            throws IllegalArgumentException {
        if (rentalStart.isBefore(endDate)) {
            rentalEnd = endDate;
        } else {
            throw new IllegalArgumentException("The rental end date (" + endDate +
                    ") must be after the rental start date (" + rentalStart + ").");
        }
    }
}
