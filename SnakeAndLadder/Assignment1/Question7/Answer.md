# Ex7 — DIP Refactoring (Smart Classroom System)



## Checkpoint A — Responsibility & DIP Violation Analysis

### Task
Analyze the dependency structure of `ClassroomController`.

### Findings

Originally, `ClassroomController` directly depended on concrete classes:

- AirConditioner
- LightsPanel
- Projector
- AttendanceScanner

Example:

ClassroomController → new AirConditioner()
ClassroomController → new Projector()

### Problem

This violates the **Dependency Inversion Principle (DIP)**:

> High-level modules should not depend on low-level modules.  
> Both should depend on abstractions.

Issues:

- Tight coupling
- Difficult to extend
- Hard to test
- Adding new device requires modifying controller



## Checkpoint B — Introduce Abstraction

### Change

Introduce abstraction:

SmartClassroomDevice

All devices implement this interface.

Controller depends only on this abstraction.



## Checkpoint C — Apply Dependency Injection

### Change

Devices are injected into `ClassroomController` via constructor.

Controller no longer creates concrete objects.

### Benefit

✔ Loose coupling  
✔ Extensible system  
✔ Testable design  
✔ DIP compliant  



## Refactored Architecture

High-Level Module:
- ClassroomController

Abstraction:
- SmartClassroomDevice

Low-Level Modules:
- AirConditioner
- LightsPanel
- Projector
- AttendanceScanner



## Refactored Flow

1. Devices registered in DeviceRegistry  
2. Controller receives devices via constructor  
3. Controller activates devices through abstraction  
4. Each device handles its own behavior  



## Before vs After

| Aspect | Before | After |
|--------|--------|-------|
| Dependency | Concrete classes | Abstraction |
| Coupling | Tight | Loose |
| Extensibility | Poor | High |
| DIP compliance | Violated | Satisfied |



## Final Outcome

✔ DIP compliance  
✔ High-level module independent of implementations  
✔ Cleaner architecture  
✔ Easily extensible  
✔ Improved testability  
✔ Reduced coupling  



## Conclusion

By introducing the `SmartClassroomDevice` abstraction and injecting dependencies into the controller, the system now follows the Dependency Inversion Principle.

The controller depends only on abstractions, making the system modular and extensible.