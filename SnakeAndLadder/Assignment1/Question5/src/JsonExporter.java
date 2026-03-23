public class JsonExporter implements Exporter {

    @Override
    public ExportResult export(ExportRequest request) {
        String content = "{ \"data\": \"" + request.getData() + "\" }";
        return new ExportResult(true, content);
    }
}