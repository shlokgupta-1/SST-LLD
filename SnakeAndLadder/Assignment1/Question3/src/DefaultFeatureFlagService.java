public class DefaultFeatureFlagService implements FeatureFlagService {

    @Override
    public boolean isEnabled(String flagName) {
        return LegacyFlags.isEnabled(flagName);
    }
}
