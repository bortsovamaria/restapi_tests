package integration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import defpack.Application;
import http.RestHttpClient;
import http.STResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//import org.json.JSONObject;
//import org.springframework.boot.test.context.SpringBootTest;;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Tests {

//    WebRequester webRequester = new WebRequester();

    RestHttpClient httpClient = new RestHttpClient();

//    @Test
    @Test
    public void getUsers() {

//        STResponse stResponse = webRequester.httpClient.runGetRequest("localhost:8080/users");

        STResponse stResponse = httpClient.runGetRequest("http://localhost:8080/users");


        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

//        Assert.assertEquals(stResponse.getResponseBody());
    }

    @Test
    public void postUser() {

//        [{"name":"root","password":"root"},{"name":"user","password":"1234"},{"name":"user2","password":"password"}]

        String json = "{ \"name\" : \"12345\", \"password\" : \"1234\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

    }

    @Test
    public void postUpdatePassword() {

        String json = "{ \"name\" : \"bonbon\", \"oldPassword\" : \"1234\", \"password\" : \"666\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

//        User user = new User("user3", "1234");

//        String json = new ObjectMapper().writeValueAsString(user);

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/updatePassword", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());
    }

    @Test
    public void postUserFailPassword() {

        String json = "{ \"name\" : \"fail2\", \"password\" : \" \" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

    }

    @Test
    public void postUserFailName() {

        String json = "{ \"name\" : \"root\", \"password\" : \"123\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        STResponse stResponse = httpClient.runPostRequest("http://localhost:8080/user", jsonObject.toString());

        System.out.println("OUT:");
        System.out.println(stResponse.getResponseCode());
        System.out.println(stResponse.getResponseBody());

    }

    @Test
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

    }

    @Test
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

    }
}
