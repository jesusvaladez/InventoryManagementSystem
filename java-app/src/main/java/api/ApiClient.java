package main.java.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

// Purpose: Base HTTP client for communication with the Flask API.
//          Provides foundation for all API operations

public class ApiClient {

    // Where the Flask API is running
    private static final String BASE_URL = "http://localhost:5000";
    private static final int TIMEOUT = 10000;
    private Gson gson;

    public ApiClient() {
        gson = new Gson();
    }


    /**
     * Sends a GET request to a specified endpoint
     * @param endpoint The following API endpoint(ex: "/api/products")
     * @return JSON response as a string
     */
    public String sendGetRequest(String endpoint) throws Exception {
        return sendRequest("GET", endpoint, null);
    }

    /**
     * Sends a POST request with JSON
     * @param endpoint The API endpoint
     * @param jsonbody JSON data to send over
     * @return JSON response as a string
     */
    public String sendPostRequest(String endpoint, String jsonbody) throws Exception {
        return sendRequest("POST", endpoint, jsonbody);
    }

    /**
     * Sends a PUT request with JSON
     * @param endpoint The API endpoint
     * @param jsonbody JSON to send in request body
     * @return JSON response as a string
     */
    public String sendPutRequest(String endpoint, String jsonbody) throws Exception {
        return sendRequest("PUT", endpoint, jsonbody);
    }

    /**
     * Sends a DELETE request
     * @param endpoint The API endpoint
     * @return JSON response
     */
    public String sendDeleteRequest(String endpoint) throws Exception {
        return sendRequest("DELETE", endpoint, null);
    }

    /**
     * The method that handles all the HTTP requests and allows communication
     * @param method HTTP method handles -> GET, POST, PUT, DELETE
     * @param endpoint API endpoint
     * @param jsonBody JSON body for POST/PUT requests and null for GET/DELETE
     * @return Response body as a string
     * @throws Exception
     */
    private String sendRequest(String method, String endpoint, String jsonBody) throws Exception {
        HttpURLConnection connection = null;

        try {
            // 1, Create the entire URL by connecting base URL and endpoint
            URL url = new URL(BASE_URL + endpoint);

            // 2. Open HTTP connection
            connection = (HttpURLConnection) url.openConnection();

            // 3. Configure the connections
            connection.setRequestMethod(method);
            connection.setConnectTimeout(TIMEOUT); // Time allowed for it to connect
            connection.setReadTimeout(TIMEOUT); // Time allowed for it to read response

            // 4. Headers for JSON communication
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // 5. If a json body exists then send it off
            if (jsonBody != null && !jsonBody.trim().isEmpty()) {
                connection.setDoOutput(true); // Allow output stream

                // Write the JSON data to the request body
                try (OutputStream outputStream = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            // 6. Get the HTTP response info
            int responseCode = connection.getResponseCode();

            // 7. Read the response info
            String responseBody;
            if (responseCode >= 200 && responseCode < 300) {
                // Successful response
                responseBody = readInputStream(connection.getInputStream());
            } else {
                // Error response
                responseBody = readInputStream(connection.getErrorStream());
                throw new RuntimeException("HTTP" + responseCode + ": " + responseBody);
            }
            return responseBody;
        } catch (IOException e) {
            System.err.println("API requesst failed: " + method + " " + endpoint);
            System.err.println("Error: " + e.getMessage());
            throw e;
        } finally {
            // 8. Close connection
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Helper method for reading data (converts the byte to String)
     * @param inputStream The input to read from
     * @return The content as a string
     * @throws IOException Occurs if reading goes wrong
     */
    private String readInputStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        StringBuilder response = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                                        (inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    /**
     * Converts a java object to JSON string
     * @param object The java object to convert
     * @return JSON string of the object
     */
    public String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            System.err.println("Failed to convert java object to JSON: " + e.getMessage());
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    public <T> T fromJson(String json, Class<T>classType) {
        try {
            return gson.fromJson(json, classType);
        } catch (JsonSyntaxException e) {
            System.err.println("Failed to parse JSON: " + json);
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    /**
     * Gets the base URL
     */
    public String getBaseUrl() {
        return BASE_URL;
    }

    public static void main(String[] args) {

    }
}
