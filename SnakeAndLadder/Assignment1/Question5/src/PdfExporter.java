public class PdfExporter implements Exporter {

    @Override
    public ExportResult export(ExportRequest request) {
        String content = "PDF_CONTENT: " + request.getData();
        return new ExportResult(true, content);
    }
}