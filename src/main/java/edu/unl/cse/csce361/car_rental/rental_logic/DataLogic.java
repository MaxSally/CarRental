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
        Customer customer = Backend.getInstance().createCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount, null, false);
        return (customer != null);
    }

    public boolean createCorporateCustomerAccountWithNegotiatedRate(String name, String streetAddress1, String streetAddress2,
                                                                    String city, String state, String zipCode, String corporateAccount, Double negotiatedRate) {
        Customer customer = Backend.getInstance().createCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount, negotiatedRate, true);
        return (customer != null);
    }

    public boolean hasCustomerName(String name){
        return (Backend.getInstance().getCustomer(name) != null);
    }

    public String getCustomerName(String name){
        Customer customer = Backend.getInstance().getCustomer(name);
        return (customer == null?"":customer.getName());
    }

    public boolean setIndividualCustomer(String name, String streetAddress1, String streetAddress2, String city,
                                         String state, String zipCode, String cardNumber, String cvv,
                                         Integer expirationMonth, Integer expirationYear){
        if(Backend.getInstance().getCustomer(name) == null){
            return false;
        }
        boolean isCompleted = true;
        isCompleted &= Backend.getInstance().updateCardInformationForIndividualCustomer(name, cardNumber, cvv, expirationMonth, expirationYear);
        if(!(isEmptyString(streetAddress1) && isEmptyString(streetAddress2) && isEmptyString(city) && isEmptyString(state) && isEmptyString(zipCode))){
            isCompleted &=Backend.getInstance().updateAddressForCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        }
        return isCompleted;
    }

    public boolean setCorporateCustomer(String name, String streetAddress1, String streetAddress2,
                                        String city, String state, String zipCode, String corporateAccount){
        if(Backend.getInstance().getCustomer(name) == null){
            return false;
        }
        boolean isCompleted = true;
        isCompleted &= Backend.getInstance().updateBankAccountForCorporationCustomer(name, corporateAccount);
        if(!(isEmptyString(streetAddress1) && isEmptyString(streetAddress2) && isEmptyString(city) && isEmptyString(state) && isEmptyString(zipCode))){
            isCompleted &=Backend.getInstance().updateAddressForCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        }
        return isCompleted;
    }

    public boolean setCorporateCustomerByManager(String name, String streetAddress1, String streetAddress2,
                                        String city, String state, String zipCode, String corporateAccount, double negotiatedRate){
        boolean isCompleted = setCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount);
        isCompleted &= Backend.getInstance().updateNegotiatedRateForCorporateCustomer(name, negotiatedRate);
        return isCompleted;
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
        currentListedCarOnCarSelection = lstValidCar;
        Collections.sort(currentListedCarOnCarSelection, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return Backend.getInstance().getModelEntityByName(car2.getModel()).getFuelEconomyMPG().get()
                        - Backend.getInstance().getModelEntityByName(car1.getModel()).getFuelEconomyMPG().get();
            }
        });
        return getCarDescriptionFromCurrentListOfCar();
    }

    public List<String> sortByPrice(boolean isAscending){
        Collections.sort(currentListedCarOnCarSelection, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                if(isAscending){
                    return car1.getDailyRate() - car2.getDailyRate();
                }else{
                    return car2.getDailyRate() - car1.getDailyRate();
                }
            }
        });
        return getCarDescriptionFromCurrentListOfCar();
    }

    private List<String> getCarDescriptionFromCurrentListOfCar(){
        List<String> lstValidCarDescription = new ArrayList<>();
        for(Car car: currentListedCarOnCarSelection){
            lstValidCarDescription.add(car.getDescription());
        }
        return lstValidCarDescription;
    }

    public String getCriteriaVehicleClass(){
        return criteriaFilter.getVehicleClass().toString();
    }

    public String getCriteriaVehicleModel(){
        return criteriaFilter.getVehicleModel();
    }

    public String getCriteriaColor(){
        return criteriaFilter.getColor();
    }

    public String getCriteriaTransmission(){
        return criteriaFilter.getTransmission().toString();
    }

    public String getCriteriaFuelType(){
        return criteriaFilter.getFuelType().toString();
    }

    public int getCriteriaMinFuelEconomy(){
        return criteriaFilter.getMinFuelEconomy();
    }

    public int getCriteriaMaxFuelEconomy(){
        return criteriaFilter.getMaxFuelEconomy();
    }

    public int getCriteriaNumberOfDoor(){
        return criteriaFilter.getNumberOfDoors();
    }

    public String getPriceSummary(){
        return Backend.getInstance().getPriceSummary();
    }

    public boolean createModel(String manufacturer, String model, Model.VehicleClass classType, Integer numberOfDoors,
                               Model.Transmission transmission, Model.Fuel fuel, Integer fuelEconomyMPG){
        return Backend.getInstance().createModel(manufacturer, model, classType, numberOfDoors, transmission, fuel, fuelEconomyMPG) != null;
    }

    public boolean createCar(String model, String color, String licensePlate, String vin, int dailyRate){
        return Backend.getInstance().createCar(model, color, licensePlate, vin, dailyRate) != null;
    }

    public List<String> getAllColors(){
        return Backend.getInstance().getAllColors();
    }

    public List<String> getAllModels(){
        return Backend.getInstance().getAllModels();
    }
}
