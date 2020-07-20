package edu.unl.cse.csce361.car_rental.backend;

/**
 * Represents individual customers. Individual customers pay the full price of rentals and do so with
 * {@link PaymentCard}s.
 */
public interface IndividualCustomer extends Customer {
    /**
     * Provides the {@link PaymentCard} with this customer's billing information.
     *
     * @return This customer's {@link PaymentCard}
     */
    PaymentCard getPaymentCard();

    /**
     * Assigns this customer's billing information. Has the effect of creating a {@link PaymentCard} object that, if
     * necessary, can be accessed through {@link #getPaymentCard()}.
     *
     * @param cardNumber          The payment card number
     * @param cardExpirationMonth The month of the card's expiration date
     * @param cardExpirationYear  The year of the card's expiration date
     * @param cvv                 The Card Verification Value
     */
    void setPaymentCard(String cardNumber, int cardExpirationMonth, int cardExpirationYear, String cvv);

    /**
     * Updates some of this customer's billing information. This method preserves the customer's existing card number
     * but updates it with a new expiration date and CVV, such as when a card is replaced after the previous one
     * expires.
     *
     * @param newExpirationMonth The month of the card's new expiration date
     * @param newExpirationYear  The year of the card's new expiration date
     * @param newCvv             The card's new Card Verification value
     */
    void updatePaymentCard(int newExpirationMonth, int newExpirationYear, String newCvv);
}
