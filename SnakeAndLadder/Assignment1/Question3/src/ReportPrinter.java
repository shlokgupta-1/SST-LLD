public class ReportPrinter {

    public void print(String studentId, boolean result) {
        System.out.println("Student ID: " + studentId);
        System.out.println("Eligibility: " + (result ? "APPROVED" : "REJECTED"));
    }
}