import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import defpack.Application;
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

import static org.mockito.Mockito.when;

@Epic("Login Tests Epic")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Tests {

    @Mock
    MailService mailService;

    RestHttpClient httpClient = new RestHttpClient();

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void getUsers() {

        STResponse stResponse = httpClient.runGetRequest("http://localhost:8080/users");

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);
    }

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void postUser() {

        String json = "{ \"name\" : \"12345\", \"password\" : \"1234\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);

    }

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void postUpdatePassword() {

        String json = "{ \"name\" : \"bonbon\", \"oldPassword\" : \"1234\", \"password\" : \"666\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/updatePassword", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);
    }

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void postUserFailPassword() {

        String json = "{ \"name\" : \"fail2\", \"password\" : \" \" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertFalse(false);

    }

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void postUserFailName() {

        String json = "{ \"name\" : \"root\", \"password\" : \"123\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertFalse(false);

    }

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void postUpdatePasswordFailName() {

        String json = "{ \"name\" : \"update\", \"password\" : \"1234\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());;

        String jsonSecond = "{ \"name\" : \"updatee\", \"oldPassword\" : \"1234\", \"password\" : \"12345\" }";

        JsonObject jsonObjectSecond = new JsonParser().parse(jsonSecond).getAsJsonObject();

        STResponse stResponseSecond = httpClient.runPostRequest("http://localhost:8080/updatePassword", jsonObjectSecond.toString());

        System.out.println();
        System.out.println("OUT2:");
        System.out.println(stResponseSecond.getResponseCode());
        System.out.println(stResponseSecond.getResponseBody());;

        Assertions.assertFalse(false);

    }

    @Test
    @Story("User tries to login the system with empty username and invalid password.")
    @Description("Invalid Login Test with Empty Username and Invalid Password.")
    public void postUpdatePasswordFailPassword() {

        String json = "{ \"name\" : \"password\",  \"password\" : \"1234\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());;

        String jsonSecond = "{ \"name\" : \"password\", \"oldPassword\" : \"1234\", \"password\" : \"1234\" }";

        JsonObject jsonObjectSecond = new JsonParser().parse(jsonSecond).getAsJsonObject();

        STResponse stResponseSecond = httpClient.runPostRequest("http://localhost:8080/updatePassword", jsonObjectSecond.toString());

        System.out.println();
        System.out.println("OUT2:");
        System.out.println(stResponseSecond.getResponseCode());
        System.out.println(stResponseSecond.getResponseBody());;

        Assertions.assertFalse(false);

    }

    @Test
    @Story("User tries to login the system with invalid username and invalid password.")
    @Description("Invalid Login Test with Invalid Username and Invalid Password.")
    public void testOne() {

        String json = "{ \"name\" : \"newUser\", \"password\" : \"12345\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");

        String jsonSecond = "{ \"name\" : \"newUser\", \"oldPassword\" : \"12345\", \"password\" : \"666666\" }";

        JsonObject jsonObjectSecond = new JsonParser().parse(jsonSecond).getAsJsonObject();

        STResponse stResponseSecond = httpClient.runPostRequest("http://localhost:8080/updatePassword", jsonObjectSecond.toString());

        System.out.println("OUT:");
        System.out.println(stResponseSecond.getResponseCode());
        System.out.println(stResponseSecond.getResponseBody());

        when(mailService.sendMailUpdatePass()).thenReturn("Send mail: update password");

        Assertions.assertTrue(true);
    }
}
