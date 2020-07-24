package edu.unl.cse.csce361.car_rental.backend;

public class PricedItemDecorator implements PricedItem{

    private PricedItem pricedItem;
    public PricedItemDecorator(PricedItem pricedItem){
        this.pricedItem = pricedItem;
    }
    @Override
    public int getDailyRate() {
        
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
