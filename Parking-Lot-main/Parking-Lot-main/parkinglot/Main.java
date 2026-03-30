package parkinglot;

import parkinglot.enums.SlotType;
import parkinglot.enums.VehicleType;
import parkinglot.factory.ParkingSlotFactory;
import parkinglot.gates.EntryGate;
import parkinglot.gates.ExitGate;
import parkinglot.models.*;
import parkinglot.service.BillingService;
import parkinglot.service.ParkingService;
import parkinglot.strategy.NearestSlotStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ParkingLot lot = ParkingLot.getInstance();
        lot.setName("Nexus Mall Parking - Bengaluru");
        lot.setSlotAssignmentStrategy(new NearestSlotStrategy());

        List<String> entryGateIds = List.of("E1", "E2");
        List<String> exitGateIds  = List.of("X1");
        entryGateIds.forEach(lot::addEntryGate);
        exitGateIds.forEach(lot::addExitGate);

        ParkingFloor floor1 = new ParkingFloor(1);

        for (int i = 1; i <= 4; i++) {
            ParkingSlot s = ParkingSlotFactory.createSlot(SlotType.SMALL, 1, i);
            s.setDistance("E1", i * 10);
            s.setDistance("E2", 50 + i * 10);
            floor1.addSlot(s);
        }

        for (int i = 1; i <= 3; i++) {
            ParkingSlot s = ParkingSlotFactory.createSlot(SlotType.MEDIUM, 1, i);
            s.setDistance("E1", i * 15);
            s.setDistance("E2", 50 + i * 15);
            floor1.addSlot(s);
        }

        for (int i = 1; i <= 2; i++) {
            ParkingSlot s = ParkingSlotFactory.createSlot(SlotType.LARGE, 1, i);
            s.setDistance("E1", i * 25);
            s.setDistance("E2", 50 + i * 25);
            floor1.addSlot(s);
        }

        ParkingFloor floor2 = new ParkingFloor(2);

        for (int i = 1; i <= 4; i++) {
            ParkingSlot s = ParkingSlotFactory.createSlot(SlotType.SMALL, 2, i);
            s.setDistance("E1", 100 + i * 10);
            s.setDistance("E2", i * 10);
            floor2.addSlot(s);
        }

        for (int i = 1; i <= 3; i++) {
            ParkingSlot s = ParkingSlotFactory.createSlot(SlotType.MEDIUM, 2, i);
            s.setDistance("E1", 100 + i * 15);
            s.setDistance("E2", i * 15);
            floor2.addSlot(s);
        }

        for (int i = 1; i <= 2; i++) {
            ParkingSlot s = ParkingSlotFactory.createSlot(SlotType.LARGE, 2, i);
            s.setDistance("E1", 100 + i * 25);
            s.setDistance("E2", i * 25);
            floor2.addSlot(s);
        }

        lot.addFloor(floor1);
        lot.addFloor(floor2);

        BillingService billing = new BillingService();
        ParkingService service = new ParkingService(lot, billing);

        EntryGate gateE1 = new EntryGate("E1", service);
        EntryGate gateE2 = new EntryGate("E2", service);
        ExitGate  gateX1 = new ExitGate("X1", service);

        System.out.println("Initial status:");
        service.showStatus();

        Vehicle bike1 = new Vehicle("KA01EF2345", "Black",  VehicleType.TWO_WHEELER);
        Vehicle car1  = new Vehicle("MH12AB1234", "White",  VehicleType.CAR);
        Vehicle bus1  = new Vehicle("DL01XY9090", "Yellow", VehicleType.BUS);
        Vehicle car2  = new Vehicle("TN09ZZ4321", "Red",    VehicleType.CAR);

        Ticket t1 = gateE1.processEntry(bike1);
        Ticket t2 = gateE1.processEntry(car1);
        Ticket t3 = gateE2.processEntry(bus1);
        Ticket t4 = gateE2.processEntry(car2);

        System.out.println("Status after check-in:");
        service.showStatus();

        LocalDateTime car1Exit = t2.getEntryTime().plusHours(3).plusMinutes(20);
        gateX1.processExit(t2.getTicketId(), car1Exit);

        LocalDateTime bus1Exit = t3.getEntryTime().plusHours(2);
        gateX1.processExit(t3.getTicketId(), bus1Exit);

        System.out.println("Status after check-out:");
        service.showStatus();

        System.out.println("Testing invalid ticket:");
        try {
            gateX1.processExit("INVALID-TICKET", LocalDateTime.now());
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("Concurrency test - two threads racing for a slot:");

        Vehicle carThread1 = new Vehicle("THREAD-A-001", "Blue",  VehicleType.CAR);
        Vehicle carThread2 = new Vehicle("THREAD-B-002", "Green", VehicleType.CAR);

        Ticket[] results = new Ticket[2];

        Thread tA = new Thread(() -> {
            results[0] = gateE1.processEntry(carThread1);
        }, "Gate-E1-Thread");

        Thread tB = new Thread(() -> {
            results[1] = gateE2.processEntry(carThread2);
        }, "Gate-E2-Thread");

        tA.start(); tB.start();
        tA.join();  tB.join();

        System.out.println("Thread A got: " +
            (results[0] != null ? results[0].getAssignedSlot().getSlotId() : "no slot"));
        System.out.println("Thread B got: " +
            (results[1] != null ? results[1].getAssignedSlot().getSlotId() : "no slot"));

        boolean doubleBooked = results[0] != null
                && results[1] != null
                && results[0].getAssignedSlot().getSlotId()
                             .equals(results[1].getAssignedSlot().getSlotId());

        System.out.println(doubleBooked ? "Double-booking detected." : "No double-booking.");

        System.out.println("Final status:");
        service.showStatus();
    }
}
