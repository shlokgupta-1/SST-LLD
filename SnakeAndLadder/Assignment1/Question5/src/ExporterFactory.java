import java.util.HashMap;
import java.util.Map;

public class ExporterFactory {

    private final Map<String, Exporter> exporters = new HashMap<>();

    public ExporterFactory() {
        exporters.put("CSV", new CsvExporter());
        exporters.put("JSON", new JsonExporter());
        exporters.put("PDF", new PdfExporter());
    }

    public Exporter getExporter(String type) {

        Exporter exporter = exporters.get(type.toUpperCase());

        if (exporter == null) {
            throw new IllegalArgumentException("Unsupported export type");
        }

        return exporter;
    }
}