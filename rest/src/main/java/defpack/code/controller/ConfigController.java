package defpack.code.controller;
import defpack.code.repository.ConfigRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigController {


    @PostMapping("/userNameRegex")
    public String userNameRegex(@RequestParam(value = "value") String name) {
        ConfigRepository.setUserName(name);
        return "userName " + ConfigRepository.getUserName();
    }

    @PostMapping("/passRegex")
    public String passwordRegex(@RequestParam(value = "value") String pass) {
        ConfigRepository.setPassword(pass);
        return "password " + ConfigRepository.getPassword();
    }
}
