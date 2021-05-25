package defpack.code.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*
    for storage config value
 */
//@Component
public class ConfigRepository {

//    @Value("${userNameRegex}")
    private String userName = ".*";

//    @Value("${passRegex}")
    private String password = ".*";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
