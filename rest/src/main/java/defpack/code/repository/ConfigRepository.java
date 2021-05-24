package defpack.code.repository;

import org.springframework.beans.factory.annotation.Value;

public class ConfigRepository {

    @Value("${userNameRegex}")
    private static String userName;

    @Value("${passRegex}")
    private static String password;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        ConfigRepository.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConfigRepository.password = password;
    }
}
