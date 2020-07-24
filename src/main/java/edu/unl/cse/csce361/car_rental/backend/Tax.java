package edu.unl.cse.csce361.car_rental.backend;

public class Tax extends PricedItemDecorator {
    private final Double SALES_TAX_PERCENT =  0.05;
    private String taxName;
    private double taxRate;

    public Tax(PricedItemDecorator pricedItemDecorator, String taxName, double taxRate){
        super(pricedItemDecorator);
        this.taxName = taxName;
        this.taxRate = taxRate;
    }

    @Override
    public int getDailyRate() {
        return (int) (super.getDailyRate() * (1.0 + taxRate));
    }

    public int getTax(){
        return (int) (super.getDailyRate() * taxRate);
    }

    @Override
    public String getLineItemSummary() {
        return super.getLineItemSummary() + getIndividualLineSummary(taxName, getTax());
    }

    @Override
    public PricedItem getBasePricedItem() {
        return super.getBasePricedItem();
    }

    @Override
    public String toString() {
        return taxName;
    }
}
