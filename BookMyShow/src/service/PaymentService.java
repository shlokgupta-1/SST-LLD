package service;

import enums.BookingStatus;
import enums.NotificationType;
import enums.PaymentStatus;
import models.Booking;
import models.Payment;
import notification.NotificationService;
import payment.PaymentMethod;
import repository.BookingRepository;
import repository.PaymentRepository;

public class PaymentService {

    private final PaymentRepository   paymentRepository;
    private final BookingRepository   bookingRepository;
    private final BookingService      bookingService;
    private final NotificationService notificationService;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository,
                          BookingService bookingService,
                          NotificationService notificationService) {
        this.paymentRepository   = paymentRepository;
        this.bookingRepository   = bookingRepository;
        this.bookingService      = bookingService;
        this.notificationService = notificationService;
    }

    public Payment makePayment(String bookingId, PaymentMethod paymentMethod) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        if (booking.getStatus() != BookingStatus.LOCKED) {
            throw new IllegalStateException(
                    "Cannot pay for booking in state: " + booking.getStatus()
                    + ". Seats may have expired.");
        }

        Payment payment = new Payment(bookingId, booking.getTotalAmount(), paymentMethod);
        paymentRepository.save(payment);

        System.out.println("[PaymentService] Initiating ₹" + booking.getTotalAmount()
                + " via " + paymentMethod.getMethodName());

        boolean success = paymentMethod.pay(booking.getTotalAmount());

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            bookingService.confirmBooking(bookingId);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            booking.setStatus(BookingStatus.FAILED);
            bookingRepository.save(booking);
            notificationService.notify(
                    booking.getUser().getId(),
                    "Payment failed for booking " + bookingId + ". Please try again.",
                    NotificationType.PAYMENT_FAILED
            );
            System.out.println("[PaymentService] Payment FAILED for booking: " + bookingId);
        }

        return payment;
    }

    public void processRefund(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No payment found for booking: " + bookingId));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException(
                    "Refund only applicable for successful payments. Current: " + payment.getStatus());
        }

        boolean refunded = payment.getMethod().refund(payment.getAmount());
        if (refunded) {
            payment.setStatus(PaymentStatus.REFUNDED);
            paymentRepository.save(payment);
            Booking booking = bookingRepository.findById(bookingId).orElseThrow();
            notificationService.notify(
                    booking.getUser().getId(),
                    "₹" + payment.getAmount() + " refunded to your account.",
                    NotificationType.PAYMENT_REFUNDED
            );
            System.out.println("[PaymentService] Refund processed for booking: " + bookingId);
        }
    }
}
