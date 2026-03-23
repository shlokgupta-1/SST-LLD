# Ex4 — SRP Refactoring (Hostel Booking System)



## Checkpoint A — Responsibility Analysis

### Task
Identify responsibilities inside `HostelFeeCalculator.processBooking`.

### Findings
The class handled multiple concerns:

- **Room Type Validation** → Checking room types via LegacyRoomTypes  
- **Base Fee Calculation** → Calculating room cost  
- **Add-On Fee Calculation** → Summing selected add-ons  
- **Money Handling** → Creating Money objects  
- **Persistence** → Saving booking via FakeBookingRepo  
- **Receipt Formatting** → Printing receipt via ReceiptPrinter  
- **Workflow Orchestration** → Coordinating entire process  

### Issue
This created a **God Class** with multiple reasons to change:

| Change Type | Impact |
|-------------|--------|
| Room pricing rules | Affected |
| Add-on structure | Affected |
| Storage mechanism | Affected |
| Receipt format | Affected |
| Validation rules | Affected |

➡ Violates Single Responsibility Principle (SRP)



## Checkpoint B — Extract Pricing Logic

### Change
Created `RoomPricingPolicy` abstraction.

Room pricing logic moved out of calculator.

### Benefit
Room pricing changes do not affect booking workflow.



## Checkpoint C — Extract Add-On Calculation

### Change
Created `AddOnPricingService`.

Add-on fee calculation isolated.

### Benefit
Add-on changes affect only this service.



## Checkpoint D — Decouple Persistence

### Change
Introduced `BookingRepository` abstraction.

`FakeBookingRepo` implements it.

### Benefit
Storage mechanism can change safely.



## Checkpoint E — Extract Receipt Formatting

### Change
Receipt generation moved to `ReceiptPrinter`.

### Benefit
Receipt format changes do not affect business logic.



## Refactored Flow

1. BookingRequest received  
2. Room price calculated  
3. Add-on cost calculated  
4. Total computed  
5. Booking saved  
6. Receipt printed  



## Final Outcome

✔ SRP compliance  
✔ No God Class  
✔ High cohesion  
✔ Low coupling  
✔ Extensible pricing  
✔ Swappable storage  
✔ Cleaner architecture  
✔ Easier testing  