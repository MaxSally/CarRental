package edu.unl.cse.csce361.car_rental.backend;

public class AddOn extends PricedItemDecorator {

    private final int cost;
    private final String name;

    public AddOn(PricedItem pricedItem, String name, int cost) {
        super(pricedItem);
        this.cost = cost;
        this.name = name;
    }

    @Override
    public int getDailyRate() {
        return super.getDailyRate() + cost;
    }

    @Override
    public String getLineItemSummary() {
        return super.getLineItemSummary() + getIndividualLineSummary(name, cost);
    }

    @Override
    public PricedItem getBasePricedItem() {
        return super.getBasePricedItem();
    }

    @Override
    public String toString() {
        return name;
    }
}
