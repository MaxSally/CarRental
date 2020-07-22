package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;
import edu.unl.cse.csce361.car_rental.backend.Customer;
import edu.unl.cse.csce361.car_rental.backend.CustomerEntity;
import edu.unl.cse.csce361.car_rental.backend.Model;
import org.dom4j.rule.Mode;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getAllFuelType(){
        List<String> lstFuelType = new ArrayList<>();
        for(Model.Fuel fuel: Model.Fuel.values()){
            if(fuel == Model.Fuel.UNKNOWN)
                lstFuelType.add("");
            else
                lstFuelType.add(fuel.toString());
        }
        return lstFuelType;
    }

    public List<String> getAllTransmission(){
        List<String> lstTransmissionType = new ArrayList<>();
        for(Model.Transmission transmission : Model.Transmission.values()){
            if(transmission == Model.Transmission.UNKNOWN)
                lstTransmissionType.add("");
            else
                lstTransmissionType.add(transmission.toString());
        }
        return lstTransmissionType;
    }

    public List<String> getAllVehicleClass(){
        List<String> lstVehicleClass = new ArrayList<>();
        for(Model.VehicleClass vehicleClass : Model.VehicleClass.values()){
            if(vehicleClass == Model.VehicleClass.UNKNOWN)
                lstVehicleClass.add("");
            else
                lstVehicleClass.add(vehicleClass.toString());
        }
        return lstVehicleClass;
    }

    public void setFilterModel(String model){
        Backend.getInstance().setFilterModel(model);
    }

    public void setFilterClass(String vehicleClass){
        Backend.getInstance().setFilterClass(vehicleClass);
    }

    public void setFilterTransmission(String transmission){
        Backend.getInstance().setFilterTransmission(transmission);
    }
    public void setFilterFuelType(String fuelType){
        Backend.getInstance().setFilterFuelType(fuelType);
    }
    public void setFilterNumberOfDoor(int numberOfDoor){
        Backend.getInstance().setFilterNumberDoor(numberOfDoor);
    }
    public void setFilterFuelEconomy(int minFuelEconomy, int maxFuelEconomy){
        Backend.getInstance().setFilterMinFuelEconomy(minFuelEconomy);
        Backend.getInstance().setFilterMaxFuelEconomy(maxFuelEconomy);
    }

    public void setFilterColor(String color){
        Backend.getInstance().setFilterColor(color);
    }
}
