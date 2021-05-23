package defpack.code.controller;

import code.entity.User;
import code.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    UserRepository userRepository = new UserRepository();

//    @Autowired
//    private UserService userService;

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";

    @GetMapping()
    public Response showStatus() {
        return new Response(HttpStatus.CONTINUE, 100);
    }


//    @GetMapping("/error")
//    public Response showStatusError() {
//        return new Response(HttpStatus.INTERNAL_SERVER_ERROR, 500);
//    } // 500

    @GetMapping(value = "/ok")
    public Response showStatusOk() {
        logger.info("test");
        return new Response(HttpStatus.OK, 200);
    } // 200

    @GetMapping("/user")
    public User user(@RequestParam(value = "name") String name) {
        System.out.println("GET");
        return userRepository.userByName(name);
    }

    @PostMapping(value = "/user",
                consumes="application/json",
                produces="application/json") //consumes = "text/plain", produces = MediaType.APPLICATION_JSON_VALUE
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) {

        User newUser = new User();

        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (userRepository.userByName(user.getName()) != null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        userRepository.addUser(newUser);

        return new ResponseEntity<>(newUser, new HttpHeaders(), HttpStatus.OK);
    }

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

    @GetMapping("/users")
    public List<User> users(@RequestParam(value = "userNameMask", required = false) String userNameMask) {

        if (userNameMask != null) {
            return userRepository.listUsers(userNameMask);
        }

        return userRepository.listUsers();
    }
}
