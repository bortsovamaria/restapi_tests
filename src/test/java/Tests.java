import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import defpack.Application;
import defpack.code.entity.User;
import defpack.code.service.MailService;
import http.RestHttpClient;
import http.STResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import steps.Steps;

import static org.mockito.Mockito.when;

@Epic("Login Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Tests {

    @Mock
    MailService mailService;

    RestHttpClient httpClient = new RestHttpClient();

    Steps steps = new Steps();



    @Test
    @Story("*")
    @Description("Add user (positive)")
    public void addUser() {

        // 1
//        String json = "{ \"name\" : \"user4\", \"password\" : \"1234\" }";

        String userName = RandomStringUtils.randomAlphanumeric(5);
        String password = RandomStringUtils.randomAlphanumeric(10);
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(newUser);
//        System.out.println(json);

        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        STResponse stResponseSecond = steps.getUsers();
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //3
        boolean name = false;
        boolean pass = false;
        Gson gsonSecond = new Gson();
        User[] userArray = gsonSecond.fromJson(stResponseSecond.getResponseBody(), User[].class);

        for(User user : userArray) {
            name = user.getName().equals(userName);
            pass = user.getPassword().equals(password);
            if (name && pass) break;
        }
        Assertions.assertTrue(name && pass, "User didn't add");
    }

    @Test
    @Story("*")
    @Description("Add user and update password (positive)")
    public void postUpdatePassword() {

        // 1
//        String json = "{ \"name\" : \"bonbon\", \"password\" : \"1234\" }";
        String userName = RandomStringUtils.randomAlphanumeric(5);
        String password = RandomStringUtils.randomAlphanumeric(10);
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(newUser);
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");

        //3
        STResponse stResponseSecond = steps.getUsers();
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //4
        boolean name = false;
        boolean pass = false;
        Gson gsonSecond = new Gson();
        User[] userArray = gsonSecond.fromJson(stResponseSecond.getResponseBody(), User[].class);

        for(User user : userArray) {
            name = user.getName().equals(userName);
            pass = user.getPassword().equals(password);
            if (name && pass) break;
        }
        Assertions.assertTrue(name && pass, "User didn't add");

        //5
//        String jsonSecond = "{ \"name\" : \"bonbon\", \"oldPassword\" : \"1234\", \"password\" : \"666\" }";
        User newUserSecond = new User();
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        newUserSecond.setName(userName);
        newUserSecond.setPassword(newPassword);
        newUserSecond.setOldPassword(password);

        Gson gsonThird = new Gson();
        String jsonSecond = gsonThird.toJson(newUserSecond);
//        System.out.println(jsonSecond);
        STResponse stResponseThird = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseThird.getResponseCode(), 200);

        //6
        when(mailService.sendMailUpdatePass()).thenReturn("Send mail: update password");

        //7
        User user = new Gson().fromJson(stResponseThird.getResponseBody(), User.class);
        Assertions.assertEquals(user.getPassword(), newPassword, "Password not match");
    }


    @Test
    @Story("*")
    @Description("Update password (negative)")
    public void postUserFailPassword() {

        //1
//        String json = "{ \"name\" : \"fail2\", \"password\" : \"\" }";
        String userName = RandomStringUtils.randomAlphanumeric(5);
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword("");

        Gson gson = new Gson();
        String json = gson.toJson(newUser);
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 500);

    }

    @Test
    @Story("*")
    @Description("Add user with same name (negative)")
    public void postUserFailName() {

        //1
//        String json = "{ \"name\" : \"samename\", \"password\" : \"123\" }";
        String userName = RandomStringUtils.randomAlphanumeric(5);
        String password = RandomStringUtils.randomAlphanumeric(10);
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(newUser);
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");

        //3
        STResponse stResponseSecond = steps.getUsers();
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //4
        boolean name = false;
        boolean pass = false;
        Gson gsonSecond = new Gson();
        User[] userArray = gsonSecond.fromJson(stResponseSecond.getResponseBody(), User[].class);

        for(User user : userArray) {
            name = user.getName().equals(userName);
            pass = user.getPassword().equals(password);
            if (name && pass) break;
        }
        Assertions.assertTrue(name && pass, "User didn't add");

        //5
//        String jsonSecond = "{ \"name\" : \"samename\", \"password\" : \"123\" }";
        User newUserSecond = new User();
        newUserSecond.setName(userName);
        newUserSecond.setPassword(password);

        Gson gsonThird = new Gson();
        String jsonSecond = gsonThird.toJson(newUserSecond);
        STResponse stResponseThird = steps.postUser(jsonSecond);
        Assertions.assertEquals(stResponseThird.getResponseCode(), 500, "Add user with same name");

    }


    @Test
    @Story("*")
    @Description("Update password when user not exists (negative)")
    public void postUpdatePasswordFailName() {

        //1
//        String json = "{ \"name\" : \"update\", \"password\" : \"1234\" }";
        String userName = RandomStringUtils.randomAlphanumeric(5);
        String password = RandomStringUtils.randomAlphanumeric(10);
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(newUser);
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");

        //3
        STResponse stResponseSecond = steps.getUsers();
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //4
        boolean name = false;
        boolean pass = false;
        Gson gsonSecond = new Gson();
        User[] userArray = gsonSecond.fromJson(stResponseSecond.getResponseBody(), User[].class);

        for(User user : userArray) {
            name = user.getName().equals(userName);
            pass = user.getPassword().equals(password);
            if (name && pass) break;
        }
        Assertions.assertTrue(name && pass, "User didn't add");

        //2
//        String jsonSecond = "{ \"name\" : \"updatee\", \"oldPassword\" : \"1234\", \"password\" : \"12345\" }";
        User newUserSecond = new User();
        newUserSecond.setName(RandomStringUtils.randomAlphanumeric(5));
        newUserSecond.setPassword(RandomStringUtils.randomAlphanumeric(5));
        newUserSecond.setOldPassword(RandomStringUtils.randomAlphanumeric(5));

        Gson gsonThird = new Gson();
        String jsonSecond = gsonThird.toJson(newUser);
        STResponse stResponseThird = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseThird.getResponseCode(), 500, "Update password with not exists user");
    }

    @Test
    @Story("*")
    @Description("Update password with same password (negative)")
    public void postUpdatePasswordFailPassword() {

        //1
//        String json = "{ \"name\" : \"password\",  \"password\" : \"1234\" }";
        String userName = RandomStringUtils.randomAlphanumeric(5);
        String password = RandomStringUtils.randomAlphanumeric(10);
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(newUser);
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");

        //3
        STResponse stResponseSecond = steps.getUsers();
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //4
        boolean name = false;
        boolean pass = false;
        Gson gsonSecond = new Gson();
        User[] userArray = gsonSecond.fromJson(stResponseSecond.getResponseBody(), User[].class);

        for(User user : userArray) {
            name = user.getName().equals(userName);
            pass = user.getPassword().equals(password);
            if (name && pass) break;
        }
        Assertions.assertTrue(name && pass, "User didn't add");

        //2
//        String jsonSecond = "{ \"name\" : \"password\", \"oldPassword\" : \"1234\", \"password\" : \"1234\" }";
        User newUserSecond = new User();
        newUserSecond.setName(userName);
        newUserSecond.setPassword(password);
        newUserSecond.setOldPassword(password);

        Gson gsonThird = new Gson();
        String jsonSecond = gsonThird.toJson(newUserSecond);
//        System.out.println(jsonSecond);
        STResponse stResponseThird = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseThird.getResponseCode(), 500, "Update old pass on same new pass. why?");

    }

    /*
     for error allure
     */
    @Test
    @Story("*")
    @Description("Fail")
    public void testFail() {
        //1
        String json = "{ \"name\" : \"fail\", \"password\" : \"fail\" }";
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);
        //1
        String jsonSecond = "{ \"name\" : \"fail\", \"password\" : \"fail\" }";
        STResponse stResponseSecond = steps.postUser(jsonSecond);
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);
    }



    /*
     configuration regex
     */
    @Test
    @Story("*")
    @Description("Check config name regex")
    public void postNameRegex() {

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/api/config/userNameRegex?value=(.*[0-9])", null);

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);
    }

    @Test
    @Story("*")
    @Description("Check config name regex")
    public void postPassRegex() {

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/api/config/passRegex?value=(.*[0-9])", null);

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);
    }
}
