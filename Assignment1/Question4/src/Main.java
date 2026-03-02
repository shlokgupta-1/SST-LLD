import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        RoomPricingPolicy pricingPolicy = new DefaultRoomPricingPolicy();
        AddOnPricingService addOnService = new AddOnPricingService();
        BookingRepository repository = new FakeBookingRepo();
        ReceiptPrinter printer = new ReceiptPrinter();

        HostelBookingService service = new HostelBookingService(
                pricingPolicy,
                addOnService,
                repository,
                printer
        );

        BookingRequest request = new BookingRequest(
                "SINGLE",
                Arrays.asList(
                        new AddOn("Laundry", 500),
                        new AddOn("WiFi", 300)
                )
        );

        service.process(request);
    }
}