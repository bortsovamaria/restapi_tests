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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import steps.Steps;

import static org.mockito.Mockito.when;

@Epic("Login Tests Epic")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Tests {

    @Mock
    MailService mailService;

    RestHttpClient httpClient = new RestHttpClient();

    Steps steps = new Steps();

    @Test
    @Story("*")
    @Description("Update password (positive)")
    public void postUpdatePassword() {

        // 1
        String json = "{ \"name\" : \"bonbon\", \"password\" : \"1234\" }";
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        String jsonSecond = "{ \"name\" : \"bonbon\", \"oldPassword\" : \"1234\", \"password\" : \"666\" }";
        STResponse stResponseSecond = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //3
        User user = new Gson().fromJson(stResponseSecond.getResponseBody(), User.class);
        Assertions.assertEquals(user.getPassword(), "666", "Password not match");
    }

    @Test
    @Story("*")
    @Description("Update password (negative)")
    public void postUserFailPassword() {

        String json = "{ \"name\" : \"fail2\", \"password\" : \" \" }";
        steps.postUser(json);

    }

    @Test
    @Story("*")
    @Description("Add user with same name (negative)")
    public void postUserFailName() {

        //1
        String json = "{ \"name\" : \"samename\", \"password\" : \"123\" }";
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        String jsonSecond = "{ \"name\" : \"samename\", \"password\" : \"123\" }";
        STResponse stResponseSecond = steps.postUser(jsonSecond);
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 500);

    }

    @Test
    @Story("*")
    @Description("Update password when user not exists (negative)")
    public void postUpdatePasswordFailName() {

        //1
        String json = "{ \"name\" : \"update\", \"password\" : \"1234\" }";
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        String jsonSecond = "{ \"name\" : \"updatee\", \"oldPassword\" : \"1234\", \"password\" : \"12345\" }";
        STResponse stResponseSecond = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 500);
    }

    @Test
    @Story("*")
    @Description("Update password with same password (negative)")
    public void postUpdatePasswordFailPassword() {

        //1
        String json = "{ \"name\" : \"password\",  \"password\" : \"1234\" }";
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        String jsonSecond = "{ \"name\" : \"password\", \"oldPassword\" : \"1234\", \"password\" : \"1234\" }";
        STResponse stResponseSecond = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 500);

    }

    @Test
    @Story("*")
    @Description("Add user and update password (positive)")
    public void testOne() {

        //1
        String json = "{ \"name\" : \"newUser\", \"password\" : \"12345\" }";
        STResponse stResponse = steps.postUser(json);
        Assertions.assertEquals(stResponse.getResponseCode(), 200);

        //2
        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");

        //3
        String jsonSecond = "{ \"name\" : \"newUser\", \"oldPassword\" : \"12345\", \"password\" : \"666666\" }";
        STResponse stResponseSecond = steps.updatePassword(jsonSecond);
        Assertions.assertEquals(stResponseSecond.getResponseCode(), 200);

        //4
        when(mailService.sendMailUpdatePass()).thenReturn("Send mail: update password");
    }

    @Test
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
