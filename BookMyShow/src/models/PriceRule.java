package models;

import enums.SeatType;

public class PriceRule {
    private final SeatType seatType;
    private final double multiplier;
    private final String description;

    public PriceRule(SeatType seatType, double multiplier, String description) {
        this.seatType    = seatType;
        this.multiplier  = multiplier;
        this.description = description;
    }

    public SeatType getSeatType()   { return seatType; }
    public double getMultiplier()   { return multiplier; }
    public String getDescription()  { return description; }

    @Override
    public String toString() {
        return "PriceRule{seatType=" + seatType + ", multiplier=" + multiplier + "}";
    }
}
