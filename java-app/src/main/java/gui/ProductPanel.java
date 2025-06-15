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


    }

    private void initializeComponents() {

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

    private void deleteProduct() {

    }

    private void showErrorMessages(String message, Exception e) {

    }

    private class ProductTableModel() extends AbstractTableModel {

    }
}
