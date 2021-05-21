package entity;

public class User {

    private String name;
    private String password;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //    Map<String, String> users = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Map<String, String> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Map<String, String> users) {
//        this.users = users;
//    }
}
