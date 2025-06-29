package main.java.api;


import com.google.gson.reflect.TypeToken;
import main.java.model.Category;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: API client for Category feature
 * Fields -> id, name, description, createdAt
 */
public class CategoryApiClient {
    private ApiClient apiClient;

    public CategoryApiClient() {
        apiClient = new ApiClient();
    }

    /**
     * Gets categories from API
     * @return List of categories
     * @throws Exception If API call fails
     */
    public List<Category> getAllCategories() throws Exception{
        try {
            String jsonResponse = apiClient.sendGetRequest("/api/categories");
            Type listType = new TypeToken<List<Category>>(){}.getType();
            List<Category> categories = apiClient.fromJson(jsonResponse, (Class<List<Category>>) listType);
            return categories != null ? categories : new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Unable to get categories");
        }
    }


    public static void main(String[] args) {

    }
}
