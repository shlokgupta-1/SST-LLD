package parkinglot.service;

import parkinglot.enums.SlotType;
import parkinglot.models.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class BillingService {

    private final Map<SlotType, Double> hourlyRates = new EnumMap<>(SlotType.class);

    public BillingService() {
        hourlyRates.put(SlotType.SMALL,  20.0);
        hourlyRates.put(SlotType.MEDIUM, 40.0);
        hourlyRates.put(SlotType.LARGE,  100.0);
    }

    public void setHourlyRate(SlotType type, double rate) {
        hourlyRates.put(type, rate);
    }

    public double generateBill(Ticket ticket, LocalDateTime exitTime) {
        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        long minutes      = duration.toMinutes();
        long hours        = (long) Math.ceil(minutes / 60.0);
        hours             = Math.max(hours, 1);

        SlotType type  = ticket.getAssignedSlot().getSlotType();
        double   rate  = hourlyRates.get(type);
        double   total = hours * rate;

        System.out.println("Ticket: " + ticket.getTicketId()
            + " | Vehicle: " + ticket.getVehicle().getLicensePlate()
            + " | Slot: " + ticket.getAssignedSlot().getSlotId()
            + " | Duration: " + hours + " hr(s)"
            + " | Rate: Rs. " + (int) rate + "/hr"
            + " | Total: Rs. " + String.format("%.2f", total));

        return total;
    }
}
