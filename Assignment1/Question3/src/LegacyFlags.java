public class LegacyFlags {

    public static boolean isEnabled(String flagName) {

        if (flagName.equals("STRICT_MODE")) {
            return true; // or false depending on test
        }

        return false;
    }
}