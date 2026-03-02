public class LightsPanel implements SmartClassroomDevice {

    @Override
    public void turnOn() {
        System.out.println("Lights ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Lights OFF");
    }

    @Override
    public String getName() {
        return "Lights Panel";
    }
}