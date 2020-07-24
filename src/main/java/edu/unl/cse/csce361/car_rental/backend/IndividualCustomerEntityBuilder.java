package edu.unl.cse.csce361.car_rental.backend;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

public class IndividualCustomerEntityBuilder {
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String cardNumber;
    private String cardCVV;
    private int paymentCardExpirationMonth;
    private int paymentCardExpirationYear;

    public IndividualCustomerEntity build(){
        return new IndividualCustomerEntity(name, streetAddress1, streetAddress2, city, state, zipCode,
                cardNumber, paymentCardExpirationMonth, paymentCardExpirationYear, cardCVV);
    }

    public IndividualCustomerEntityBuilder(String name){
        this.name = name; //name is compulsory. No default for it.
        streetAddress1 = city = "Unknown";
        state = "NA";
        streetAddress2 = "";
        zipCode = "00000";
        cardNumber = "0000000000000000";
        paymentCardExpirationMonth = 1;
        paymentCardExpirationYear = 0;
        cardCVV = "000";
    }

    public IndividualCustomerEntityBuilder setStreetAddress1(String streetAddress1) {
        if(!isEmptyString(streetAddress1))
            this.streetAddress1 = streetAddress1;
        return this;
    }
    
    public IndividualCustomerEntityBuilder setStreetAddress2(String streetAddress2){
        if(!isEmptyString(streetAddress2))
            this.streetAddress2 = streetAddress2;
        return this;
    }
    
    public IndividualCustomerEntityBuilder setCity(String city){
        if(!isEmptyString(city))
            this.city = city;
        return this;
    }
    
    public IndividualCustomerEntityBuilder setState(String state){
        if(!isEmptyString(state))
            this.state = state;
        return this;
    }
    
    public IndividualCustomerEntityBuilder setZipCode(String zipCode){
        if(!isEmptyString(zipCode))
            this.zipCode = zipCode;
        return this;
    }
    
    public IndividualCustomerEntityBuilder setCardNumber(String cardNumber){
        if(!isEmptyString(cardNumber))
            this.cardNumber = cardNumber;
        return this;
    }

    public IndividualCustomerEntityBuilder setCardCVV(String cardCVV) {
        if(!isEmptyString(cardCVV))
            this.cardCVV = cardCVV;
        return this;
    }

    public IndividualCustomerEntityBuilder setPaymentCardExpirationMonth(Integer paymentCardExpirationMonth) {
        if(paymentCardExpirationMonth != null)
            this.paymentCardExpirationMonth = paymentCardExpirationMonth;
        return  this;
    }

    public IndividualCustomerEntityBuilder setPaymentCardExpirationYear(Integer paymentCardExpirationYear) {
        if(paymentCardExpirationYear != null)
            this.paymentCardExpirationYear = paymentCardExpirationYear;
        return  this;
    }
}
