import java.util.UUID;

public class HostelBookingService {

    private final RoomPricingPolicy roomPricing;
    private final AddOnPricingService addOnService;
    private final BookingRepository repository;
    private final ReceiptPrinter printer;

    public HostelBookingService(RoomPricingPolicy roomPricing,
                                AddOnPricingService addOnService,
                                BookingRepository repository,
                                ReceiptPrinter printer) {
        this.roomPricing = roomPricing;
        this.addOnService = addOnService;
        this.repository = repository;
        this.printer = printer;
    }

    public void process(BookingRequest request) {

        Money base = roomPricing.basePrice(request.getRoomType());
        Money addOnCost = addOnService.calculate(request.getAddOns());

        Money total = base.add(addOnCost);

        String bookingId = UUID.randomUUID().toString();

        repository.save(bookingId, total);

        printer.print(bookingId, total);
    }
}