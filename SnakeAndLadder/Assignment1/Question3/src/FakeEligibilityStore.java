import java.util.HashMap;
import java.util.Map;

public class FakeEligibilityStore implements EligibilityRepository {

    private final Map<String, Boolean> store = new HashMap<>();

    @Override
    public void save(String studentId, boolean result) {
        store.put(studentId, result);
    }
}