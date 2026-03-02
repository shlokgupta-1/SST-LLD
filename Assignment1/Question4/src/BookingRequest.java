import java.util.List;

public class BookingRequest {

    private final String roomType;
    private final List<AddOn> addOns;

    public BookingRequest(String roomType, List<AddOn> addOns) {
        this.roomType = roomType;
        this.addOns = addOns;
    }

    public String getRoomType() {
        return roomType;
    }

    public List<AddOn> getAddOns() {
        return addOns;
    }
}