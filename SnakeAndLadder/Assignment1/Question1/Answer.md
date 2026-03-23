# Ex1 — SRP Refactoring



## Checkpoint A — Responsibility Analysis

### Task
Identify responsibilities inside `OnboardingService.registerFromRawInput`.

### Findings
The method handled multiple concerns:

- **Parsing** → Extracting fields from raw input  
- **Validation** → Checking business rules  
- **ID Generation** → Creating student ID  
- **Persistence** → Saving via FakeDb  
- **Printing** → Formatting console output  
- **Error Handling** → Printing validation failures  

### Issue
This created a **God Method** with multiple reasons to change:

| Change Type | Impact |
|-------------|--------|
| Input format | Affected |
| Validation rules | Affected |
| ID logic | Affected |
| Database | Affected |
| Output formatting | Affected |

➡ **Violates Single Responsibility Principle (SRP)**.



## Checkpoint B — Extract Parsing

### Change
Parsing logic moved to `StudentInputParser`.

### Result
`OnboardingService` no longer manipulates raw strings directly.

### Benefit
Input format changes affect only the parser.



## Checkpoint C — Extract Validation

### Change
Validation rules moved to `StudentValidator`.

### Result
Errors returned as `List<String>` instead of printed inline.

### Benefit
Validation is testable and business rules are centralized.



## Checkpoint D — Decouple Persistence

### Change
Persistence separated via abstraction (`StudentRepository`).

### Result
Workflow no longer tightly coupled to FakeDb.

### Benefit
Storage mechanism can change safely.



## Checkpoint E — Extract Printing

### Change
Console output moved to `ConsolePrinter`.

### Result
Business logic separated from UI/formatting.

### Benefit
Output changes do not affect onboarding logic.



## 🔧 Key Changes in `OnboardingService.java`

### Before Refactor
`registerFromRawInput()` contained:

❌ Parsing  
❌ Validation  
❌ Printing  
❌ Persistence logic  



### After Refactor
`OnboardingService` now:

✔ Delegates parsing → `StudentInputParser`  
✔ Delegates validation → `StudentValidator`  
✔ Delegates printing → `ConsolePrinter`  
✔ Retains workflow orchestration only  



## 🔄 How the Refactored Flow Works

1️⃣ Raw input received  
2️⃣ Parser converts → structured data  
3️⃣ Validator checks rules → returns errors  
4️⃣ If valid → generate ID  
5️⃣ Create `StudentRecord`  
6️⃣ Save via DB  
7️⃣ Printer displays output  

➡ **Clear separation of responsibilities**



## Final Outcome

✔ SRP compliance  
✔ Reduced complexity  
✔ Better modularity  
✔ Easier testing  
✔ Lower change impact  
✔ Cleaner design  