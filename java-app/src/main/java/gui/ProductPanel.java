package main.java.gui;

import main.java.api.ProductApiClient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

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

    }

    private JPanel createButtonPanel() {
        return null;
    }

    private void setupEventHandlers() {

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
