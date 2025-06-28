package main.java.gui;

import main.java.api.ProductApiClient;
import main.java.model.Product;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    private void addProduct() {
        showProductDialog(null);
    }

    private void editProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) return;

        Product selectedProduct = tableModel.getProductAt(selectedRow);
        showProductDialog(selectedProduct);
    }

    private void deleteProduct(int productId) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return productApiClient.deleteProduct(productId);
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(ProductPanel.this,
                                "Product deleted successfully",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadProducts();
                    }
                } catch (Exception e) {
                    showErrorMessage("Failed to delete", e);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        worker.execute();
    }



    private void loadProducts() {
        // Show current state
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        refreshButton.setEnabled(true);
        refreshButton.setText("Loading...");

        // Perform API call in background
        SwingWorker<java.util.List<Product>, Void> worker = new SwingWorker<java.util.List<Product>, Void>() {
            @Override
            protected java.util.List<Product> doInBackground() throws Exception {
                return productApiClient.getAllProducts();
            }

            @Override
            protected void done() {
                try {
                    java.util.List<Product> products = get();
                    tableModel.setProducts((java.util.List<Product>) products);
                } catch (Exception e) {
                    showErrorMessage("Failed to load products", e);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                    refreshButton.setEnabled(true);
                    refreshButton.setText("Refresh");
                }
            }
        };
        worker.execute();
    }

    private void showErrorMessage(String message, Exception e) {
        String entireMessage = message + "\n\nError Details: " + e.getMessage();
        JOptionPane.showMessageDialog(this,
                entireMessage, "Error", JOptionPane.ERROR_MESSAGE);
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }

    private class ProductTableModel extends AbstractTableModel{
        private List<Product> products;
        private final String[] columnNames = {"ID", "Name",  "SKU", "Price",
                                              "Quantity", "Category"};

        public ProductTableModel() {
            this.products = new ArrayList<>();
        }

        public void setProducts(java.util.List<Product> products) {
            this.products = products != null ? products : new ArrayList<>();
            fireTableDataChanged();
        }

        public Product getProductAt(int rowIndex) {
            return products.get(rowIndex);
        }

        @Override
        public int getRowCount() {
            return products.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Product product = products.get(rowIndex);
            switch(columnIndex) {
                case 0 : return product.getId();
                case 1 : return product.getName();
                case 2 : return product.getSku();
                case 3 : return String.format("$%.2f", product.getPrice());
                case 4 : return product.getQuantity();
                case 5 : return product.getCategoryName() != null ?
                                product.getCategoryName() : "Uncategorized";
                default : return null;
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex) {
                // ID
                case 0 : return Integer.class;
                // Index
                case 4 : return Integer.class;
                default : return String.class;
            }
        }
    }
}
