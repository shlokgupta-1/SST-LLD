public class Main {

    public static void main(String[] args) {

        DeviceRegistry registry = new DeviceRegistry();

        registry.register(new AirConditioner());
        registry.register(new LightsPanel());
        registry.register(new Projector());
        registry.register(new AttendanceScanner());

        ClassroomController controller =
                new ClassroomController(registry.getDevices());

        controller.startClass();

        System.out.println("Class in progress...");

        controller.endClass();
    }
}