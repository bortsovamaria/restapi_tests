package defpack.code.controller;

import defpack.code.entity.User;
import defpack.code.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class Controller {

//    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    final static Logger logger = LogManager.getLogger(Controller.class);

    UserRepository userRepository = new UserRepository();

    /*
        post /user
     */
    @PostMapping(value = "/user",
                consumes="application/json",
                produces="application/json") //consumes = "text/plain", produces = MediaType.APPLICATION_JSON_VALUE
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) {

        User newUser = new User();

        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            logger.error("This password empty or null!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (userRepository.userByName(user.getName()) != null) {
            logger.error("This name exists!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        userRepository.addUser(newUser);

        logger.info("Add user");

        return new ResponseEntity<>(newUser, new HttpHeaders(), HttpStatus.OK);
    }

    /*
        post /updatePassword
     */
    @PostMapping(value = "/updatePassword",
                consumes = "application/json",
                produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> passwordUpdate(@RequestBody User user) {

        User newUser = new User();

        if (userRepository.userByName(user.getName()) != null) {

            if (!user.getOldPassword().equals(user.getPassword())) {
                newUser.setName(user.getName());
                newUser.setPassword(user.getPassword());
            } else {
                logger.info("test logger passwordUpdate");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        userRepository.addUser(newUser);

        return new ResponseEntity<>(newUser, new HttpHeaders(), HttpStatus.OK);
    }

    /*
        get /users
     */
    @GetMapping("/users")
    public List<User> users(@RequestParam(value = "userNameMask", required = false) String userNameMask) {

        if (userNameMask != null) {
            return userRepository.listUsers(userNameMask);
        }

        return userRepository.listUsers();
    }
}
