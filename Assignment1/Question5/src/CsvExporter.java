public class CsvExporter implements Exporter {

    @Override
    public ExportResult export(ExportRequest request) {
        String content = "CSV_DATA: " + request.getData();
        return new ExportResult(true, content);
    }
}