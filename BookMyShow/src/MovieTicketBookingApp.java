import data.DataContext;
import data.DataSeeder;
import enums.Genre;
import enums.SeatType;
import models.*;
import notification.EmailNotificationObserver;
import notification.NotificationService;
import notification.SmsNotificationObserver;
import payment.CreditCardPayment;
import payment.UpiPayment;
import payment.WalletPayment;
import pricing.DynamicPricingStrategy;
import repository.*;
import service.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieTicketBookingApp {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Movie Ticket Booking System\n");

        MovieRepository    movieRepo    = new InMemoryMovieRepository();
        TheatreRepository  theatreRepo  = new InMemoryTheatreRepository();
        ShowRepository     showRepo     = new InMemoryShowRepository();
        ShowSeatRepository showSeatRepo = new InMemoryShowSeatRepository();
        BookingRepository  bookingRepo  = new InMemoryBookingRepository();
        PaymentRepository  paymentRepo  = new InMemoryPaymentRepository();
        SeatLockRepository lockRepo     = new InMemorySeatLockRepository();

        NotificationService notificationService = new NotificationService();
        notificationService.registerObserver(new EmailNotificationObserver());
        notificationService.registerObserver(new SmsNotificationObserver());

        AdminService adminService = new AdminService(
                movieRepo, theatreRepo, showRepo, showSeatRepo);

        CatalogueService catalogueService = new CatalogueService(
                movieRepo, theatreRepo, showRepo);

        SeatService seatService = new SeatService(showSeatRepo);

        BookingService bookingService = new BookingService(
                bookingRepo, showSeatRepo, lockRepo, showRepo, notificationService);

        PaymentService paymentService = new PaymentService(
                paymentRepo, bookingRepo, bookingService, notificationService);

        DataContext data = new DataSeeder(adminService).seed();

        separator("BROWSING — Movies, Theatres & Shows");

        List<Movie> mumbaiMovies = catalogueService.getMoviesByCity(data.mumbai);
        System.out.println("Movies available in Mumbai (" + mumbaiMovies.size() + "):");
        mumbaiMovies.forEach(m -> System.out.println("  → " + m.getTitle()
                + " [" + m.getLanguage() + "] ⭐" + m.getRating()));

        List<Theatre> bangaloreTheatres = catalogueService.getTheatresByCity(data.bangalore);
        System.out.println("\nTheatres in Bangalore (" + bangaloreTheatres.size() + "):");
        bangaloreTheatres.forEach(t -> System.out.println("  → " + t.getName()));

        List<Show> inceptionShows = catalogueService.getShowsByMovie(data.inception, data.mumbai);
        System.out.println("\nShows for 'Inception' in Mumbai (" + inceptionShows.size() + "):");
        inceptionShows.forEach(s -> System.out.println("  → " + s.getTheatre().getName()
                + " | " + s.getScreen().getName()
                + " | " + s.getStartTime().toLocalTime()));

        List<Movie> sciFiMovies = catalogueService.searchByGenre(Genre.SCI_FI);
        System.out.println("\nSci-Fi movies: " + sciFiMovies.stream().map(Movie::getTitle).toList());

        separator("SEAT AVAILABILITY — Show 1 (Inception @ IMAX)");

        List<ShowSeat> availableSeats = seatService.getAvailableSeats(data.show1.getId());
        System.out.println("Available seats: " + availableSeats.size());
        System.out.println("Sample seats:");
        availableSeats.stream().limit(5).forEach(ss ->
                System.out.println("  " + ss.getSeat().getSeatNumber()
                        + " [" + ss.getSeat().getSeatType() + "] ₹" + ss.getPrice()));

        separator("NORMAL BOOKING FLOW — Alice books 2 GOLD seats");

        User alice = new User("Alice", "alice@email.com", "+91-9000000001");
        User bob   = new User("Bob",   "bob@email.com",   "+91-9000000002");

        List<ShowSeat> goldSeats = availableSeats.stream()
                .filter(ss -> ss.getSeat().getSeatType() == SeatType.GOLD)
                .limit(2)
                .toList();

        List<String> goldSeatIds = goldSeats.stream()
                .map(ss -> ss.getSeat().getId()).toList();

        System.out.println("Alice picks: " + goldSeats.stream()
                .map(ss -> ss.getSeat().getSeatNumber()).toList());

        Booking aliceBooking = bookingService.createBooking(alice, data.show1.getId(), goldSeatIds);

        System.out.println("Booking status: " + aliceBooking.getStatus()
                + " | Amount: ₹" + aliceBooking.getTotalAmount());

        paymentService.makePayment(aliceBooking.getId(), new UpiPayment("alice@okicici"));

        System.out.println("Final booking status: " + aliceBooking.getStatus());

        separator("CONCURRENT BOOKING RACE — Alice & Bob fight for the SAME seat");

        List<ShowSeat> remainingSeats = seatService.getAvailableSeats(data.show2.getId());
        ShowSeat contestedSeat = remainingSeats.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No available seats for race demo"));

        System.out.println("Contested seat: " + contestedSeat.getSeat().getSeatNumber()
                + " [" + contestedSeat.getSeat().getSeatType() + "] ₹" + contestedSeat.getPrice());
        System.out.println("Both Alice and Bob will try to book this seat simultaneously...\n");

        CountDownLatch startGun = new CountDownLatch(1);
        CountDownLatch done     = new CountDownLatch(2);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        String       showId   = data.show2.getId();
        List<String> seatList = List.of(contestedSeat.getSeat().getId());

        executor.submit(() -> {
            try {
                startGun.await();
                System.out.println("[Thread-Alice] Attempting to book " + contestedSeat.getSeat().getSeatNumber());
                Booking b = bookingService.createBooking(alice, showId, seatList);
                System.out.println("[Thread-Alice] ✅ SUCCESS — Booking " + b.getId());
                paymentService.makePayment(b.getId(), new CreditCardPayment("4111111111111234", "Alice"));
            } catch (Exception e) {
                System.out.println("[Thread-Alice] ❌ FAILED — " + e.getMessage());
            } finally {
                done.countDown();
            }
        });

        executor.submit(() -> {
            try {
                startGun.await();
                System.out.println("[Thread-Bob]   Attempting to book " + contestedSeat.getSeat().getSeatNumber());
                Booking b = bookingService.createBooking(bob, showId, seatList);
                System.out.println("[Thread-Bob]   ✅ SUCCESS — Booking " + b.getId());
                paymentService.makePayment(b.getId(), new WalletPayment("bob-wallet", 1000));
            } catch (Exception e) {
                System.out.println("[Thread-Bob]   ❌ FAILED — " + e.getMessage());
            } finally {
                done.countDown();
            }
        });

        startGun.countDown();
        done.await();
        executor.shutdown();

        System.out.println("\nRace complete. Only ONE booking succeeded.");

        separator("CANCELLATION & REFUND — Alice cancels her booking");

        bookingService.cancelBooking(aliceBooking.getId());
        paymentService.processRefund(aliceBooking.getId());

        System.out.println("Alice's booking status after cancel: " + aliceBooking.getStatus());

        long releasedCount = seatService.getAvailableSeats(data.show1.getId()).stream()
                .filter(ss -> goldSeatIds.contains(ss.getSeat().getId()))
                .count();
        System.out.println("Released seats visible as AVAILABLE again: " + releasedCount);

        separator("ADMIN — Apply Dynamic Pricing to Show 3 (KGF)");

        adminService.applyDynamicPricing(
                data.show3.getId(),
                new DynamicPricingStrategy(0.50, 1.4));

        System.out.println("Pricing updated. Sample seats after surge:");
        seatService.getAllSeats(data.show3.getId()).stream().limit(6).forEach(ss ->
                System.out.println("  " + ss.getSeat().getSeatNumber()
                        + " [" + ss.getSeat().getSeatType() + "] ₹" + ss.getPrice()));

        separator("ALL DONE  🎬  Thanks for watching this system design come alive!");
    }

    private static void separator(String title) {
        System.out.println("\n" + title);
    }
}
