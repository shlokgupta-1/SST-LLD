# Ex3 — SRP Refactoring (Eligibility Engine)



## Checkpoint A — Responsibility Analysis

### Task
Identify responsibilities inside `EligibilityEngine.process`.

### Findings
The class handled multiple concerns:

- **Input Handling** → Reading RuleInput and StudentProfile
- **Eligibility Rule Evaluation** → Checking conditions
- **Feature Flag Checking** → Using LegacyFlags
- **Persistence** → Saving via FakeEligibilityStore
- **Report Printing** → Printing results using ReportPrinter
- **Decision Aggregation** → Combining rule outcomes

### Issue
This created a **God Class** with multiple reasons to change:

| Change Type | Impact |
|-------------|--------|
| Rule changes | Affected |
| Feature flag logic | Affected |
| Storage mechanism | Affected |
| Output format | Affected |
| Input structure | Affected |

➡ Violates Single Responsibility Principle (SRP)



## Checkpoint B — Extract Rule Evaluation

### Change
Created `EligibilityRule` interface.

Concrete rule classes implement evaluation logic.

### Benefit
New rules can be added without modifying the engine.



## Checkpoint C — Decouple Feature Flags

### Change
Introduced `FeatureFlagService`.

Engine no longer directly depends on `LegacyFlags`.

### Benefit
Flag system changes do not affect business logic.



## Checkpoint D — Decouple Persistence

### Change
Introduced `EligibilityRepository` abstraction.

`FakeEligibilityStore` implements it.

### Benefit
Storage implementation can change safely.



## Checkpoint E — Extract Reporting

### Change
Report printing moved to `ReportPrinter`.

### Benefit
Output formatting changes do not affect eligibility logic.



## Refactored Flow

1. StudentProfile received
2. Rules evaluated
3. Flags checked
4. Decision computed
5. Result saved
6. Report printed



## Final Outcome

✔ SRP compliance  
✔ No God Class  
✔ High cohesion  
✔ Low coupling  
✔ Extensible rule system  
✔ Swappable storage  
✔ Cleaner architecture  
✔ Easier testing  