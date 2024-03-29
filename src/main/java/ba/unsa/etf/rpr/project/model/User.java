package ba.unsa.etf.rpr.project.model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String mail;

    public User(Integer id, String firstName, String lastName, String username, String mail, String password) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mail=mail;
        this.password = password;

    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String email) {
        this.mail = email;
    }
}
