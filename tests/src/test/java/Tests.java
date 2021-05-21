import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import entity.User;
import http.RestHttpClient;
import http.STResponse;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import org.json.JSONObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;;


//@SpringBootTest
public class Tests {

//    WebRequester webRequester = new WebRequester();

    RestHttpClient httpClient = new RestHttpClient();

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

        String json = "{ \"name\" : \"bonbon\", \"password\" : \"1234\" }";

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

//        User user = new User("user3", "1234");

//        String json = new ObjectMapper().writeValueAsString(user);

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
}
