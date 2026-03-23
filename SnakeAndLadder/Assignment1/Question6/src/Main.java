public class Main {

    public static void main(String[] args) {

        Notification notification =
                new Notification("user@example.com", "Hello from system");

        MessageSender sender = new EmailSender();
        Previewable preview = new ConsolePreview();
        AuditLog audit = new AuditLog();

        preview.preview(notification);
        sender.send(notification);
        audit.record(notification);
    }
}