package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static com.sun.xml.fastinfoset.stax.events.Util.isEmptyString;
import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.*;

public class DataLogic {
    private static DataLogic instance;
    private CriteriaFilter criteriaFilter;
    private List<Car> currentListedCarOnCarSelection;
    private List<Car> currentListedCarOnCarManagerScreen;

    public static DataLogic getInstance() {
        if(instance == null) {
            instance = new DataLogic();
        }
        return instance;
    }

    private DataLogic() {
        super();
        criteriaFilter = new CriteriaFilter();
        currentListedCarOnCarManagerScreen = new ArrayList<>();
        currentListedCarOnCarSelection = new ArrayList<>();
    }

    public boolean logIn(String username){
        return Backend.getInstance().logIn(username);
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

    public boolean hasModelName(String name) {
        return (Backend.getInstance().getModelByName(name) != null);
    }

    public boolean addSelectedCar(Integer carIndex) {
        return Backend.getInstance().addSelectedCar(currentListedCarOnCarSelection.get(carIndex));
    }

    public boolean setIndividualCustomer(String name, String streetAddress1, String streetAddress2, String city,
                                         String state, String zipCode, String cardNumber, String cvv,
                                         Integer expirationMonth, Integer expirationYear) {
        boolean isCompleted = true;
        if(Backend.getInstance().getCustomer(name) == null) {
            isCompleted = false;
        }
        isCompleted &= Backend.getInstance().updateCardInformationForIndividualCustomer(name, cardNumber, cvv, expirationMonth, expirationYear);
        if(!(isEmptyString(streetAddress1) && isEmptyString(streetAddress2) && isEmptyString(city) && isEmptyString(state) && isEmptyString(zipCode))) {
            isCompleted &=Backend.getInstance().updateAddressForCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        }
        return isCompleted;
    }

    public boolean setCorporateCustomer(String name, String streetAddress1, String streetAddress2,
                                        String city, String state, String zipCode, String corporateAccount) {
        boolean isCompleted = true;
        if(Backend.getInstance().getCustomer(name) == null) {
            isCompleted = false;
        }
        isCompleted = Backend.getInstance().updateBankAccountForCorporationCustomer(name, corporateAccount);
        if(!(isEmptyString(streetAddress1) && isEmptyString(streetAddress2) && isEmptyString(city) && isEmptyString(state) && isEmptyString(zipCode))) {
            isCompleted &=Backend.getInstance().updateAddressForCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        }
        return isCompleted;
    }

    public boolean setCorporateCustomerByManager(String name, String streetAddress1, String streetAddress2,
                                        String city, String state, String zipCode, String corporateAccount, double negotiatedRate) {
        boolean isCompleted = setCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount);
        isCompleted &= Backend.getInstance().updateNegotiatedRateForCorporateCustomer(name, negotiatedRate);
        return isCompleted;
    }

    public void setDailyRateByManager(String vehicleClass, Integer dailyRate) {
        Backend.getInstance().updateDailyRateByManager(Model.VehicleClass.valueOf(vehicleClass), dailyRate);
    }

    public List<String> getAllFuelType() {
        List<String> lstFuelType = new ArrayList<>();
        for(Model.Fuel fuel: Model.Fuel.values()) {
            if (fuel == Model.Fuel.UNKNOWN) {
                lstFuelType.add("");
            } else {
                lstFuelType.add(fuel.toString());
            }
        }
        return lstFuelType;
    }

    public List<String> getAllTransmission() {
        List<String> lstTransmissionType = new ArrayList<>();
        for(Model.Transmission transmission : Model.Transmission.values()) {
            if (transmission == Model.Transmission.UNKNOWN) {
                lstTransmissionType.add("");
            } else {
                lstTransmissionType.add(transmission.toString());
            }
        }
        return lstTransmissionType;
    }

    public List<String> getAllVehicleClass() {
        List<String> lstVehicleClass = new ArrayList<>();
        for(Model.VehicleClass vehicleClass : Model.VehicleClass.values()) {
            if(vehicleClass == Model.VehicleClass.UNKNOWN) {
                lstVehicleClass.add("");
            } else {
                lstVehicleClass.add(vehicleClass.toString());
            }
        }
        return lstVehicleClass;
    }

    public void setFilterModel(String model){
        criteriaFilter.setVehicleModel(model);
    }

    public void setFilterClass(String vehicleClass) {
        if(isEmptyString(vehicleClass) || checkIfItemExistInVehicleClass(vehicleClass) == Model.VehicleClass.UNKNOWN) {
            criteriaFilter.setVehicleClass(Model.VehicleClass.UNKNOWN);
        } else {
            criteriaFilter.setVehicleClass(Model.VehicleClass.valueOf(vehicleClass));
        }
    }

    public void setFilterTransmission(String transmission) {
        if(isEmptyString(transmission) || checkIfItemExistInTransmission(transmission) == Model.Transmission.UNKNOWN) {
            criteriaFilter.setTransmission(Model.Transmission.UNKNOWN);
        } else {
            criteriaFilter.setTransmission(Model.Transmission.valueOf(transmission));
        }
    }

    public void setFilterFuelType(String fuelType) {
        if(isEmptyString(fuelType) || checkIfItemExistInFuelType(fuelType) == Model.Fuel.UNKNOWN) {
            criteriaFilter.setFuelType(Model.Fuel.UNKNOWN);
        } else {
            criteriaFilter.setFuelType(Model.Fuel.valueOf(fuelType));
        }
    }

    public void setFilterNumberOfDoor(Integer numberOfDoor) {
        criteriaFilter.setNumberOfDoors(numberOfDoor == null?CriteriaFilter.INVALID_DOOR:numberOfDoor);
    }

    public void setFilterFuelEconomy(int minFuelEconomy, int maxFuelEconomy) {
        criteriaFilter.setMinFuelEconomy(minFuelEconomy);
        criteriaFilter.setMaxFuelEconomy(maxFuelEconomy);
    }

    public void setFilterColor(String color) {
        criteriaFilter.setColor(color);
    }

    public void resetCriteriaFilter(){
        criteriaFilter = new CriteriaFilter();
    }

    public List<String> getValidCarDescription() {
        List<Car> lstCar = Backend.getInstance().getAllCar();
        List<Car> lstValidCar = new ArrayList<>();
        for(Car car: lstCar) {
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
        return getCarDescriptionFromCurrentListOfCar(false);
    }

    public List<String> sortByPrice(boolean isAscending){
        Collections.sort(currentListedCarOnCarSelection, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                if(isAscending){
                    return car1.getDailyRate() - car2.getDailyRate();
                } else {
                    return car2.getDailyRate() - car1.getDailyRate();
                }
            }
        });
        return getCarDescriptionFromCurrentListOfCar(false);
    }

    private List<String> getCarDescriptionFromCurrentListOfCar(boolean isManger) {
        List<String> lstValidCarDescription = new ArrayList<>();
        for(Car car: (isManger?currentListedCarOnCarManagerScreen:currentListedCarOnCarSelection)) {
            if(isManger) {
                lstValidCarDescription.add(car.getDescriptionForManager());
            } else {
                lstValidCarDescription.add(car.getDescription());
            }
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
        if (Backend.getInstance().isCorporateCustomer()) {
            Backend.getInstance().addTax("Discount:", -(1.0 - ((CorporateCustomerEntity) Backend.getInstance().getCurrentCustomer()).getNegotiatedRate()));
        }
        Backend.getInstance().addTax("Sales Tax", 0.15);
        return Backend.getInstance().getFinalPriceSummary();
    }

    public boolean createModel(String manufacturer, String model, String classType, Integer numberOfDoors,
                               String transmission, String fuel, Integer fuelEconomyMPG) {
        return Backend.getInstance().createModel(manufacturer, model, Model.VehicleClass.valueOf(classType), numberOfDoors,
                Model.Transmission.valueOf(transmission), Model.Fuel.valueOf(fuel), fuelEconomyMPG) != null;
    }

    public boolean createCar(String model, String color, String licensePlate, String vin,
                             Boolean isRemoved, Boolean isUnderMaintenance) {
        return Backend.getInstance().createCar(model, color, licensePlate, vin, isRemoved, isUnderMaintenance) != null;
    }

    public List<String> getAllColors(){
        return Backend.getInstance().getAllColors();
    }

    public List<String> getAllModels(){
        return Backend.getInstance().getAllModels();
    }

    public void removeCar(int carIndex) {
        Car car = currentListedCarOnCarManagerScreen.get(carIndex);
        Backend.getInstance().removeCar(car);
    }

    public void moveToGarage(int carIndex) {
        Car car = currentListedCarOnCarManagerScreen.get(carIndex);
        Backend.getInstance().moveToGarage(car);
    }

    public void moveOutOfGarage(int carIndex) {
        Car car = currentListedCarOnCarManagerScreen.get(carIndex);
        Backend.getInstance().moveOutOfGarage(car);
    }

    public List<String> getCarDescriptionManager() {
        List<Car> lstCar = Backend.getInstance().getAllCar();
        List<Car> lstValidCar = new ArrayList<>();
        for(Car car: lstCar) {
            boolean acceptable = !Backend.getInstance().getIfRemoved((CarEntity) car);
            if(acceptable) {
                lstValidCar.add(car);
            }
        }
        currentListedCarOnCarManagerScreen = lstValidCar;
        return getCarDescriptionFromCurrentListOfCar(true);
    }

    public String getSelectedCarDescription() {
        if(Backend.getInstance().getSelectedCar() == null) {
            return "No rented Car";
        } else {
            return Backend.getInstance().getSelectedCar().getDescription();
        }
    }

    public String getRentedCarDescription() {
        return Backend.getInstance().getRentedCarDescription();
    }

    public void addNewAddOn(String addOnName, Integer price) {
        Backend.getInstance().addAddon(addOnName, price);
    }

    public boolean isIndividualCustomerType() {
        return Backend.getInstance().isIndividualCustomer();
    }

    public boolean isCorporateCustomerType() {
        return Backend.getInstance().isCorporateCustomer();
    }

    public void resetSelectedCars() {
        Backend.getInstance().resetSelectedCars();
    }

    public boolean rentCarIndividual(String cardNumber, Integer expirationMonth, Integer expirationYear, String cardCVV) {
        if(Backend.getInstance().updateCardInformationForIndividualCustomer(
                Backend.getInstance().getCurrentCustomer().getName(), cardNumber, cardCVV, expirationMonth, expirationYear)) {
            return Backend.getInstance().rentCar();
        } else {
            return false;
        }
    }

    public boolean rentCarCorporate(String bankAccountNumber) {
        if(Backend.getInstance().updateBankAccountForCorporationCustomer(
                Backend.getInstance().getCurrentCustomer().getName(), bankAccountNumber)) {
            return Backend.getInstance().rentCar();
        } else {
            return false;
        }
    }

    public Long returnCar() {
        return Backend.getInstance().returnCarGetLeftoverCosts();
    }

}
