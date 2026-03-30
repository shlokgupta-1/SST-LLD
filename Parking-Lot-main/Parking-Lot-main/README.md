# 🅿️ Multilevel Parking Lot — Low-Level Design (Java)

A clean, production-grade Java implementation of a Multilevel Parking Lot system demonstrating **SOLID principles**, **Gang-of-Four design patterns**, and **thread-safe concurrency** without any external libraries.

---

## 📁 Project Structure

```
src/com/parkinglot/
├── enums/
│   ├── VehicleType.java       # TWO_WHEELER | CAR | BUS
│   ├── SlotType.java          # SMALL | MEDIUM | LARGE
│   └── SlotStatus.java        # FREE | OCCUPIED
│
├── models/
│   ├── Vehicle.java           # licensePlate, color, vehicleType
│   ├── ParkingSlot.java       # slot with ReentrantLock + distance map
│   ├── ParkingFloor.java      # floor holding slots, prints availability
│   ├── Ticket.java            # entry snapshot: vehicle, slot, gate, time
│   └── ParkingLot.java        # ★ Singleton — the source of truth
│
├── strategy/
│   ├── SlotAssignmentStrategy.java   # Strategy interface
│   ├── NearestSlotStrategy.java      # Min-Heap nearest-slot implementation
│   └── FirstAvailableStrategy.java   # Alternate: first free slot in floor order
│
├── factory/
│   └── ParkingSlotFactory.java       # Factory for slot creation + ID naming
│
├── gates/
│   ├── EntryGate.java         # Thin controller — calls ParkingService.checkIn()
│   └── ExitGate.java          # Thin controller — calls ParkingService.checkOut()
│
├── service/
│   ├── ParkingService.java    # Façade: orchestrates entry, exit, showStatus
│   └── BillingService.java    # Rate card + ceil-rounded duration billing
│
└── Main.java                  # Full demo: 4 vehicles, 2 gates, concurrent test
```

---

## 🏛️ Design Patterns Used

| Pattern       | Where Applied                         | Why                                                                 |
|---------------|---------------------------------------|---------------------------------------------------------------------|
| **Singleton** | `ParkingLot`                          | One source of truth for slot availability across all gates          |
| **Strategy**  | `SlotAssignmentStrategy`              | Swap nearest/first-available/cheapest without touching gate logic   |
| **Factory**   | `ParkingSlotFactory`                  | Centralize slot creation; adding a new type needs one-line change   |
| **Façade**    | `ParkingService`                      | Gates stay thin; all domain logic in one coordinating service       |

---

## ⚙️ SOLID Principles

| Principle                   | How It's Applied                                                                    |
|-----------------------------|-------------------------------------------------------------------------------------|
| **S** – Single Responsibility | `BillingService` only bills; `ParkingLot` only manages state; gates only route    |
| **O** – Open / Closed        | New strategies (e.g., `CheapestSlotStrategy`) extend without modifying existing code |
| **L** – Liskov Substitution  | Any `SlotAssignmentStrategy` implementation is a drop-in replacement               |
| **I** – Interface Segregation | `SlotAssignmentStrategy` has exactly one method — no fat interfaces               |
| **D** – Dependency Inversion | `ParkingLot` depends on the `SlotAssignmentStrategy` abstraction, not a concrete class |

---

## 🔄 Logic Flow

```
ENTRY
  Vehicle arrives at gate
       │
       ▼
  EntryGate.processEntry()
       │
       ▼
  ParkingService.checkIn()
       │
       ▼
  ParkingLot.generateParkingTicket()
       │  maps VehicleType → SlotType
       ▼
  SlotAssignmentStrategy.findSlot()   ← Min-Heap, O(log n)
       │  atomically tryOccupy() on winning slot
       ▼
  Ticket issued ─── stored in ConcurrentHashMap


EXIT
  Driver presents Ticket ID at gate
       │
       ▼
  ExitGate.processExit()
       │
       ▼
  ParkingService.checkOut()
       │
       ▼
  BillingService.generateBill()       ← ⌈duration_hours⌉ × rate
       │
       ▼
  ParkingLot.closeTicket()            ← slot.release() + remove from map
```

---

## 🧵 Thread Safety

Two gates racing to park vehicles can never double-book a slot:

1. **`NearestSlotStrategy`** builds a local min-heap (thread-local, no sharing).
2. It calls `slot.tryOccupy()` which acquires the slot's own **`ReentrantLock`** atomically.
3. If a concurrent thread already grabbed the slot, `tryOccupy()` returns `false` and the strategy retries with the next-nearest candidate.
4. Active tickets are stored in a **`ConcurrentHashMap`** for lock-free O(1) reads.

> No global lock is held during slot search — throughput scales with the number of slots.

---

## 💰 Billing Rate Card

| Slot Type | Vehicle    | Rate         |
|-----------|------------|--------------|
| SMALL     | 2-Wheeler  | ₹ 20 / hr   |
| MEDIUM    | Car        | ₹ 40 / hr   |
| LARGE     | Bus        | ₹ 100 / hr  |

- Duration is **rounded up** to the next full hour (`Math.ceil`).
- Minimum charge = **1 hour**.
- Rates are configurable via `BillingService.setHourlyRate(SlotType, double)`.

---

## 📐 Distance Model

Each `ParkingSlot` stores a `Map<gateId, distance>` — distances are set at configuration time (in `Main.java`) and can come from any source (DB, config file, computed geometry). The `NearestSlotStrategy` reads `slot.getDistance(gateId)` to populate the heap.

**Example configuration (Floor 1, MEDIUM slots):**
```
Gate E1 → F1-M-01 : 15 units  (closest)
Gate E1 → F1-M-02 : 30 units
Gate E1 → F1-M-03 : 45 units  (farthest)
```

---

## 🚀 How to Compile & Run

```bash
# From the project root (Parking Lot/)
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.parkinglot.Main
```

Requires **Java 17+** (uses sealed-switch expressions).

---

## 🧩 Extending the System

| Feature                  | What to change                                              |
|--------------------------|-------------------------------------------------------------|
| New vehicle type (EV)    | Add `EV` to `VehicleType`, `EV_CHARGING` to `SlotType`, update `ParkingSlotFactory` and the mapping in `ParkingLot` |
| New pricing strategy     | Add method to `BillingService` or override with a subclass  |
| New slot-search strategy | Implement `SlotAssignmentStrategy`, inject into `ParkingLot.setSlotAssignmentStrategy()` |
| Persistence              | Wrap `ConcurrentHashMap` in a repository interface backed by a DB |
| REST API                 | Add Spring controllers that call `ParkingService`           |
