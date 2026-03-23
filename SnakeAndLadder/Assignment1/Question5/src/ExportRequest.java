public class ExportRequest {

    private final String type;
    private final String data;

    public ExportRequest(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}