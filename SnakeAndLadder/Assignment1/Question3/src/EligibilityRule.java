public interface EligibilityRule {
    boolean evaluate(StudentProfile profile);
}