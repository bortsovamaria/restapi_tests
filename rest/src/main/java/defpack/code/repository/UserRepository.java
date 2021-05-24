package defpack.code.repository;

import code.entity.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRepository {


    static Map<String, User> users = new Hashtable<>(); //DB

    static {
        users.put("root", new User("root", null,"root"));
        users.put("user", new User("user", null, "1234"));
        users.put("user2", new User("user2", null,"password"));
    }

    public User userByName(String name) {
        return users.get(name);
    }

    public User addUser(User u) {
        users.put(u.getName(), u);
        return u;
    }

    public List<User> listUsers() {
        List<User> list = new ArrayList<>();
        for (Map.Entry user : users.entrySet()) {
            list.add((User) user.getValue());
        }

        return list;
    }

    public List<User> listUsers(String userNameMask) {

        List<User> list = new ArrayList<>();

        Pattern p = Pattern.compile(userNameMask);

        for (Map.Entry map : users.entrySet()) {
            Matcher m = p.matcher((CharSequence) map.getKey());
            if (m.matches()) {
                list.add((User) map.getValue());
            }
        }

        return list;
    }

//    List<User> users = new ArrayList<>();

//    public void save(User user) {
//        users.indexOf(user);
//        users.remove()
//    }

//    public void add(User user) {
//        users.add(user);
//    }
}
