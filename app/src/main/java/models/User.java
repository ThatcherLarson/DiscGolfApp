package models;

public class User {
    private String first_name;
    private String last_name;
    private String email;
    private String uid;

    public User(String first, String last, String email, String uid) {
        first_name = first;
        last_name = last;
        this.email = email;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getUid() {
        return uid;
    }
}
