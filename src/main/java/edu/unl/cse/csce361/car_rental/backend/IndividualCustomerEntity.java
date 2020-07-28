package edu.unl.cse.csce361.car_rental.backend;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.DateTimeException;
import java.util.List;

/**
 * Hibernate implementation of {@link IndividualCustomer}.
 */
@Entity
public class IndividualCustomerEntity extends CustomerEntity implements IndividualCustomer {

    @Embedded
    private PaymentCard paymentCard;


    public IndividualCustomerEntity() {        // required 0-argument constructor
        super();
    }

    public IndividualCustomerEntity(String name) {
        super(name);
        paymentCard = new PaymentCard();
    }

    public IndividualCustomerEntity(String name, String streetAddress1, String streetAddress2,
                                    String city, String state, String zipCode)
            throws IllegalArgumentException, NullPointerException {
        super(name, streetAddress1, streetAddress2, city, state, zipCode);
        paymentCard = null;
    }

    public IndividualCustomerEntity(String name, String streetAddress1, String streetAddress2,
                                    String city, String state, String zipCode,
                                    String paymentCardNumber, int paymentCardExpirationMonth,
                                    int paymentCardExpirationYear, String paymentCardCvv)
            throws DateTimeException, IllegalArgumentException, NullPointerException {
        super(name, streetAddress1, streetAddress2, city, state, zipCode);
        setPaymentCard(paymentCardNumber, paymentCardExpirationMonth, paymentCardExpirationYear, paymentCardCvv);
    }


    @Override
    public String getPaymentInformation() {
        return paymentCard.toString();
    }

    @Override
    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    @Override
    public IndividualCustomerEntity setPaymentCard(String cardNumber, int cardExpirationMonth, int cardExpirationYear, String cvv)
            throws DateTimeException, IllegalArgumentException, NullPointerException {
        this.paymentCard = new PaymentCard(cardNumber, cardExpirationMonth, cardExpirationYear, cvv);
        return this;
    }

    @Override
    public void updatePaymentCard(int newExpirationMonth, int newExpirationYear, String newCvv)
            throws DateTimeException, IllegalArgumentException, NullPointerException {
        paymentCard = new PaymentCard(paymentCard.getCardNumber(), newExpirationMonth, newExpirationYear, newCvv);
    }

    @Override
    boolean canRent() {
        List<RentalEntity> rentals = getRentals();
        return rentals.size() == 0 || rentals.get(rentals.size() - 1).hasBeenReturned();
    }
}
