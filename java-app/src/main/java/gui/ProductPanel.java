package main.java.gui;

import main.java.api.ProductApiClient;
import main.java.model.Product;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Purpose: Will display products in a table and deal w CRUD operations
 */
public class ProductPanel extends JPanel {
    private JTable productTable;
    private ProductTableModel tableModel;
    private JButton refreshButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private ProductApiClient productApiClient;

    public ProductPanel() {
        productApiClient = new ProductApiClient();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadProducts();
    }

    private void initializeComponents() {
        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);

        // Configure the row height and selection mode
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(25);

        // Make the buttons
        refreshButton = new JButton("Refresh");
        addButton = new JButton("Add Product");
        editButton = new JButton("Edit Product");
        deleteButton = new JButton("Delete Product");

        // TODO: Make these temporary not work (change later)
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);

        // Button
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(refreshButton);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        return panel;
    }

    /**
     * Event handlers for all components that have actions
     */
    private void setupEventHandlers() {
        // Refresh
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });

        // Add
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductMessage();
            }
        });

        // Edit
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editProductMessage();
            }
        });

        // Delete
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Allows buttons
        productTable.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = productTable.getSelectedRow() != -1;
            editButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });
    }

    private void addProductMessage() {
        // TODO: Make this actually add a product
        // Placeholder for now
        JOptionPane.showMessageDialog(this,
                "Add Product functionality will be implemented later",
                "Add Prduct", JOptionPane.INFORMATION_MESSAGE);
    }
    private void editProductMessage() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) return;

        Product selectedProduct = tableModel.getProductAt(selectedRow);

        // TODO: Make this edit product
        // Placeholder for now
        JOptionPane.showMessageDialog(this,
                "Edit Product functionality will be implemented later",
                "Edit Product", JOptionPane.INFORMATION_MESSAGE);
    }
    private void deleteProduct() {

    }



    private void loadProducts() {

    }

    private void deleteProduct(int productId) {

    }

    private void showErrorMessage(String message, Exception e) {

    }

    private class ProductTableModel extends AbstractTableModel {

    }
}
