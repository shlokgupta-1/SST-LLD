public class AuditLog {

    public void record(Notification notification) {
        System.out.println("Audit Log: Notification sent to "
                + notification.getRecipient());
    }
}