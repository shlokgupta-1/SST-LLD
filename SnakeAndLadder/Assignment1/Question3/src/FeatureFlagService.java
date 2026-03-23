public interface FeatureFlagService {
    boolean isEnabled(String flagName);
}