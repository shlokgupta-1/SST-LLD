public class StudentProfile {

    private final String id;
    private final double score;
    private final double attendance;

    public StudentProfile(String id, double score, double attendance) {
        this.id = id;
        this.score = score;
        this.attendance = attendance;
    }

    public String getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public double getAttendance() {
        return attendance;
    }
}