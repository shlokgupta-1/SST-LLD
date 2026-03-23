public class SecretaryTool implements MinutesOperations {

    private final MinutesBook minutesBook;

    public SecretaryTool(MinutesBook minutesBook) {
        this.minutesBook = minutesBook;
    }

    @Override
    public void addMinutes(String note) {
        minutesBook.add(note);
    }

    @Override
    public int getMinutesCount() {
        return minutesBook.count();
    }
}