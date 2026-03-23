public class SmsSender implements MessageSender {

    @Override
    public void send(Notification notification) {
        System.out.println("Sending SMS to " + notification.getRecipient());
        System.out.println("Message: " + notification.getMessage());
    }
}