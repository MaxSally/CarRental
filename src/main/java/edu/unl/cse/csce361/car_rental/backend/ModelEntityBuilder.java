package edu.unl.cse.csce361.car_rental.backend;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

public class ModelEntityBuilder {
    private String manufacturer;
    private String model;
    private Model.VehicleClass vehicleClass;
    private Model.Transmission transmission;
    private Model.Fuel fuelType;
    private Integer fuelEconomy;
    private Integer numberOfDoors;

    public ModelEntityBuilder(){
        manufacturer = model = "Unknown";
        vehicleClass = Model.VehicleClass.UNKNOWN;
        transmission = Model.Transmission.UNKNOWN;
        fuelType = Model.Fuel.UNKNOWN;
        fuelEconomy = null;
        numberOfDoors = null;
    }

    public ModelEntityBuilder setManufacturer(String manufacturer) {
        if(!isEmptyString(manufacturer))
           this.manufacturer = manufacturer;
        return this;
    }

    public ModelEntityBuilder setVehicleClass(Model.VehicleClass vehicleClass) {
        if(vehicleClass != Model.VehicleClass.UNKNOWN)
            this.vehicleClass = vehicleClass;
        return this;
    }

    public ModelEntityBuilder setTransmission(Model.Transmission transmission) {
        if(transmission != Model.Transmission.UNKNOWN)
            this.transmission = transmission;
        return this;
    }

    public ModelEntityBuilder setFuelType(Model.Fuel fuelType) {
        if(fuelType != Model.Fuel.UNKNOWN)
            this.fuelType = fuelType;
        return this;
    }

    public ModelEntityBuilder setFuelEconomy(Integer fuelEconomy) {
        if(fuelEconomy != null)
            this.fuelEconomy = fuelEconomy;
        return this;
    }

    public ModelEntityBuilder setNumberOfDoors(Integer numberOfDoors) {
        if(numberOfDoors != null)
            this.numberOfDoors = numberOfDoors;
        return this;
    }

    public ModelEntityBuilder setModel(String model) {
        if(!isEmptyString(model))
            this.model = model;
        return this;
    }

    public ModelEntity build(){
        return new ModelEntity(manufacturer, model, vehicleClass, numberOfDoors, transmission, fuelType, fuelEconomy);
    }
}
