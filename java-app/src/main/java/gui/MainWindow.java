package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JTabbedPane tabbedPane;

    public static void main(String[] args) {
    }

    public MainWindow() {
        initalizedWindow();
        windowMenuBar();
        windowContentArea();
    }

    private void initalizedWindow() {
        setTitle("Inventerio");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
    }

    private void windowMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File option of the Menu Navigation Bar
        JMenu FileMenu = new JMenu("File");
        JMenuItem productItem = new JMenuItem("New Product");
        JMenuItem categoryItem = new JMenuItem("New Category");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Product event listener
        productItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Product item clicked . . .");
                // TODO: Actually do stuff when clicked
            }
        });

       // Category event listener
        categoryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Category item clicked . . .");
                // TODO: Actually do stuff when clicked
            }
        });

        // Exit event listener
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                System.out.println("Exit item clicked . . .");
                // TODO: Actually do stuff when clicked
            }
        });

        // Finally, adds the items to the menu bar
        FileMenu.add(productItem);
        FileMenu.add(categoryItem);
        FileMenu.addSeparator();
        FileMenu.add(exitItem);

        // View option of the Menu Navigation Bar
        JMenu ViewMenu = new JMenu("View");
        JMenuItem allProductsItem = new JMenuItem("All Products");
        JMenuItem allCategoriesItem = new JMenuItem("All Categories");
        JMenuItem uncategorizedProductsItem = new JMenuItem("Uncategorized Products");

        // All Products event listener
        allProductsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("All Products option clicked . . .");
                tabbedPane.setSelectedIndex(0);
            }
        });

        // All Categories event listener
        allCategoriesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("All Categories option clicked . . .");
                tabbedPane.setSelectedIndex(1);
            }
        });

        // Uncategorized Products event listener
        uncategorizedProductsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Uncat Product option clicked . . .");
                // TODO: Does stuff when clicked
            }
        });

        // Finally, add items to the menu bar
        ViewMenu.add(allProductsItem);
        ViewMenu.add(allCategoriesItem);
        ViewMenu.addSeparator();
        ViewMenu.add(uncategorizedProductsItem);


        // Help option of the Menu Navigation Bar
        JMenu HelpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        // About event listener
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Inventory Management System\n Version 1.0\n Built by Jesus Valadez",
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        HelpMenu.add(aboutItem);

        // Adds all menus to the nav bar
        menuBar.add(FileMenu);
        menuBar.add(ViewMenu);
        menuBar.add(HelpMenu);
        setJMenuBar(menuBar);
    }

    private void windowContentArea() {
        tabbedPane = new JTabbedPane();

        // Products tab
        ProductPanel productPanel = new ProductPanel();

        // Categories tab
        JPanel categoriesPanel = new JPanel(new BorderLayout());
        JLabel categoriesLabel = new JLabel("Categories will be shown here", JLabel.CENTER);
        categoriesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoriesPanel.add(categoriesLabel, BorderLayout.CENTER);

        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Categories", categoriesPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

}
