public class ConsolePreview implements Previewable {

    @Override
    public void preview(Notification notification) {
        System.out.println("Preview:");
        System.out.println("To: " + notification.getRecipient());
        System.out.println("Message: " + notification.getMessage());
    }
}