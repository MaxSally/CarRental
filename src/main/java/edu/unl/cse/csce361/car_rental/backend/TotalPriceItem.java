package edu.unl.cse.csce361.car_rental.backend;

public class TotalPriceItem extends PricedItemDecorator {
    private String name = "Subtotal";

    public TotalPriceItem() {
        super();
    }

    public TotalPriceItem(PricedItem pricedItem) {
        super(pricedItem);
    }

    @Override
    public int getDailyRate() {
        return super.getDailyRate();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getLineItemSummary() {
        return super.getLineItemSummary() + getIndividualLineSummary(name, getDailyRate());
    }

    @Override
    public PricedItem getBasePricedItem() {
        return super.getBasePricedItem();
    }
}
