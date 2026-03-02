public class WhatsAppSender implements MessageSender {

    @Override
    public void send(Notification notification) {
        System.out.println("Sending WhatsApp to " + notification.getRecipient());
        System.out.println("Message: " + notification.getMessage());
    }
}