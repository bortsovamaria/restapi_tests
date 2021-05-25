package defpack.code.repository;

import defpack.code.config.ConfigRepository;
import defpack.code.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    imitation DB
 */
public class UserRepository {

    ConfigRepository configRepository = new ConfigRepository();

//    @Autowired
//    ConfigRepository configRepository;

    static Map<String, User> users = new Hashtable<>(); //DB

    /*
     prepared data
     */
    static {
        users.put("root", new User("root", null,"root"));
        users.put("user", new User("user", null, "1234"));
        users.put("user2", new User("user2", null,"password"));
    }

    public User userByName(String name) {
        return users.get(name);
    }

    /*
        add user in repository
     */
    public User addUser(User u) {
        /*
            according regex
         */
        Pattern p = Pattern.compile(configRepository.getUserName());
        Pattern p2 = Pattern.compile(configRepository.getPassword());

        Matcher m = p.matcher(u.getName());
        Matcher m2 = p2.matcher(u.getPassword());
//
        if (m.matches() || m2.matches()) {
            users.put(u.getName(), u);
        }
        return u;
    }

    /*
        get list users without mask
     */
    public List<User> listUsers() {
        List<User> list = new ArrayList<>();
        for (Map.Entry user : users.entrySet()) {
            list.add((User) user.getValue());
        }

        return list;
    }

    /*
        get list users with mask
     */
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

}
