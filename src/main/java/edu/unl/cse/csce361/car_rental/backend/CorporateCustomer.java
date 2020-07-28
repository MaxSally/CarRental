package edu.unl.cse.csce361.car_rental.backend;

/**
 * Represents corporate customers. Corporate customers are distinguished by being able to rent multiple cars at once and
 * by having a negotiated rate that allows them to pay less than the "list price."
 */
public interface CorporateCustomer extends Customer {


    double DEFAULT_NEGOTIATED_RATE = 1.0;

    /**
     * Provides the corporate account that any rentals will be charged to.
     *
     * @return This customer's corporate account
     */
    String getAccount();

    /**
     * Assigns the corporate account that any rentals will be charged to.
     *
     * @param newAccount This customer's corporate account.
     */
    void setCorporateAccount(String newAccount);

    /**
     * Provides the customer's negotiated rate. The negotiated rate is a multiplier between 0.0 (exclusive) and 1.0
     * (inclusive) that will be applied to the rental's base rate to determine the actual rate charged to this
     * customer. After multiplying the negotiated rate, the rate charged to the customer will be rounded to the nearest
     * Zorkmid.
     *
     * @return This customer's negotiated rate
     */
    double getNegotiatedRate();

    /**
     * Assigns the customer's negotiated rate. The negotiated rate is a multiplier between 0.0 (exclusive) and 1.0
     * (inclusive) that will be applied to the rental's base rate to determine the actual rate charged to this
     * customer. After multiplying the negotiated rate, the rate charged to the customer will be rounded to the nearest
     * Zorkmid.
     *
     * @param newRate This customer's negotiated rate
     */
    CorporateCustomer setNegotiatedRate(double newRate);
}
