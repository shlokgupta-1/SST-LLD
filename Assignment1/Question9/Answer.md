# Ex9 — DIP Refactoring (Assignment Evaluation Pipeline)



## Checkpoint A — Current Behavior & Problem Analysis

### Task
Run the existing program and observe how `EvaluationPipeline.evaluate()` works.

### Observed Behavior

The pipeline:

1. Instantiates concrete components using `new`
   - Plagiarism checker
   - Code grader
   - Report writer
2. Runs evaluation steps
3. Prints final result
4. Writes report

Sample output:

=== Evaluation Pipeline ===  
PlagiarismScore=12  
CodeScore=78  
Report written: report-23BCS1007.txt  
FINAL: PASS (total=90)



### Problems Identified

1. **High-level module depends on low-level modules**  
   `EvaluationPipeline` directly creates concrete classes.

2. **Tight coupling**  
   Changing grader or checker requires modifying pipeline.

3. **Hard to test**  
   Cannot inject mock grader/checker for testing.

4. **No abstraction boundaries**  
   Responsibilities mixed inside pipeline.

5. **Configuration embedded**  
   Paths, thresholds, logic hard-coded inside pipeline.

This violates the **Dependency Inversion Principle (DIP)**:

> High-level modules should not depend on low-level modules.  
> Both should depend on abstractions.



## Checkpoint B — Introduce Abstractions

To decouple the pipeline, we introduced small focused interfaces:

- `PlagiarismChecker`
- `CodeGrader`
- `ReportWriter`

Each abstraction defines only what the pipeline requires.

Example:

interface PlagiarismChecker {
    int check(Submission submission);
}

interface CodeGrader {
    int grade(Submission submission);
}

interface ReportWriter {
    void write(Submission submission, int totalScore);
}



## Checkpoint C — Inject Dependencies

Instead of:

new PlagiarismCheckerImpl()

Dependencies are injected via constructor:

EvaluationPipeline(
    PlagiarismChecker checker,
    CodeGrader grader,
    ReportWriter writer
)

Now the pipeline depends only on abstractions.

Concrete implementations are created in `Main` and passed to the pipeline.



## Checkpoint D — Preserve Output

All output lines and order remain identical:

=== Evaluation Pipeline ===  
PlagiarismScore=12  
CodeScore=78  
Report written: report-23BCS1007.txt  
FINAL: PASS (total=90)

Only internal structure changed — behavior preserved.



## Refactored Architecture

High-Level Module:
- EvaluationPipeline

Abstractions:
- PlagiarismChecker
- CodeGrader
- ReportWriter

Low-Level Modules:
- SimplePlagiarismChecker
- SimpleCodeGrader
- FileReportWriter



## Before vs After

| Aspect | Before | After |
|--------|--------|-------|
| Dependency | Concrete classes | Interfaces |
| Coupling | Tight | Loose |
| Testability | Hard | Easy |
| Extensibility | Low | High |
| DIP compliance | Violated | Satisfied |



## Acceptance Criteria Verification

✔ Pipeline depends on abstractions  
✔ Concrete implementations injected  
✔ Output preserved  
✔ Easy to substitute test doubles  
✔ No external libraries used  



## Stretch Goal Possibility

Now we can add:

- `AdvancedCodeGrader`
- `AIPlagiarismChecker`
- `CloudReportWriter`

Without modifying `EvaluationPipeline`.

The pipeline remains closed for modification and open for extension.



## Conclusion

By introducing abstractions and injecting dependencies into `EvaluationPipeline`,
the system now follows the Dependency Inversion Principle.

The high-level evaluation workflow is independent of concrete implementations,
resulting in:

✔ Cleaner architecture  
✔ Lower coupling  
✔ Higher testability  
✔ Better extensibility  
✔ Maintainable design  