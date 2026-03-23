public class EmailSender implements MessageSender {

    @Override
    public void send(Notification notification) {
        System.out.println("Sending EMAIL to " + notification.getRecipient());
        System.out.println("Message: " + notification.getMessage());
    }
}