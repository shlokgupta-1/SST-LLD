import java.io.FileWriter;
import java.io.IOException;

public class FileStore implements InvoiceRepository {

    @Override
    public void save(String invoiceId, String content) {
        try (FileWriter writer = new FileWriter(invoiceId + ".txt")) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}