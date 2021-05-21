package code.controller;

import code.entity.User;
//import code.entity.Validator;
import code.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    UserRepository userRepository = new UserRepository();

//    @Autowired
//    private UserService userService;

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";

    @GetMapping
    public Response showStatus() {
        return new Response(SUCCESS_STATUS, 1);
    }


//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getUsers() {
//        List<User> users = this.userService.getAll();
//
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

//    @PostMapping("/user")
//    public Response postUser(@RequestBody User user) {
//        HttpHeaders headers = new HttpHeaders();
//
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        this.userService.save(user);
//        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
//    }

//    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response postUser(@RequestBody )

//    @PostMapping("/updatePassword")
//    public ResponseEntity<User> postUpdatePassword(@RequestBody User user) {
//        HttpHeaders headers = new HttpHeaders();
//
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        this.userService.save(user);
//
//        return new ResponseEntity<>(user, headers, HttpStatus.OK);
//    }

    @GetMapping("/user")
    public User user(@RequestParam(value = "name") String name) {
        System.out.println("GET");
        return userRepository.userByName(name);
    }


//    public User userUpdate(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password) {

    @PostMapping(value = "/user",
                consumes="application/json",
                produces="application/json") //consumes = "text/plain", produces = MediaType.APPLICATION_JSON_VALUE
    @ResponseBody
    public User addUser(@RequestBody User user) {
        System.out.println("POST");

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        userRepository.addUser(newUser);

        return newUser;
    }

    @PostMapping(value = "/updatePassword",
                consumes = "application/json",
                produces = "application/json")
    @ResponseBody
    public User passwordUpdate(@RequestBody User user) {

        User newUser = new User();

        if (userRepository.userByName(user.getName()) != null) {

            if (!user.getOldPassword().equals(user.getPassword())) {
                newUser.setName(user.getName());
                newUser.setPassword(user.getPassword());
            }

        }

        userRepository.addUser(newUser);

        return newUser;
    }

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.listUsers();
    }

}
