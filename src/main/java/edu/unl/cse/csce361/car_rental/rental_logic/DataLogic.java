package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.sun.xml.fastinfoset.stax.events.Util.isEmptyString;
import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.availabilityCriteriaChecker;

public class DataLogic {
    private static DataLogic instance;
    private CriteriaFilter criteriaFilter;
    private List<Car> currentListedCarOnCarSelection;

    public static DataLogic getInstance(){
        if(instance == null){
            instance = new DataLogic();
        }
        return instance;
    }

    private DataLogic(){
        super();
        criteriaFilter = new CriteriaFilter();
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

    public boolean setAddress(String name, String streetAddress1, String streetAddress2, String city, String state, String zipCode){
        if(Backend.getInstance().updateAddressForIndividualCustomer(name, streetAddress1, streetAddress2, city, state, zipCode)){
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
        criteriaFilter.setVehicleModel(model);
    }

    public void setFilterClass(String vehicleClass){
        if(isEmptyString(vehicleClass)){
            criteriaFilter.setVehicleClass(Model.VehicleClass.UNKNOWN);
        }else{
            criteriaFilter.setVehicleClass(Model.VehicleClass.valueOf(vehicleClass));
        }
    }

    public void setFilterTransmission(String transmission){
        if(isEmptyString(transmission)){
            criteriaFilter.setTransmission(Model.Transmission.UNKNOWN);
        }else {
            criteriaFilter.setTransmission(Model.Transmission.valueOf(transmission));
        }
    }

    public void setFilterFuelType(String fuelType){
        if(isEmptyString(fuelType)){
            criteriaFilter.setFuelType(Model.Fuel.UNKNOWN);
        }else{
            criteriaFilter.setFuelType(Model.Fuel.valueOf(fuelType));
        }
    }
    public void setFilterNumberOfDoor(Integer numberOfDoor){
        criteriaFilter.setNumberOfDoors(numberOfDoor == null?CriteriaFilter.INVALID_DOOR:numberOfDoor);
    }

    public void setFilterFuelEconomy(int minFuelEconomy, int maxFuelEconomy){
        criteriaFilter.setMinFuelEconomy(minFuelEconomy);
        criteriaFilter.setMaxFuelEconomy(maxFuelEconomy);
    }

    public void setFilterColor(String color){
        criteriaFilter.setColor(color);
    }

    public void resetCriteriaFilter(){
        criteriaFilter = new CriteriaFilter();
    }

    public List<String> getValidCarDescription(){
        List<Car> lstCar = Backend.getInstance().getAllCar();
        List<Car> lstValidCar = new ArrayList<>();
        for(Car car: lstCar){
            boolean acceptable = true;
            Model currentCarModel = Backend.getInstance().getModelEntityByName(car.getModel());
            acceptable &= car.isAvailable();
            acceptable &= availabilityCriteriaChecker("", criteriaFilter.getVehicleModel(), currentCarModel.getModel());
            acceptable &= availabilityCriteriaChecker(Model.VehicleClass.UNKNOWN.toString(), criteriaFilter.getVehicleClass().toString(), currentCarModel.getClassType().toString());
            acceptable &= availabilityCriteriaChecker("", criteriaFilter.getColor(), car.getColor());
            acceptable &= availabilityCriteriaChecker(Model.Fuel.UNKNOWN.toString(), criteriaFilter.getFuelType().toString(), currentCarModel.getFuel().toString());
            acceptable &= availabilityCriteriaChecker(Model.Transmission.UNKNOWN.toString(), criteriaFilter.getTransmission().toString(), currentCarModel.getTransmission().toString());
            if(criteriaFilter.getNumberOfDoors() != CriteriaFilter.INVALID_DOOR && criteriaFilter.getNumberOfDoors() != currentCarModel.getNumberOfDoors().get()){
                acceptable = false;
            }
            if(criteriaFilter.getMinFuelEconomy() > currentCarModel.getFuelEconomyMPG().get() || currentCarModel.getFuelEconomyMPG().get() > criteriaFilter.getMaxFuelEconomy()){
                acceptable = false;
            }
            if(acceptable){
                lstValidCar.add(car);
            }
        }
        Collections.sort(lstValidCar, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return Backend.getInstance().getModelEntityByName(car2.getModel()).getFuelEconomyMPG().get()
                - Backend.getInstance().getModelEntityByName(car1.getModel()).getFuelEconomyMPG().get();
            }
        });
        List<String> lstValidCarDescription = new ArrayList<>();
        currentListedCarOnCarSelection = lstValidCar;
        for(Car car: lstValidCar){
            lstValidCarDescription.add(car.getDescription());
        }
        return lstValidCarDescription;
    }

    public void sortByPrice(boolean isAscending){
        Collections.sort(currentListedCarOnCarSelection, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                if(Backend.getInstance().getModelEntityByName(car2.getModel()).getFuelEconomyMPG().get()
                        == Backend.getInstance().getModelEntityByName(car1.getModel()).getFuelEconomyMPG().get()){
                    if(isAscending){
                        return car1.getDailyRate() - car2.getDailyRate();
                    }else{
                        return car2.getDailyRate() - car1.getDailyRate();
                    }
                }
                return Backend.getInstance().getModelEntityByName(car2.getModel()).getFuelEconomyMPG().get()
                        - Backend.getInstance().getModelEntityByName(car1.getModel()).getFuelEconomyMPG().get();
            }
        });
    }
}
