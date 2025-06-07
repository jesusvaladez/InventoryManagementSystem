package main.java.model;

public class Category {
    private int id;
    private String name;
    private String description;
    private String createdAt;

    public Category(int id, String name, String description, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public void setCreatedAt(String new_created_at) {
        this.createdAt = new_created_at;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description
                + ", Created At: " + createdAt;
    }

    public static void main(String[] args) {

    }
}
