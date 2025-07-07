package main.java.gui;

import main.java.api.CategoryApiClient;
import main.java.model.Category;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Purpose: Panel that displays and manages categories in a table manner
 */
public class CategoryPanel extends JPanel {
    JTable categoryTable;
    CategoryTableModel tableModel;
    JButton refreshButton;
    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    JTextField searchField;
    JButton searchButton;
    CategoryApiClient categoryApiClient;

    public CategoryPanel() {
        // TODO
    }

    private void initializeComponents() {

    }

    private void setupLayout() {

    }

    private JPanel createSearchPanel() {

    }

    private JPanel createButtonPanel() {

    }

    private void setupEventHandler() {

    }

    private void loadCategories() {

    }

    private void showAddCategoryDialog() {

    }

    private void editSelectedCategory() {

    }

    private void deleteSelectedCategory() {

    }

    private void deleteCategory(int categoryId) {

    }

    private void searchCategories() {

    }

    private void showErrorMessages(String message, Exception e) {

    }

    private class CategoryTableModel extends AbstractTableModel {
        private List<Category> categories;
        private final String[] columnNames = {"ID", "Name", "Description", "Created At"};
    }


}
