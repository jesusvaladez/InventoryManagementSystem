package main.java.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private String sku;
    private double price;
    private int quantity;
    private Integer categoryId;
    private String categoryName;
    private String createdAt;

    public Product() {
    }

    // Constructor
    public Product(String name, String description, String sku, double price
                    , int quantity, Integer categoryID, String categoryName, String createdAt) {
         this.name = name;
         this.description = description;
         this.sku = sku;
         this.price = price;
         this.quantity = quantity;
         this.categoryId = categoryID;
         this.categoryName = categoryName;
         this.createdAt = createdAt;
    }

    // Get methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // Setter methods
    public void setId(int new_id) {
        this.id = new_id;
    }

    public void setName(String new_name) {
        this.name = new_name;
    }

    public void setDescription(String new_description) {
        this.description = new_description;
    }

    public void setSku(String new_sku) {
        this.sku = new_sku;
    }

    public void setPrice(double new_price) {
        if (new_price >= 0.0) {
            this.price = new_price;
        }
    }

    public void setQuantity(int new_quantity) {
        this.quantity = new_quantity;
    }

    public void setCategoryID(Integer new_Category_Id) {
        this.categoryId = new_Category_Id;
    }

    public void setCategoryName(String new_Category_Name) {
        this.categoryName =new_Category_Name;
    }

    public void setCreatedAt(String new_created_at) {
        this.createdAt = new_created_at;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + " , SKU: " + sku
                + ", Price: " + price + ", Quantity: " + quantity + ", Category ID: " + categoryId
                + ", Category Name: " + categoryName + ", Created At: " + createdAt;
    }

    public static void main(String[] args) {

    }
}
