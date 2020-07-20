package edu.unl.cse.csce361.car_rental.backend;

/**
 * Represents anything that is part of a rental: the car, add-ons, fees, taxes, etc. This is the overarching
 * interface for the Decorator Pattern.
 */
public interface PricedItem {

    char CURRENCY_SYMBOL = '¤';
    int LINE_ITEM_TEXT_LENGTH = 80;

    /**
     * Provides the total daily rate, in Zorkmids (¤), for this item plus any enclosed items
     *
     * @return The total daily rate
     */
    int getDailyRate();

    /**
     * Provides a line-by-line description of each PriceItem enclosed in this PriceItem, plus the description of this
     * PricedItem object itself. The description of each item shall be on its own line (hence, "line item") and shall
     * consist of the item's String description (left-justified) and its price (right-justified to position 79).
     *
     * @return A line-by-line description of all PricedItem objects associated with this object
     */
    String getLineItemSummary();

    /**
     * Returns the "base" PricedItem object; that is, the object that does not decorate any other PricedItem objects.
     * The only known "base" PricedItem class(es) are {@link Car} implementations.
     *
     * @return The "base" PricedItem object
     */
    PricedItem getBasePricedItem();
}
