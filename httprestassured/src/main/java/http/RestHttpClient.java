//package http;
//
//import com.sun.istack.Nullable;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.springframework.http.HttpMethod;
//
//public class RestHttpClient {
//
//    private RequestSpecification client = new RestAssured().given();
//    private STResponse stResponse = new STResponse();
//
//    public STResponse runGetRequest(String url) {
//        return runRequest(url, HttpMethod.GET, null);
//    }
//
//    public STResponse runPostRequest(String url, String payload) {
//        return runRequest(url, HttpMethod.POST, payload);
//    }
//
//
//    public STResponse runRequest(String url, HttpMethod method, @Nullable String payload) {
//        if (payload != null) {
//            client.body(payload);
//        }
//
//        Response response = client.request(method.toString(), url);
////        stResponse.setResponseCode(response.getStatusCode());
////        stResponse.setResponseBody(response.body().prettyPrint());
//
//        return stResponse;
//    }
//}
