public class AttendanceScanner implements SmartClassroomDevice {

    @Override
    public void turnOn() {
        System.out.println("Attendance Scanner ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Attendance Scanner OFF");
    }

    @Override
    public String getName() {
        return "Attendance Scanner";
    }
}