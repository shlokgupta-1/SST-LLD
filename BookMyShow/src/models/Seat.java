package models;

import enums.SeatType;

import java.util.UUID;

public class Seat {
    private final String id;
    private final String seatNumber;
    private final int row;
    private final int column;
    private final SeatType seatType;

    public Seat(String seatNumber, int row, int column, SeatType seatType) {
        this.id         = UUID.randomUUID().toString();
        this.seatNumber = seatNumber;
        this.row        = row;
        this.column     = column;
        this.seatType   = seatType;
    }

    public String getId()         { return id; }
    public String getSeatNumber() { return seatNumber; }
    public int getRow()           { return row; }
    public int getColumn()        { return column; }
    public SeatType getSeatType() { return seatType; }

    @Override
    public String toString() {
        return "Seat{" + seatNumber + ", " + seatType + "}";
    }
}
