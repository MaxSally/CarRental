package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Model;

public class CriteriaFilter {
    private Model.VehicleClass vehicleClass;
    private String vehicleModel;
    private Model.Transmission transmission;
    private Model.Fuel fuelType;
    private int numberOfDoors;
    private int minFuelEconomy;
    private int maxFuelEconomy;
    private String color;

    public static int INVALID_DOOR = -1;
    public static int MIN_FUEL_ECONOMY = 0;
    public static int MAX_FUEL_ECONOMY = 150;

    public CriteriaFilter(){
        super();
        vehicleModel = color = "";
        numberOfDoors = INVALID_DOOR;
        minFuelEconomy = MIN_FUEL_ECONOMY;
        maxFuelEconomy = MAX_FUEL_ECONOMY;
        vehicleClass = Model.VehicleClass.UNKNOWN;
        fuelType = Model.Fuel.UNKNOWN;
        transmission = Model.Transmission.UNKNOWN;
    }

    public Model.VehicleClass getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(Model.VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Model.Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Model.Transmission transmission) {
        this.transmission = transmission;
    }

    public Model.Fuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(Model.Fuel fuelType) {
        this.fuelType = fuelType;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public int getMinFuelEconomy() {
        return minFuelEconomy;
    }

    public void setMinFuelEconomy(int minFuelEconomy) {
        this.minFuelEconomy = minFuelEconomy;
    }

    public int getMaxFuelEconomy() {
        return maxFuelEconomy;
    }

    public void setMaxFuelEconomy(int maxFuelEconomy) {
        this.maxFuelEconomy = maxFuelEconomy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
