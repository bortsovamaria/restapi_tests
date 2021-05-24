package defpack.code.entity;

/*
    Model User
 */
public class User {

    private String name;
    private String password;
    private String oldPassword;

    public User() {

    }

//    public User(String name, String password) {
//        this.name = name;
//        this.password = password;
//    }

    public User(String name, String oldPassword, String password) {
        this.name = name;
        this.oldPassword = oldPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

//    public Map<String, String> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Map<String, String> users) {
//        this.users = users;
//    }
}
