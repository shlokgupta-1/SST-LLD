package models;

public class Admin extends User {
    private final String adminCode;

    public Admin(String name, String email, String phone, String adminCode) {
        super(name, email, phone);
        this.adminCode = adminCode;
    }

    public String getAdminCode() { return adminCode; }

    @Override
    public String toString() {
        return "Admin{id=" + getId() + ", name=" + getName() + "}";
    }
}
