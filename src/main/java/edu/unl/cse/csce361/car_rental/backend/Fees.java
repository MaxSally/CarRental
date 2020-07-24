package edu.unl.cse.csce361.car_rental.backend;

public class Fees implements PricedItem {
    @Override
    public int getDailyRate() {
        return 0;
    }

    @Override
    public String getLineItemSummary() {
        return null;
    }

    @Override
    public PricedItem getBasePricedItem() {
        return null;
    }
}
