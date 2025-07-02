package main.java.api;

import com.google.gson.reflect.TypeToken;
import main.java.model.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Purpose: Specific operations for Product feature (CRUD operations)

public class ProductApiClient {
    private final ApiClient apiClient;

    /**
     * Constructor that creates instance of API Client
     */
    public ProductApiClient() {
        this.apiClient = new ApiClient();
    }

    /**
     * Gets all products from the API
     * @return List of all Products
     * @throws Exception if API call fails
     */
    public List<Product> getAllProducts() throws Exception{
        try {
            // 1.Makes the GET request to products endpoint
            String jsonResponse = apiClient.sendGetRequest("/api/products");

            // 2. Define type for JSON
            Type listType = new TypeToken<List<Product>>(){}.getType();

            // 3. Convert JSON array to List<Product>
            List<Product> products = apiClient.fromJson(jsonResponse, (Class<List<Product>>) listType);

            // 4. Must be able to handle errors
            return products != null ? products : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Failed to get all Products: " + e.getMessage());
            throw new Exception("Unable to get products", e);
        }
    }

    /**
     * Gets a specific product from ID
     * @param productId The ID of the product to get
     * @return The product object
     * @throws Exception If API fails
     */
    public Product getProduct(int productId) throws Exception {
        try {
            String jsonResponse = apiClient.sendGetRequest("/api/products/" + productId);
            return apiClient.fromJson(jsonResponse, Product.class);
        } catch (Exception e) {
            System.err.println("Failed to get the product " + productId + ": " + e.getMessage());
            throw new Exception("Unable to get certain product", e);
        }
    }

    /**
     * Creates a Product
     * @param product The product to create, no ID needed
     * @return The created Product
     * @throws Exception if creating the Product fails
     */
    public Product createProduct(Product product) throws Exception {
        try {
            // 1. Converts Product object to JSON
            String jsonBody = apiClient.toJson(product);

            // 2. Send POST request with Product data
            String jsonResponse = apiClient.sendPostRequest("/api/products", jsonBody);

            // 3. Parse response to get the product w ID
            return apiClient.fromJson(jsonResponse, Product.class);
        } catch (Exception e) {
            System.err.println("Failed to create product: " + e.getMessage());
            throw new Exception("Unable to create product", e);
        }
    }

    /**
     * Updates product that exists already
     * @param productId The ID of the product to alter
     * @param product The actual updated product
     * @return The changed product
     * @throws Exception if updating fails
     */
    public Product updateProduct(int productId, Product product) throws Exception {
        try {
            String jsonBody = apiClient.toJson(product);
            String jsonResponse = apiClient.sendPutRequest("/api/products/" + productId, jsonBody);
            return apiClient.fromJson(jsonResponse, Product.class);
        } catch (Exception e) {
            System.err.println("Failed to update product " + productId + ": " + e.getMessage());
            throw new Exception("Unable to update product", e);
        }
    }

    /**
     * Deletes a product
     * @param productId The ID of the product you want to delete
     * @return returns True if the product was deleted, otherwise False
     * @throws Exception if deleting fails
     */
    public boolean deleteProduct(int productId) throws Exception {
        try {
            apiClient.sendDeleteRequest("/api/products/" + productId);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to delete product " + productId + ": " + e.getMessage());
            throw new Exception("Unable to delete product", e);
        }
    }

    /**
     * Searches products by name
     * @param query The search functionality
     * @return List of products that match the query (search)
     * @throws Exception if search fails
     */
    public List<Product> searchProducts(String query) throws Exception {
        try {
            String endpoint = "/api/products?search=" + java.net.URLEncoder.encode(query, "UTF-8");

            String jsonResponse = apiClient.sendGetRequest(endpoint);
            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products = apiClient.fromJson(jsonResponse, (Class<List<Product>>) listType);

            return products != null ? products : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Failed to search products: " + e.getMessage());
            throw new Exception("Unable to search for the products", e);
        }
    }

    /**
     * Gets products by category
     * @param categoryId The ID used to filter categories
     * @return List of products in the specified category
     * @throws Exception if getting fails
     */
    public List<Product> getProductsByCategory(int categoryId) throws Exception {
        try {
            String endpoint = "/api/products?category_id=" + categoryId;
            String jsonResponse = apiClient.sendGetRequest(endpoint);
            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products = apiClient.fromJson(jsonResponse, (Class<List<Product>>) listType);

            return products != null ? products : new ArrayList<>();
        } catch (Exception e) {
        System.err.println("Failed to get products by category: " + e.getMessage());
        throw new Exception("Unable to get products by category", e);
        }
    }

    /**
     * Gets uncategorized products
     * @return List of products without a category (lost ones)
     * @throws Exception if API call fails
     */
    public List<Product> getUncategorizedProducts() throws Exception {
        try {
            String jsonResponse = apiClient.sendGetRequest("/api/products/uncategorized");
            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products = apiClient.fromJson(jsonResponse, (Class<List<Product>>) listType);

            return products != null ? products : new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Failed to get uncategorized products: " + e.getMessage());
            throw new Exception("Unable to get uncategorized products", e);
        }
    }



    public static void main(String[] args) {

    }
}
