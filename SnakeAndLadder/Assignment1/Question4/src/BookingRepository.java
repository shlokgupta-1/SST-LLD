public interface BookingRepository {
    void save(String bookingId, Money total);
}