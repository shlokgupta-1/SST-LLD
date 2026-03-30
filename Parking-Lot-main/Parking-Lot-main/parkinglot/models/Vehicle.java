package parkinglot.models;

import parkinglot.enums.VehicleType;

public class Vehicle {

    private final String licensePlate;
    private final String color;
    private final VehicleType vehicleType;

    public Vehicle(String licensePlate, String color, VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.color        = color;
        this.vehicleType  = vehicleType;
    }

    public String getLicensePlate() { return licensePlate; }
    public String getColor()        { return color;        }
    public VehicleType getVehicleType() { return vehicleType; }

    @Override
    public String toString() {
        return String.format("Vehicle[plate=%s, color=%s, type=%s]",
                licensePlate, color, vehicleType);
    }
}
