package repository;

import models.Payment;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<String, Payment> store            = new ConcurrentHashMap<>();
    private final Map<String, Payment> bookingIndex     = new ConcurrentHashMap<>();

    @Override
    public void save(Payment payment) {
        store.put(payment.getId(), payment);
        bookingIndex.put(payment.getBookingId(), payment);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Payment> findByBookingId(String bookingId) {
        return Optional.ofNullable(bookingIndex.get(bookingId));
    }
}
