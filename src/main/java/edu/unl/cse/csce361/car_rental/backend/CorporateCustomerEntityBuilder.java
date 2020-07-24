package edu.unl.cse.csce361.car_rental.backend;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

public class CorporateCustomerEntityBuilder {
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String bankAccount;
    private double negotiatedRate;

    public CorporateCustomerEntity build(){
        return new CorporateCustomerEntity(name, streetAddress1, streetAddress2, city, state, zipCode,
                bankAccount, negotiatedRate);
    }

    public CorporateCustomerEntityBuilder(String name){
        this.name = name; //name is compulsory. No default for it.
        streetAddress1 = city = "Unknown";
        state = "NA";
        streetAddress2 = "";
        zipCode = "00000";
        bankAccount = "00000000000";
        negotiatedRate = 1.0;
    }

    public CorporateCustomerEntityBuilder setStreetAddress1(String streetAddress1) {
        if(!isEmptyString(streetAddress1))
            this.streetAddress1 = streetAddress1;
        return this;
    }

    public CorporateCustomerEntityBuilder setStreetAddress2(String streetAddress2){
        if(!isEmptyString(streetAddress2))
            this.streetAddress2 = streetAddress2;
        return this;
    }

    public CorporateCustomerEntityBuilder setCity(String city){
        if(!isEmptyString(city))
            this.city = city;
        return this;
    }

    public CorporateCustomerEntityBuilder setState(String state){
        if(!isEmptyString(state))
            this.state = state;
        return this;
    }

    public CorporateCustomerEntityBuilder setZipCode(String zipCode){
        if(!isEmptyString(zipCode))
            this.zipCode = zipCode;
        return this;
    }

    public CorporateCustomerEntityBuilder setBankAccount(String bankAccount){
        if(!isEmptyString(bankAccount))
            this.bankAccount = bankAccount;
        return this;
    }

    public CorporateCustomerEntityBuilder setNegotiatedRate(Double negotiatedRate){
        if(negotiatedRate != null)
            this.negotiatedRate = negotiatedRate;
        return this;
    }
}
