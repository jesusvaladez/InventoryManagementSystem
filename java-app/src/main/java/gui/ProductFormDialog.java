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
