# Ex6 — ISP Refactoring (Notification System)



## Checkpoint A — Responsibility & ISP Violation Analysis

### Task
Analyze the `NotificationSender` interface and its implementations.

### Findings

The original design had a broad interface:

- send()
- preview()
- audit()

All sender classes (`EmailSender`, `SmsSender`, `WhatsAppSender`) were forced to implement all methods.

### Problem

Not all senders require all behaviors:

- Some senders may not support preview.
- Some may not handle audit internally.
- Some may not require configuration.

This violates the **Interface Segregation Principle (ISP)**:

> Clients should not be forced to depend on methods they do not use.

Large interfaces create:

- Unused method implementations
- Empty method bodies
- Tight coupling
- Poor design



## Checkpoint B — Split Interfaces

### Change

Instead of one large interface, we split it into smaller focused interfaces:

- `MessageSender`
- `Previewable`
- `Auditable`

Each interface contains only related methods.

### Benefit

Classes implement only what they need.

No forced implementation.



## Checkpoint C — Apply Composition

### Change

Audit logging moved to `AuditLog` class.

Preview logic separated into `ConsolePreview`.

Senders now focus only on sending.



## Refactored Structure

- `MessageSender` → defines send()
- `Previewable` → defines preview()
- `Auditable` → defines audit()
- Concrete senders implement only required interfaces
- Main coordinates components



## Refactored Flow

1. Notification created  
2. Sender sends message  
3. Optional preview performed  
4. Audit log records event  



## Before vs After

| Aspect | Before | After |
|--------|--------|-------|
| Interface size | Large | Segregated |
| Unused methods | Yes | No |
| Coupling | High | Low |
| ISP compliance | Violated | Satisfied |



## Final Outcome

✔ ISP compliance  
✔ Smaller focused interfaces  
✔ Higher cohesion  
✔ Lower coupling  
✔ Cleaner architecture  
✔ Easier maintenance  
✔ Better extensibility  



## Conclusion

By segregating the large `NotificationSender` interface into smaller role-based interfaces, the system now follows the Interface Segregation Principle.

Each class depends only on the methods it actually uses, resulting in a cleaner and more maintainable design.