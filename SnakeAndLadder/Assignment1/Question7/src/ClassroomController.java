import java.util.List;

public class ClassroomController {

    private final List<SmartClassroomDevice> devices;

    public ClassroomController(List<SmartClassroomDevice> devices) {
        this.devices = devices;
    }

    public void startClass() {
        for (SmartClassroomDevice device : devices) {
            device.turnOn();
        }
    }

    public void endClass() {
        for (SmartClassroomDevice device : devices) {
            device.turnOff();
        }
    }
}