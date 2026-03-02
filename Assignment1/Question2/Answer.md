# Ex2 — SRP Refactoring



## Checkpoint A — Responsibility Analysis

### Task  
Identify responsibilities inside `CafeteriaSystem.checkout`.

### Findings  
The method handled multiple concerns:

- **Menu Lookup** → Fetching `MenuItem` from map  
- **Subtotal Calculation** → Computing line totals  
- **Tax Calculation** → Applying tax rules  
- **Discount Calculation** → Applying business discounts  
- **Invoice ID Generation** → Creating unique invoice number  
- **Invoice Formatting** → Building formatted text output  
- **Persistence** → Saving invoice using `FileStore`  
- **Output Return** → Returning formatted invoice  

### Issue  
This created a **God Method** with multiple reasons to change:

| Change Type | Impact |
|-------------|--------|
| Menu structure | Affected |
| Pricing rules | Affected |
| Tax policy | Affected |
| Discount rules | Affected |
| Invoice format | Affected |
| Storage mechanism | Affected |

➡ **Violates Single Responsibility Principle (SRP)**.



## Checkpoint B — Extract Pricing Logic

### Change  
Subtotal calculation moved to `PricingService`.

### Result  
`CafeteriaSystem` no longer calculates line totals directly.

### Benefit  
Pricing logic changes affect only `PricingService`.



## Checkpoint C — Extract Tax Logic

### Change  
Tax logic moved to `TaxPolicy` interface and implemented in `DefaultTaxPolicy`.

### Result  
Tax rules are no longer hardcoded in checkout.

### Benefit  
Tax structure can change without modifying core workflow.



## Checkpoint D — Extract Discount Logic

### Change  
Discount logic moved to `DiscountPolicy` interface and implemented in `DefaultDiscountPolicy`.

### Result  
Discount rules centralized and independent.

### Benefit  
New discount schemes can be added safely.



## Checkpoint E — Extract Invoice Formatting

### Change  
Formatting logic moved to `InvoiceFormatter` interface with implementation `SimpleInvoiceFormatter`.

### Result  
Checkout no longer builds string output.

### Benefit  
Invoice presentation changes do not affect business logic.



## Checkpoint F — Decouple Persistence

### Change  
Introduced abstraction `InvoiceRepository`.

`FileStore` now implements this interface.

### Result  
`CafeteriaSystem` depends on abstraction, not concrete storage.

### Benefit  
Storage mechanism can be changed (File → Database → Cloud) without modifying workflow.



## 🔧 Key Changes in `CafeteriaSystem.java`

### Before Refactor  
`checkout()` contained:

❌ Pricing logic  
❌ Tax logic  
❌ Discount logic  
❌ Formatting logic  
❌ File saving  
❌ ID generation  
❌ Business calculations  



### After Refactor  
`CafeteriaSystem` now:

✔ Delegates subtotal → `PricingService`  
✔ Delegates tax → `TaxPolicy`  
✔ Delegates discount → `DiscountPolicy`  
✔ Delegates formatting → `InvoiceFormatter`  
✔ Delegates persistence → `InvoiceRepository`  
✔ Acts only as workflow orchestrator  



## 🔄 How the Refactored Flow Works

1️⃣ Order received  
2️⃣ `PricingService` calculates subtotal  
3️⃣ `TaxPolicy` calculates tax percentage  
4️⃣ `DiscountPolicy` computes discount  
5️⃣ `Invoice` object created  
6️⃣ `InvoiceFormatter` formats output  
7️⃣ `InvoiceRepository` saves invoice  
8️⃣ Formatted invoice returned  

➡ **Clear separation of responsibilities**



## Final Outcome

✔ SRP compliance  
✔ No God Method  
✔ Reduced coupling  
✔ High cohesion  
✔ Extensible tax & discount rules  
✔ Swappable storage mechanism  
✔ Cleaner, maintainable architecture  
✔ Easier unit testing  