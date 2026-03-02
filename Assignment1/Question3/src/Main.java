import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        EligibilityRule scoreRule = new MinimumScoreRule(60);

        FeatureFlagService flagService = new DefaultFeatureFlagService();
        EligibilityRepository repository = new FakeEligibilityStore();
        ReportPrinter printer = new ReportPrinter();

        EligibilityEngine engine = new EligibilityEngine(
                Arrays.asList(scoreRule),
                flagService,
                repository,
                printer
        );

        StudentProfile profile = new StudentProfile("S1", 70, 80);

        engine.process(profile);
    }
}