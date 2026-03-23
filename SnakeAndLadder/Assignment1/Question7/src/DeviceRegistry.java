import java.util.ArrayList;
import java.util.List;

public class DeviceRegistry {

    private final List<SmartClassroomDevice> devices = new ArrayList<>();

    public void register(SmartClassroomDevice device) {
        devices.add(device);
    }

    public List<SmartClassroomDevice> getDevices() {
        return devices;
    }
}