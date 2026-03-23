import java.util.*;

public class OnboardingService {
    private final FakeDb db;

    //Delegated responsibilities
    private final StudentInputParser parser = new StudentInputParser();
    private final StudentValidator validator = new StudentValidator();
    private final ConsolePrinter printer = new ConsolePrinter();

    public OnboardingService(FakeDb db) {
        this.db = db;
    }

    public void registerFromRawInput(String raw) {

        printer.printInput(raw);

        ParsedStudentData data = parser.parse(raw);

        List<String> errors = validator.validate(data);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());

        StudentRecord rec = new StudentRecord(
                id, data.name, data.email, data.phone, data.program
        );

        db.save(rec);

        printer.printSuccess(id);
        printer.printSaved(db.count());
        printer.printConfirmation(rec);
    }
}

class ParsedStudentData {
    final String name;
    final String email;
    final String phone;
    final String program;

    ParsedStudentData(String name, String email, String phone, String program) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.program = program;
    }
}

class StudentInputParser {

    ParsedStudentData parse(String raw) {
        String[] parts = raw.split(";");
        String name = "", email = "", phone = "", program = "";

        for (String part : parts) {
            String[] kv = part.split("=", 2);
            if (kv.length < 2) continue;

            switch (kv[0].trim()) {
                case "name": name = kv[1].trim(); break;
                case "email": email = kv[1].trim(); break;
                case "phone": phone = kv[1].trim(); break;
                case "program": program = kv[1].trim(); break;
            }
        }

        return new ParsedStudentData(name, email, phone, program);
    }
}

class StudentValidator {

    List<String> validate(ParsedStudentData data) {
        List<String> errors = new ArrayList<>();

        if (data.name.isBlank()) errors.add("name is required");
        if (data.email.isBlank() || !data.email.contains("@"))
            errors.add("email is invalid");
        if (data.phone.isBlank() || !data.phone.matches("\\d+"))
            errors.add("phone is invalid");
        if (!(data.program.equals("CSE") ||
              data.program.equals("AI") ||
              data.program.equals("SWE")))
            errors.add("program is invalid");

        return errors;
    }
}

class ConsolePrinter {

    void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) {
            System.out.println("- " + e);
        }
    }

    void printSuccess(String id) {
        System.out.println("OK: created student " + id);
    }

    void printSaved(int count) {
        System.out.println("Saved. Total students: " + count);
    }

    void printConfirmation(StudentRecord rec) {
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}