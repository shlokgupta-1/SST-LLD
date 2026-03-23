public class MinimumScoreRule implements EligibilityRule {

    private final double minimumScore;

    public MinimumScoreRule(double minimumScore) {
        this.minimumScore = minimumScore;
    }

    @Override
    public boolean evaluate(StudentProfile profile) {
        return profile.getScore() >= minimumScore;
    }
}