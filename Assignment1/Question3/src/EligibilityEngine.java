import java.util.List;

public class EligibilityEngine {

    private final List<EligibilityRule> rules;
    private final FeatureFlagService flagService;
    private final EligibilityRepository repository;
    private final ReportPrinter printer;

    public EligibilityEngine(List<EligibilityRule> rules,
                             FeatureFlagService flagService,
                             EligibilityRepository repository,
                             ReportPrinter printer) {
        this.rules = rules;
        this.flagService = flagService;
        this.repository = repository;
        this.printer = printer;
    }

    public void process(StudentProfile profile) {

        boolean eligible = true;

        for (EligibilityRule rule : rules) {
            if (!rule.evaluate(profile)) {
                eligible = false;
                break;
            }
        }

        if (flagService.isEnabled("STRICT_MODE")) {
            eligible = eligible && profile.getAttendance() >= 75;
        }

        repository.save(profile.getId(), eligible);

        printer.print(profile.getId(), eligible);
    }
}