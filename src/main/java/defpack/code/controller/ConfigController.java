package defpack.code.controller;
import defpack.code.config.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
    controller for config api methods
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    ConfigRepository configRepository = new ConfigRepository();

//    @Autowired
//    ConfigRepository configRepository;

    @PostMapping("/userNameRegex")
    public String userNameRegex(@RequestParam(value = "value") String name) {
        configRepository.setUserName(name);
        return "userName " + configRepository.getUserName();
    }

    @PostMapping("/passRegex")
    public String passwordRegex(@RequestParam(value = "value") String pass) {
        configRepository.setPassword(pass);
        return "password " + configRepository.getPassword();
    }
}
