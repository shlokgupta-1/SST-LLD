package parkinglot.factory;

import parkinglot.enums.SlotType;
import parkinglot.models.ParkingSlot;

public class ParkingSlotFactory {

    private ParkingSlotFactory() {}

    public static ParkingSlot createSlot(SlotType type, int floorNumber, int slotNumber) {
        String typeCode = switch (type) {
            case SMALL  -> "S";
            case MEDIUM -> "M";
            case LARGE  -> "L";
            default     -> "U";
        };

        String slotId = String.format("F%d-%s-%02d", floorNumber, typeCode, slotNumber);
        return new ParkingSlot(slotId, type, floorNumber, slotNumber);
    }
}
