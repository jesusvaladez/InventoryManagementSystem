package main.java.api;


import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import main.java.model.Category;
import main.java.model.Product;

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
            System.err.println("Failed to get all Categories: " + e.getMessage());
            throw new Exception("Unable to get Categories", e);
        }
    }

    /**
     * Gets certain Category data
     * @param categoryId The id of the category to get
     * @return The category
     * @throws Exception if all fails
     */
    public Category getCategory(int categoryId) throws Exception {
        try {
            String jsonResponse = apiClient.sendGetRequest("/api/categories/" + categoryId);
            return apiClient.fromJson(jsonResponse, Category.class);
        } catch (Exception e) {
            System.err.println("Unable to get Category " + categoryId);
            throw new Exception("Not able to get Category", e);
        }
    }

    /**
     * Creates a Category
     * @param category The category to make
     * @return The created category
     * @throws Exception if all fails
     */
    public Category createACategory(Category category) throws Exception {
        try {
            String jsonBody = apiClient.toJson(category);
            String jsonResponse = apiClient.sendPostRequest("/api/categories", jsonBody);
            return apiClient.fromJson(jsonResponse, Category.class);
        } catch (Exception e) {
            System.err.println("Failed to create Category" + e.getMessage());
            throw new Exception("Unable to create Category", e);
        }
    }

    /**
     * Updates an existing Category
     * @param CategoryId The Category we want to edit
     * @return The edited Category
     * @throws Exception If updating fails
     */
    public Category updateCategory(int CategoryId, Category category) throws Exception {
        try {
            String jsonBody = apiClient.toJson(category);
            String jsonResponse = apiClient.sendPutRequest("/api/categories/" + CategoryId, jsonBody);
            return apiClient.fromJson(jsonResponse, Category.class);
        } catch (Exception e) {
            System.err.println("Failed to update Category " + CategoryId);
            throw new Exception("Unable to update Category", e);
        }
    }

    /**
     * Deletes a Category
     * @param categoryId The certain Category we want deleted
     * @return true if deletion of Category is successful
     * @throws Exception If deletion fails
     */
    public boolean deleteCategory(int categoryId) throws Exception {
        try {
            apiClient.sendDeleteRequest("/api/categories/" + categoryId);
            return true;
        } catch (Exception e) {
            System.err.println("Deletion of Category has failed");
            throw new Exception("Unable to delete Category", e);
        }
    }

    public List<Category> searchCategories(String searchQuery) throws Exception {
        try {
            String endpoint = "/api/categories?search=" + java.net.URLEncoder.encode(searchQuery, "UTF-8");
            Type listType = new TypeToken<List<Category>>(){}.getType();
            String jsonResponse = apiClient.sendGetRequest(endpoint);
            List<Category> categories = apiClient.fromJson(jsonResponse, (Class<List<Category>>) listType);

            return categories != null ? categories : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Failed to search for Category: " + e.getMessage());
            throw new Exception("Unable to search for the Category", e);
        }
    }

    /**
     * Gets Products in a Category
     * @param categoryId The Category ID
     * @return List of Products in the Category
     * @throws Exception If retrieval fails
     */
    public List<Product> getCategoryProducts(int categoryId) throws Exception {
        try {
            String endpoint = "/api/categories/" + categoryId + "/products";
            String jsonResponse = apiClient.sendGetRequest(endpoint);

            // For parsing
            JsonObject responseObj = apiClient.fromJson(jsonResponse, JsonObject.class);
            String productsJson = responseObj.get("products").toString();

            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products = apiClient.fromJson(productsJson, (Class<List<Product>>) listType);

            return products != null ? products : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Failed to find Products in Category " + categoryId + ": " + e.getMessage());
            throw new Exception("Unable to retrieve category products", e);
        }
    }
}
