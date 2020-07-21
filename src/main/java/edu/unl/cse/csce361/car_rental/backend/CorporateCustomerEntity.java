package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Hibernate implementation of {@link CorporateCustomer}.
 */
@Entity
public class CorporateCustomerEntity extends CustomerEntity implements CorporateCustomer {

    /** Upper limit on the length of the account number */
    public static final int ACCOUNT_LENGTH = 11;

    @Column(nullable = false, length = ACCOUNT_LENGTH)
    private String corporateAccount;
    @Column
    private double negotiatedRate;


    public CorporateCustomerEntity() {        // required 0-argument constructor
        super();
    }

    public CorporateCustomerEntity(String name, String streetAddress1, String streetAddress2,
                                   String city, String state, String zipCode,
                                   String corporateAccount, double negotiatedRate)
            throws IllegalArgumentException, NullPointerException {
        super(name, streetAddress1, streetAddress2, city, state, zipCode);
        setCorporateAccount(corporateAccount);
        setNegotiatedRate(negotiatedRate);
    }


    @Override
    public String getAccount() {
        return corporateAccount;
    }

    @Override
    public void setCorporateAccount(String newAccount)
            throws IllegalArgumentException, NullPointerException {
        if (newAccount == null) {
            throw new NullPointerException("Corporate account number cannot be null.");
        }
        if (newAccount.length() > ACCOUNT_LENGTH) {
            throw new IllegalArgumentException("Provided corporate account number has " + newAccount.length() +
                    "digits, which exceeds the limit of " + ACCOUNT_LENGTH + " digits.");
        }
        Set<Character> illegalCharacters = ValidationUtil.containsOnlyDigits(newAccount);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("Account number can contain only decimal digits. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        int paddingNeeded = ACCOUNT_LENGTH - newAccount.length();
        String padding = "0".repeat(paddingNeeded);
        corporateAccount = padding + newAccount;
    }

    @Override
    public double getNegotiatedRate() {
        return negotiatedRate;
    }

    @Override
    public void setNegotiatedRate(double newRate)
            throws IllegalArgumentException {
        if ((newRate <= 0.0) || (newRate > 1.0)) {
            throw new IllegalArgumentException("Negotiated rate must be a multiplier between 0.0 (exclusive) and 1.0 " +
                    "(inclusive);" + newRate + "is not in this range.");
        }
        negotiatedRate = newRate;
    }

    @Override
    boolean canRent() {
        return true;
    }

    @Override
    public String getPaymentInformation() {
        return "Corporate Account #" + corporateAccount;
    }
}
