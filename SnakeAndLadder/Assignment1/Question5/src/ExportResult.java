public class ExportResult {

    private final boolean success;
    private final String content;

    public ExportResult(boolean success, String content) {
        this.success = success;
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getContent() {
        return content;
    }
}