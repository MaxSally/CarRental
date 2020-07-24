package edu.unl.cse.csce361.car_rental.backend;

public class AddOn implements PricedItem {

    public AddOn(PricedItem pricedItem){
        super(pricedItem);

    }

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
        super();
    }
}
