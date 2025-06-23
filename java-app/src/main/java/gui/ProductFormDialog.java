package main.java.gui;

import main.java.model.Category;
import main.java.model.Product;

import javax.swing.*;
import java.util.List;


/**
 * Purpose: Opens a popup window when user clicks on the "Add/Edit" buttons
 * - Validates user input
 * - Returns product data to ProductPanel class
 * - Handles both Add and Edit feature information
 */
public class ProductFormDialog extends JDialog {
    private JTextField name;
    private JTextField sku;
    private JTextField description;
    private JTextField price;
    private JTextField quantity;
    private JComboBox<Category> selection;
    private JButton save;
    private JButton cancel;
    private Product currentProduct;
    private boolean isEditMode;
    private boolean wasSaved;
    private List<Category> categories;

    /**
     * Constructor for Add feature
     * @param parent The parent window
     * @param categories List of available categories for dropdown
     */
    public ProductFormDialog(JFrame parent, List<Category> categories) {
        this(parent, categories, null);
    }

    /**
     * Constructor for Edit feature
     * @param parent The parent window
     * @param categories List of available categories for dropdown
     * @param product The product to edit
     */
    public ProductFormDialog(JFrame parent, List<Category> categories, Product product) {
        super(parent, "title", true);

        this.categories = categories;
        this.currentProduct = product;
        this.isEditMode = (product != null);
        this.wasSaved = false;

        setTitle(isEditMode ? "Edit Product" : "Add Product");

        initializeComponents();
        setupLayout();
        setupEventHandlers();

        if (isEditMode) {
            populateFields();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * All form components
     */
    private void initializeComponents() {
        name = new JTextField(20);
        sku = new JTextField(20);
        description = new JTextField(20);
        price = new JTextField(10);
        quantity = new JTextField(20);
        selection = new JComboBox<>();

        save = new JButton(isEditMode ? "Update" : "Add");
        cancel = new JButton("Cancel");

        selection.addItem(null);
        for (Category category : categories) {
            selection.addItem(category);
        }
    }

    /**
     * Creates a form with labels and inputs
     */
    private void setupLayout() {

    }

    /**
     * Actions that occur when buttons are pressed
     */
    private void setupEventHandlers() {

    }

    /**
     * Form fields when editing products
     */
    private void populateFields() {

    }

    /**
     * Validates form data and creates Product object
     * @return Product if validation is successful, null if it fails to do so
     */
    private Product validateAndSave() {
        return null;
    }

    /**
     * Shows error message to user
     * @param message The error to display to user
     */
    private void showErrorDialog(String message) {

    }

    /**
     * Checks if user saved or cancelled
     * @return true if saved, false otherwise
     */
    private boolean wasSaved() {
        return false;
    }

    /**
     * Method to get Product data
     * @return Product data
     */
    public Product getProduct() {
        return null;
    }
}
