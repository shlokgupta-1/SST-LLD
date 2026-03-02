# Ex10 — DIP Refactoring (Campus Transport Booking)



## Checkpoint A — Current Behavior & Problem Analysis

### Task
Run the existing program and observe how `TransportBookingService.book()` works.

### Observed Behavior

The booking service:

1. Instantiates concrete classes directly:
   - `DistanceCalculator`
   - `DriverAllocator`
   - `PaymentGateway`
2. Calculates trip distance
3. Allocates a driver
4. Processes payment
5. Prints receipt

Sample output:

=== Transport Booking ===  
DistanceKm=6.0  
Driver=DRV-17  
Payment=PAID txn=TXN-9001  
RECEIPT: R-501 | fare=90.00  



### Problems Identified

1. **High-level module depends on low-level modules**  
   `TransportBookingService` directly creates concrete services using `new`.

2. **Tight coupling**  
   Changing payment gateway or allocator requires editing booking service.

3. **Hard to test**  
   Cannot inject mock gateway or allocator.

4. **Business logic mixed with infrastructure calls**  
   Pricing calculation and external service calls are combined.

5. **No abstraction boundaries**  
   Responsibilities are not clearly separated.

This violates the **Dependency Inversion Principle (DIP)**:

> High-level modules should not depend on low-level modules.  
> Both should depend on abstractions.



## Checkpoint B — Introduce Abstractions

We define minimal interfaces required by the booking service:

- `DistanceService`
- `DriverAllocationService`
- `PaymentService`

Each interface contains only what the booking service needs.

Example:

interface DistanceService {
    double calculate(TripRequest request);
}

interface DriverAllocationService {
    String allocate(TripRequest request);
}

interface PaymentService {
    String charge(double amount);
}



## Checkpoint C — Inject Dependencies

Instead of creating dependencies inside the service:

new PaymentGateway()

We inject them via constructor:

TransportBookingService(
    DistanceService distanceService,
    DriverAllocationService driverService,
    PaymentService paymentService
)

Now the booking service depends only on abstractions.



## Checkpoint D — Preserve Output

All printed lines and order remain identical:

=== Transport Booking ===  
DistanceKm=6.0  
Driver=DRV-17  
Payment=PAID txn=TXN-9001  
RECEIPT: R-501 | fare=90.00  

Internal structure changed — external behavior preserved.



## Refactored Architecture

High-Level Module:
- TransportBookingService

Abstractions:
- DistanceService
- DriverAllocationService
- PaymentService

Low-Level Modules:
- SimpleDistanceCalculator
- DefaultDriverAllocator
- OnlinePaymentGateway



## Before vs After

| Aspect | Before | After |
|--------|--------|-------|
| Dependency | Concrete classes | Interfaces |
| Coupling | Tight | Loose |
| Testability | Hard | Easy |
| Extensibility | Low | High |
| DIP compliance | Violated | Satisfied |



## Acceptance Criteria Verification

✔ Booking service depends only on abstractions  
✔ Concrete implementations injected  
✔ Receipt output preserved  
✔ Easy to substitute test doubles  
✔ No external libraries used  



## Stretch Goal

Now we can add:

- `MockPaymentService`
- `MockDriverAllocator`
- `DynamicPricingDistanceService`

Without modifying `TransportBookingService`.

The high-level booking logic remains stable and independent.



## Conclusion

By introducing abstractions and injecting dependencies into `TransportBookingService`,
the system now follows the Dependency Inversion Principle.

The booking logic is independent of infrastructure details, resulting in:

✔ Cleaner architecture  
✔ Reduced coupling  
✔ Improved testability  
✔ Higher flexibility  
✔ Better maintainability  