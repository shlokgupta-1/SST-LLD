public class ReceiptPrinter {

    public void print(String bookingId, Money total) {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Total Fee: " + total.getAmount());
    }
}