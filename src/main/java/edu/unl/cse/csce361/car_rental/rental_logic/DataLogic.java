package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;
import edu.unl.cse.csce361.car_rental.backend.Customer;
import edu.unl.cse.csce361.car_rental.backend.CustomerEntity;

public class DataLogic {
    private static DataLogic instance;


    public static DataLogic getInstance(){
        if(instance == null){
            instance = new DataLogic();
        }
        return instance;
    }

    private DataLogic(){
        super();
    }

    public boolean logIn(String username){
        if(Backend.getInstance().getCustomer(username) == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean createIndividualCustomerAccount(String name, String streetAddress1, String streetAddress2,
                                                   String city, String state, String zipCode){
        Customer customer = Backend.getInstance().createIndividualCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        return (customer != null);
    }

    public boolean createCorporateCustomerAccount(String name, String streetAddress1, String streetAddress2,
                                                   String city, String state, String zipCode, String corporateAccount){
        return createCorporateCustomerAccountWithNegotiatedRate(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount,
                Backend.getInstance().DEFAULT_NEGOTIATED_RATE);
    }

    public boolean createCorporateCustomerAccountWithNegotiatedRate(String name, String streetAddress1, String streetAddress2,
                                                                    String city, String state, String zipCode, String corporateAccount, Double negotiatedRate) {
        Customer customer = Backend.getInstance().createCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount, negotiatedRate);
        return (customer != null);
    }

    public boolean hasCustomerName(String name){
        return (Backend.getInstance().getCustomer(name) != null);
    }

    public String getCustomerName(String name){
        Customer customer = Backend.getInstance().getCustomer(name);
        return (customer == null?"":customer.getName());
    }

    public boolean setNegotiatedRate(String name, double negotiatedRate){
        if(Backend.getInstance().updateNegotiatedRateForCorporationCustomer(name, negotiatedRate)){
            return true;
        }else{
            return false;
        }
    }
}
