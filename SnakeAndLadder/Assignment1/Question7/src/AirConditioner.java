public class AirConditioner implements SmartClassroomDevice {

    @Override
    public void turnOn() {
        System.out.println("Air Conditioner ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Air Conditioner OFF");
    }

    @Override
    public String getName() {
        return "Air Conditioner";
    }
}