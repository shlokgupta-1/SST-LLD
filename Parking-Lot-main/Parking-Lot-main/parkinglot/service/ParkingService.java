package parkinglot.service;

import parkinglot.models.ParkingLot;
import parkinglot.models.Ticket;
import parkinglot.models.Vehicle;

import java.time.LocalDateTime;

public class ParkingService {

    private final ParkingLot     parkingLot;
    private final BillingService billingService;

    public ParkingService(ParkingLot parkingLot, BillingService billingService) {
        this.parkingLot     = parkingLot;
        this.billingService = billingService;
    }

    public Ticket checkIn(Vehicle vehicle, String entryGateId) {
        Ticket ticket = parkingLot.generateParkingTicket(vehicle, entryGateId);
        System.out.println("CHECK-IN SUCCESS");
        System.out.println(ticket);
        return ticket;
    }

    public double checkOut(String ticketId, LocalDateTime exitTime) {
        Ticket ticket = parkingLot.getTicket(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid or already-settled ticket: " + ticketId);
        }

        System.out.println("CHECK-OUT: Vehicle " + ticket.getVehicle().getLicensePlate());

        double amount = billingService.generateBill(ticket, exitTime);
        parkingLot.closeTicket(ticketId);
        return amount;
    }

    public void showStatus() {
        parkingLot.showStatus();
    }
}
