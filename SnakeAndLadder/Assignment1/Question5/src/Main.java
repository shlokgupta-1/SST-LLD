public class Main {

    public static void main(String[] args) {

        ExporterFactory factory = new ExporterFactory();

        ExportRequest request = new ExportRequest(
                "CSV",
                SampleData.getData()
        );

        Exporter exporter = factory.getExporter(request.getType());

        ExportResult result = exporter.export(request);

        System.out.println(result.getContent());
    }
}