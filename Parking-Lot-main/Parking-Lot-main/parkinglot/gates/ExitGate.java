package parkinglot.gates;

import parkinglot.service.ParkingService;

import java.time.LocalDateTime;

public class ExitGate {

    private final String         gateId;
    private final ParkingService parkingService;

    public ExitGate(String gateId, ParkingService parkingService) {
        this.gateId         = gateId;
        this.parkingService = parkingService;
    }

    public double processExit(String ticketId, LocalDateTime exitTime) {
        System.out.printf("Exit Gate: %s%n", gateId);
        return parkingService.checkOut(ticketId, exitTime);
    }

    public String getGateId() { return gateId; }
}
