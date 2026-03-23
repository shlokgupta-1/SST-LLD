# Ex5 — OCP Refactoring (Data Export System)



## Checkpoint A — Responsibility & OCP Violation Analysis

### Task
Analyze how exporters are selected and executed.

### Findings
The system originally required modification when adding a new export format.

Typical issue:

- Conditional logic (if/else or switch) to choose exporter
- Adding new format requires modifying existing selection logic

### Problem
This violates **Open/Closed Principle (OCP)**:

> Software entities should be open for extension but closed for modification.

When adding a new exporter:
- Existing code must change ❌
- Risk of breaking working code ❌



## Checkpoint B — Introduce Abstraction

### Change
Use `Exporter` interface as abstraction.

All exporters implement:

Exporter

### Benefit
New exporters can be added without modifying existing ones.



## Checkpoint C — Remove Conditional Logic

### Change
Introduce `ExporterFactory` (or registry-based approach).

Selection logic moved into factory.

Main system depends only on `Exporter` abstraction.



## Checkpoint D — Dependency Inversion

### Change
Main program depends on abstraction (`Exporter`) instead of concrete classes.

### Benefit
System becomes:

✔ Extensible  
✔ Maintainable  
✔ OCP compliant  
✔ Loosely coupled  



## Refactored Flow

1. ExportRequest created  
2. ExporterFactory selects appropriate exporter  
3. Exporter performs export  
4. ExportResult returned  



## Final Outcome

✔ OCP compliance  
✔ New formats require no modification of existing code  
✔ Cleaner architecture  
✔ Reduced coupling  
✔ High extensibility  
✔ Safer future enhancements  