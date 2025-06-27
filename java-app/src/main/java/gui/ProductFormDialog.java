package main.java.gui;

import main.java.model.Category;
import main.java.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Name:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;  // Field expands to fill space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(name, gbc);

        // Row 1: SKU field
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("SKU:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(sku, gbc);

        // Row 2: Description field
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(description, gbc);

        // Row 3: Price field
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(price, gbc);

        // Row 4: Quantity field
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(quantity, gbc);

        // Row 5: Category dropdown
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(selection, gbc);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();

        // Add panels to main dialog (structured properly for now)
        add(formPanel, BorderLayout.CENTER);   // Form in center
        add(buttonPanel, BorderLayout.SOUTH);  // Buttons at bottom
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        panel.add(save);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(cancel);

        return panel;
    }


    /**
     * Actions that occur when buttons are pressed
     */
    private void setupEventHandlers() {
        // Save button functionality
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFunctionality();
            }
        });

        // Cancel button functionality
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelFunctionality();
            }
        });

        getRootPane().setDefaultButton(save);
        setupKeyboardCuts();
    }

    /**
     * Method to set up keyboard shortcuts
     */
    private void setupKeyboardCuts() {
        // Esc key button cancels dialog
        KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelFunctionality();
            }
        });
    }

    /**
     * Save function
     */
    private void saveFunctionality() {
        Product validatedProduct = validateAndSave();

        if(validatedProduct != null) {
            // Validation goes through
            this.currentProduct = validatedProduct;
            this.wasSaved = true;
            dispose();
        }
    }

    /**
     * Cancel function
     */
    private void cancelFunctionality() {
        this.wasSaved = false;
        dispose();
    }

    /**
     * Form fields when editing products
     */
    private void populateFields() {
        if (currentProduct != null) {
            return;
        }

        name.setText(currentProduct.getName() != null ? currentProduct.getName() : "");
        sku.setText(currentProduct.getSku() != null ? currentProduct.getSku() : "");
        description.setText(currentProduct.getDescription() != null ? currentProduct.getDescription() : "");
        price.setText(String.valueOf(currentProduct.getPrice()));
        quantity.setText(String.valueOf(currentProduct.getQuantity()));

        setCategorySelection();
    }

    private void setCategorySelection() {
        Integer productCategoryID = currentProduct.getCategoryId();

        if (productCategoryID == null) {
            selection.setSelectedItem(null);
            return;
        }

        // Ensures the category is matched
        for (int i = 0; i < selection.getItemCount(); i++) {
            Category category = selection.getItemAt(i);

            if (category != null && category.getId() == productCategoryID.intValue()) {
                selection.setSelectedItem(category);
                return;
            }
        }
        // Failure to find category if this occurs
        selection.setSelectedItem(null);
    }

    /**
     * Validates form data and creates Product object
     * @return Product if validation is successful, null if it fails to do so
     */
    private Product validateAndSave() {
        // 1. Get values from input
        String nameVal = name.getText().trim();
        String skuVal = sku.getText().trim();
        String descriptionVal = description.getText().trim();
        String priceVal = price.getText().trim();
        String quantityVal = quantity.getText().trim();
        Category chosenCategory = (Category) selection.getSelectedItem();

        // 2. Validate data
        if (nameVal.isEmpty()) {
            showErrorDialog("Product requires a name");
            name.requestFocus();
            return null;
        }

        if (skuVal.isEmpty()) {
            showErrorDialog("SKU required");
            sku.requestFocus();
            return null;
        }

        // 3. Parse numerical data
        double priceNumber;
        try {
            if (priceVal.isEmpty()) {
                priceNumber = 0.0;
            } else {
                priceNumber = Double.parseDouble(priceVal);
                if (priceNumber < 0) {
                    showErrorDialog("Price cannot be a negative amount");
                    price.requestFocus();
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Price has to be a valid number");
            price.requestFocus();
            return null;
        }

        int quantityNumber;
        try {
            if (quantityVal.isEmpty()) {
                quantityNumber = 0;
            } else {
                quantityNumber = Integer.parseInt(quantityVal);
                if (quantityNumber < 0) {
                    showErrorDialog("Quantity cannot be a negative value");
                    quantity.requestFocus();
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Quantity must be a valid input");
            quantity.requestFocus();
            return null;
        }

        // 4. Create and update Product object
        if (isEditMode) {
            // For updating product
            currentProduct.setName(nameVal);
            currentProduct.setSku(skuVal);
            currentProduct.setDescription(descriptionVal);
            currentProduct.setPrice(Double.parseDouble(priceVal));
            currentProduct.setQuantity(Integer.parseInt(quantityVal));

            // TODO: Fix later
            if (chosenCategory != null) {
                currentProduct.setCategoryID(chosenCategory.getId());
                currentProduct.setCategoryName(chosenCategory.getName());
            } else {
                currentProduct.setCategoryID(null);
                currentProduct.setCategoryName(null);
            }
            return currentProduct;

        } else {
            // For creating a product
            Product newProduct = new Product();
            newProduct.setName(nameVal);
            newProduct.setSku(skuVal);
            newProduct.setDescription(descriptionVal);
            newProduct.setPrice(Double.parseDouble(priceVal));
            newProduct.setQuantity(Integer.parseInt(quantityVal));

            if (chosenCategory != null) {
                newProduct.setCategoryID(chosenCategory.getId());
                newProduct.setCategoryName(chosenCategory.getName());
            } else {
                newProduct.setCategoryID(null);
                newProduct.setCategoryName(null);
            }
            return newProduct;
        }
    }

    // OPTIONAL METHOD
    private boolean isSKUunique(String skuVal) {
        // TODO: Implement later
        return true;
    }

    /**
     * Input checker
     */
    private boolean validateInput(String nameValue, String skuValue, double priceValue, int quantityValue) {

        if (skuValue.length() < 3) {
            showErrorDialog("SKU must be greater than 3 characters");
            sku.requestFocus();
            return false;
        }

        if (nameValue.length() > 100) {
            showErrorDialog("Product name cannot be larger than 100 characters");
            name.requestFocus();
            return false;
        }

        if (priceValue > 100000) {
            showErrorDialog("Price cannot be $100,000");
            price.requestFocus();
            return false;
        }
        // If all passes
        return true;
    }

    /**
     * Shows error message to user
     * @param message The error to display to user
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Checks if user saved or cancelled
     * @return true if saved, false otherwise
     */
    private boolean wasSaved() {
        return wasSaved;
    }

    /**
     * Method to get Product data
     * @return Product data
     */
    public Product getProduct() {
        return wasSaved ? currentProduct : null;
    }

    public void resetForm() {
        name.setText("");
        sku.setText("");
        description.setText("");
        price.setText("");
        quantity.setText("");
        selection.setSelectedItem(null);
        wasSaved = false;
    }

    private void setInitialFocus() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            setInitialFocus();
        }
    }
}
