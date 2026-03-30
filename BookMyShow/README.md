# Movie Ticket Booking System
A BookMyShow-style cinema booking system built in Java, designed as a clean, interview-ready low-level design.

---

## Folder Structure

```
movie_ticket/
├── build_and_run.sh
└── src/
    ├── MovieTicketBookingApp.java      ← Main driver / entry point
    ├── data/
    │   ├── DataContext.java            ← Container for seeded objects
    │   └── DataSeeder.java             ← Seeds cities, movies, theatres, shows
    ├── enums/
    │   ├── BookingStatus.java          ← CREATED, LOCKED, CONFIRMED, CANCELLED, FAILED
    │   ├── Genre.java
    │   ├── Language.java
    │   ├── NotificationType.java
    │   ├── PaymentStatus.java          ← INITIATED, SUCCESS, FAILED, REFUNDED
    │   ├── ScreenType.java             ← IMAX, DOLBY, 2D, 3D, 4DX
    │   ├── SeatStatus.java             ← AVAILABLE, LOCKED, BOOKED
    │   └── SeatType.java               ← SILVER, GOLD, PLATINUM, RECLINER
    ├── models/
    │   ├── Admin.java
    │   ├── Booking.java
    │   ├── City.java
    │   ├── Coupon.java                 ← Future extensibility
    │   ├── Movie.java
    │   ├── Notification.java
    │   ├── Payment.java
    │   ├── PriceRule.java
    │   ├── Screen.java
    │   ├── Seat.java                   ← Physical seat in a screen
    │   ├── SeatLock.java               ← TTL-based seat hold
    │   ├── Show.java
    │   ├── ShowSeat.java               ← Seat status + price per show (has ReentrantLock)
    │   ├── Theatre.java
    │   └── User.java
    ├── notification/
    │   ├── EmailNotificationObserver.java
    │   ├── NotificationObserver.java   ← Observer interface
    │   ├── NotificationService.java    ← Observable hub
    │   └── SmsNotificationObserver.java
    ├── payment/
    │   ├── CreditCardPayment.java
    │   ├── DebitCardPayment.java
    │   ├── PaymentMethod.java          ← Strategy interface
    │   ├── UpiPayment.java
    │   └── WalletPayment.java
    ├── pricing/
    │   ├── BasePricingStrategy.java    ← Flat rate by seat type
    │   ├── DynamicPricingStrategy.java ← Surge pricing based on occupancy
    │   └── PricingStrategy.java        ← Strategy interface
    └── repository/
        ├── BookingRepository.java
        ├── InMemoryBookingRepository.java
        ├── InMemoryMovieRepository.java
        ├── InMemoryPaymentRepository.java
        ├── InMemorySeatLockRepository.java
        ├── InMemoryShowRepository.java
        ├── InMemoryShowSeatRepository.java
        ├── InMemoryTheatreRepository.java
        ├── MovieRepository.java
        ├── PaymentRepository.java
        ├── SeatLockRepository.java
        ├── ShowRepository.java
        ├── ShowSeatRepository.java
        └── TheatreRepository.java
```

---

## How to Run

```bash
cd movie_ticket
chmod +x build_and_run.sh
./build_and_run.sh
```

Or manually:

```bash
find src -name "*.java" | xargs javac -d out -sourcepath src
cd out && java MovieTicketBookingApp
```

---

## Architecture Overview

The system is layered:

```
MovieTicketBookingApp (Driver)
        │
        ├── Services (business logic)
        │       ├── AdminService
        │       ├── CatalogueService
        │       ├── BookingService          ← concurrency handled here
        │       ├── PaymentService
        │       ├── SeatService
        │       └── SeatLockExpiryService
        │
        ├── Repositories (data access)
        │       └── InMemory* implementations of each interface
        │
        ├── Models / Entities
        │       └── User, Movie, Theatre, Screen, Show, ShowSeat, Booking, Payment ...
        │
        ├── Payment (Strategy pattern)
        │       └── CreditCard, Debit, UPI, Wallet
        │
        ├── Pricing (Strategy pattern)
        │       └── BasePricingStrategy, DynamicPricingStrategy
        │
        └── Notification (Observer pattern)
                └── EmailNotificationObserver, SmsNotificationObserver
```

---

## Design Patterns Used

| Pattern | Where | Why |
|---|---|---|
| **Strategy** | `PaymentMethod`, `PricingStrategy` | Swap payment/pricing logic without touching services |
| **Observer** | `NotificationService` + observers | Decouple booking events from notification channels |
| **Repository** | All `*Repository` interfaces | Swap in-memory store for JPA/Redis without touching business logic |
| **Template Method** | Implicit in the booking flow | Consistent lock → validate → mutate → release lifecycle |
| **Factory** (light) | `DataSeeder` | Centralised construction of sample entities |

---

## SOLID Principles Applied

- **S — Single Responsibility**: Each service has exactly one job. `BookingService` books. `PaymentService` pays. `AdminService` administrates.
- **O — Open/Closed**: Add a new `NetBankingPayment` class without touching `PaymentService`.
- **L — Liskov Substitution**: Any `PaymentMethod` or `PricingStrategy` implementation is fully interchangeable.
- **I — Interface Segregation**: `MovieRepository` only defines movie ops; `ShowRepository` only defines show ops.
- **D — Dependency Inversion**: Services depend on `MovieRepository` (interface), not `InMemoryMovieRepository` (concrete).

---

## Concurrency Handling

The critical section is: *two users booking the same seat at the same time*.

**How it works in `BookingService.createBooking()`:**

1. Fetch all requested `ShowSeat` objects.
2. Sort them by a composite key (`showId_seatId`) — this enforces a consistent lock order across all threads, **preventing deadlock**.
3. Acquire `ReentrantLock` on each seat one-by-one.
4. Under all locks, check that every seat is still `AVAILABLE`.
5. If any seat is taken, release all locks and throw an exception — the user sees a clean error.
6. If all seats are free, mark them `LOCKED`, create the `Booking`, persist `SeatLock` records with a TTL.
7. Release all locks in the `finally` block.

**Why `ReentrantLock` per seat (not `synchronized` on the show)?**

Synchronising on the entire show would mean any two users booking *different* seats in the same show would block each other. Per-seat locks let unrelated bookings proceed in parallel, which is exactly what you want at scale.

**Why `ConcurrentHashMap` in repositories?**

Multiple threads read/write the map simultaneously (admin adding movies while users search). `ConcurrentHashMap` handles this without explicit locking and outperforms a `synchronizedMap` under high read load.

---

## Booking Flow (End to End)

```
User selects seats
        │
        ▼
BookingService.createBooking()
   - Acquires per-seat ReentrantLocks (sorted order)
   - Validates all seats AVAILABLE
   - Marks seats → LOCKED
   - Creates Booking (status = LOCKED)
   - Saves SeatLock with 10-min TTL
   - Releases locks
        │
        ▼
PaymentService.makePayment()
   - Checks booking is still LOCKED
   - Delegates to chosen PaymentMethod (Strategy)
   - On success → BookingService.confirmBooking()
                  seats → BOOKED
                  booking → CONFIRMED
                  SeatLock removed
                  Notifications fired
   - On failure → booking → FAILED
                  Notification fired
        │
        ▼
(Optional) BookingService.cancelBooking()
   - Seats → AVAILABLE
   - Booking → CANCELLED
   - PaymentService.processRefund()
   - Notifications fired
```

---

## APIs

| Method | Description |
|---|---|
| `getMoviesByCity(city)` | Movies with at least one active show in the city |
| `getTheatresByCity(city)` | All theatres in a city |
| `getShowsByMovie(movie, city)` | Active shows for a movie filtered by city |
| `getAvailableSeats(showId)` | All AVAILABLE ShowSeats for a show |
| `createBooking(user, showId, seatIds)` | Lock seats and create a booking |
| `makePayment(bookingId, paymentMethod)` | Process payment and confirm booking |
| `cancelBooking(bookingId)` | Cancel and release seats |
| `addMovie(movie)` | Admin: add a movie |
| `addTheatre(theatre)` | Admin: add a theatre |
| `addShow(show, basePrice)` | Admin: add a show and auto-generate ShowSeats |
| `updateShow(show)` | Admin: update show details |
| `deleteShow(showId)` | Admin: soft-delete a show |
| `applyDynamicPricing(showId, strategy)` | Admin: reprice available seats |

---

## Assumptions

- Authentication and authorisation are not implemented (out of scope for LLD).
- The in-memory store resets on every restart. In production: replace repositories with JPA/Redis.
- Seat lock TTL is 10 minutes. `SeatLockExpiryService.expireLockedSeats()` would be triggered by a scheduler (e.g. `ScheduledExecutorService`, Spring `@Scheduled`).
- Payment gateway calls are simulated with print statements.
- One booking per user per `createBooking()` call is the unit of work.
- Dynamic pricing only reprices AVAILABLE seats; LOCKED/BOOKED seats retain their price.

---

## Future Improvements

- **Coupons / Discount codes**: `Coupon` model already exists; wire it into `BookingService.createBooking()`.
- **Loyalty points**: Observer on `BOOKING_CONFIRMED` events accumulates points in a `LoyaltyService`.
- **Recommendations**: `RecommendationService` reads booking history to suggest movies.
- **Push notifications**: Add a `PushNotificationObserver` to `NotificationService`.
- **Distributed locking**: Replace per-JVM `ReentrantLock` with Redis `SETNX` for multi-instance deployments.
- **Persistent storage**: Swap `InMemory*` repositories for Spring Data JPA implementations.
- **Search / filtering**: Add Elasticsearch indexing via an observer on `Movie` and `Show` saves.
- **Waitlist**: When a LOCKED seat's TTL expires, offer it to the next user on the waitlist.
- **Pricing rules engine**: Extend `PriceRule` to express complex conditions (day-of-week, time-of-day, genre).

---

## Interview Talking Points

1. **Why per-seat `ReentrantLock` and not `synchronized(show)`?** — Granularity. Two users booking different seats in the same show should never block each other.
2. **How do you prevent deadlock when locking multiple seats?** — Sort all requested seats by a consistent composite key before acquiring locks. This guarantees threads always acquire locks in the same order.
3. **Why Strategy for payment and pricing?** — Open/Closed Principle. Adding NetBanking or a new pricing model is a new class, not a change to existing code.
4. **Why Observer for notifications?** — Booking and payment services should not know about email, SMS or push. They fire an event; the registered channels handle it.
5. **How does seat lock expiry work?** — `SeatLock` stores an `expiresAt` timestamp. A scheduler calls `SeatLockExpiryService.expireLockedSeats()` every minute. Expired locks release seats back to AVAILABLE.
6. **How would this scale horizontally?** — Replace in-memory repositories with Redis (for locks + caching) and a relational DB. Use a distributed lock (Redis `SETNX` / Redisson) instead of `ReentrantLock`. Run multiple stateless service instances behind a load balancer.
