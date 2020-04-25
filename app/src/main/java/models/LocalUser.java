package models;

public class LocalUser {
    private String name;
    private int userNumber;

    public LocalUser(String name, int userNumber) {
        this.name = name;
        this.userNumber = userNumber;
    }

    public String getName() {
        return name;
    }

    public int getUserNumber() {
        return userNumber;
    }

}
