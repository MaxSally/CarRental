package edu.unl.cse.csce361.car_rental.backend;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.DateTimeException;
import java.time.Year;
import java.time.YearMonth;
import java.util.Set;

/**
 * Represents payment cards that {@link IndividualCustomer}s use to pay for rentals.
 */
@Embeddable
public class PaymentCard {
    /** The exact length of a payment card's number */
    public static final int CARD_NUMBER_LENGTH = 16;
    /** The minimum length of a card's CVV */
    public static final int CVV_MINIMUM_LENGTH = 3;
    /** The maximum length of a card's CVV */
    public static final int CVV_MAXIMUM_LENGTH = 4;

    public static final int INVALID_EXPIRATION_MONTH = 0;
    public static final int INVALID_EXPIRATION_YEAR = 0;
    @Column
    private String cardNumber;
    @Column
    private YearMonth expirationDate;
    @Column
    private String cvv;

    public PaymentCard() { // required 0-argument constructor
    }

    public PaymentCard(String cardNumber, int expirationMonth, int expirationYear, String cvv)
            throws DateTimeException, IllegalArgumentException, NullPointerException {
        this(cardNumber, Year.of(expirationYear).atMonth(expirationMonth), cvv);
    }

    public PaymentCard(String cardNumber, YearMonth expirationDate, String cvv)
            throws IllegalArgumentException, NullPointerException {
        setCardNumber(cardNumber);
        if (expirationDate.isAfter(YearMonth.now())) {
            this.expirationDate = expirationDate;
        } else {
            this.expirationDate = null;
        }
        setCardVerificationValue(cvv);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    private void setCardNumber(String cardNumber)
            throws IllegalArgumentException, NullPointerException {
        if (cardNumber == null) {
            throw new NullPointerException("Payment card number cannot be null.");
        }
        Set<Character> illegalCharacters = ValidationUtil.containsOnlyDigits(cardNumber);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("Payment card number can contain only decimal digits. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        if (cardNumber.length() != CARD_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Payment card number must be exactly " + CARD_NUMBER_LENGTH +
                    " digits,not " + cardNumber.length() + " digits.");
        }
        this.cardNumber = cardNumber;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    private void setCardVerificationValue(String cvv)
            throws IllegalArgumentException, NullPointerException {
        if (cvv == null) {
            throw new NullPointerException("Card Verification Value (CVV) cannot be null.");
        }
        Set<Character> illegalCharacters = ValidationUtil.containsOnlyDigits(cardNumber);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("Card Verification Value (CVV) can contain only decimal digits. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        if ((cvv.length() < CVV_MINIMUM_LENGTH) || (cvv.length() > CVV_MAXIMUM_LENGTH)) {
            throw new IllegalArgumentException("Card Verification Value (CVV) must be between " + CVV_MINIMUM_LENGTH +
                    " and " + CVV_MAXIMUM_LENGTH + " digits, inclusive, not " + cvv.length() + " digits.");
        }
        this.cvv = cvv;
    }

    public String toString() {
        return "*".repeat(CARD_NUMBER_LENGTH - 4) + cardNumber.substring(CARD_NUMBER_LENGTH - 4);
    }

    public boolean isActive() {
        return !cardNumber.equals("0000000000000000") && cardNumber != null && !cardNumber.equals("");
    }
}
