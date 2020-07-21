package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;

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
}
