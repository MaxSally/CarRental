package edu.unl.cse.csce361.car_rental.backend;


import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.isEmptyString;

public class CarEntityBuilder {
    private String model;
    private String color;
    private String licensePlate;
    private String vin;
    private int dailyRate;

    public CarEntityBuilder(){
        model = color = licensePlate = vin = "Unknown";
        dailyRate = 0;
    }

    public CarEntityBuilder setModel(String model) {
        if(!isEmptyString(model))
            this.model = model;
        return this;
    }

    public CarEntityBuilder setColor(String color) {
        if(!isEmptyString(color))
        this.color = color;
        return  this;
    }

    public CarEntityBuilder setLicensePlate(String licensePlate) {
        if(!isEmptyString(licensePlate))
            this.licensePlate = licensePlate;
        return  this;
    }

    public CarEntityBuilder setVin(String vin) {
        if(!isEmptyString(vin))
            this.vin = vin;
        return this;
    }

    public CarEntityBuilder setDailyRate(Integer dailyRate) {
        if(dailyRate != null)
            this.dailyRate = dailyRate;
        return this;
    }

    public CarEntity build(){
        return new CarEntity(model, color, licensePlate, vin, dailyRate);
    }
}
