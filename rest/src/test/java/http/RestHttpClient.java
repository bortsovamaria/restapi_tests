package http;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jetbrains.annotations.Nullable;

public class RestHttpClient {

    private RequestSpecification client = new RestAssured().given();
    private STResponse stResponse = new STResponse();

    public STResponse runGetRequest(String url) {
        return runRequest(url, Method.GET, null);
    }

    public STResponse runPostRequest(String url, String payload) {
        return runRequest(url, Method.POST, payload);
    }


    public STResponse runRequest(String url, Method method, @Nullable String payload) {

        if (payload != null) {
            client.body(payload);
        }

        client.contentType("application/json");
        client.header("Accept", "application/json");
        client.header("User-Agent", "Mozilla/5.0");
        client.header("Connection-Timeout", 5000);
        client.header("Read-Timeout", 5000);
        client.header("doOutput", true);

        Response response = client.request(method.toString(), url);

        stResponse.setResponseCode(response.getStatusCode());
        stResponse.setResponseBody(response.body().prettyPrint());

        return stResponse;
    }
}
