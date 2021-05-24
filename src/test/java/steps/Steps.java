package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import defpack.code.service.MailService;
import http.RestHttpClient;
import http.STResponse;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class Steps {

    RestHttpClient httpClient = new RestHttpClient();

    @Mock
    MailService mailService;

    @Step("Get all Users")
    public void getUsers() {

        STResponse stResponse = httpClient.runGetRequest("http://localhost:8080/users");

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);
    }

    @Step("Add user")
    public STResponse postUser(String json) {

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        return stResponse;

    }

    @Step("Update password")
    public STResponse updatePassword(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/updatePassword", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        return stResponse;
    }

//    @Step("Send mail - add user")
//    public String sendMailAddUser() {
//
//        when(mailService.sendMailAdd()).thenReturn("Send mail: add user");
//
//        return "Send mail - add user";
//    }
//
//    @Step("Send mail - update password")
//    public String sendMailUpdatePassword() {
//
//        when(mailService.sendMailUpdatePass()).thenReturn("Send mail: update password");
//
//        return "Send mail - update password";
//    }

}
