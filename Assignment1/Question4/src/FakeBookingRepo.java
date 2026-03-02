import java.util.HashMap;
import java.util.Map;

public class FakeBookingRepo implements BookingRepository {

    private final Map<String, Money> store = new HashMap<>();

    @Override
    public void save(String bookingId, Money total) {
        store.put(bookingId, total);
    }
}