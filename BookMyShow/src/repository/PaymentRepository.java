package repository;

import models.Payment;

import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);
    Optional<Payment> findById(String id);
    Optional<Payment> findByBookingId(String bookingId);
}
