package edu.unl.cse.csce361.car_rental.backend;

public class Tax implements PricedItem {
    private final Double SALES_TAX_PERCENT =  0.05;
    private final Double ENVIRONMENT_TAX = 0.02;


    @Override
    public int getDailyRate() {
        return 0;
    }

    @Override
    public String getLineItemSummary() {
        return String.format("Sales Tax (%f): %c%d", SALES_TAX_PERCENT,CURRENCY_SYMBOL,getTax());
    }

    @Override
    public PricedItem getBasePricedItem() {
        return null;
    }

    public double getTax(){
        return (SALES_TAX_PERCENT + ENVIRONMENT_TAX) * getDailyRate();
    }
}
