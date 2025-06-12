package main.java.api;

import com.google.gson.Gson;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:5000";
    private Gson gson;

    public ApiClient() {
        gson = new Gson();
    }

    public String sendGetRequest(String endpoint) {
        return null;
    }

    public String sendPostRequest(String endpoint, String jsonbody) {
        return null;
    }

    public String sendPutRequest(String endpoint, String jsonbody) {
        return null;
    }

    public String sendDeleteRequest(String endpoint) {
        return null;
    }

    public static void main(String[] args) {

    }
}
