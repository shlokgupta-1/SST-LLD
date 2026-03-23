public class Projector implements SmartClassroomDevice {

    @Override
    public void turnOn() {
        System.out.println("Projector ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Projector OFF");
    }

    @Override
    public String getName() {
        return "Projector";
    }
}