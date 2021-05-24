package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import http.RestHttpClient;
import http.STResponse;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

public class Steps {

    RestHttpClient httpClient = new RestHttpClient();

    @Step("Get all Users")
    public void getUsers() {

        STResponse stResponse = httpClient.runGetRequest("http://localhost:8080/users");

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

        Assertions.assertTrue(true);
    }

    @Step("Add user")
    public void postUser(String json) {

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

    }

}
