package edu.unl.cse.csce361.car_rental.backend;

public class PricedItemDecorator implements PricedItem {
    private PricedItem pricedItem;

    public PricedItemDecorator() {
        super();
    }

    public PricedItemDecorator(PricedItem pricedItem) {
        this.pricedItem = pricedItem;
    }

    @Override
    public int getDailyRate() {
        return pricedItem.getDailyRate();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getLineItemSummary() {
        return pricedItem.getLineItemSummary();
    }

    @Override
    public PricedItem getBasePricedItem() {
        return pricedItem.getBasePricedItem();
    }

    protected String getIndividualLineSummary(String description, int price) {
        String dailyRate = CURRENCY_SYMBOL + " " + price;
        int descriptionLength = description.length();
        int dailyRateLength = dailyRate.length();
        int numberOfLines = 1;
        while (descriptionLength > LINE_ITEM_TEXT_LENGTH - dailyRateLength - 1) {
            // place newline at index 80 on 1st iteration, 161 on 2nd (allows for previous newline), 242 on 3rd, etc.
            int index = numberOfLines * LINE_ITEM_TEXT_LENGTH + numberOfLines - 1;
            description = description.substring(0, index + 1) + System.lineSeparator() + description.substring(index + 1);
            descriptionLength -= LINE_ITEM_TEXT_LENGTH;
        }
        String padding = " ".repeat(LINE_ITEM_TEXT_LENGTH - descriptionLength - dailyRateLength);
        return description + padding + dailyRate + "\n";
    }
}
