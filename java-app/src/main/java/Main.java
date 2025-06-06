package main.java;

import main.java.gui.MainWindow;

import javax.swing.*;
import java.io.IO;

public class Main {
    public static void main(String[] args) {

        // Sets the look and feel of the system (regular appearance)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException
                    | InstantiationException | IllegalAccessException e) {
            System.err.println("Couldn't set the system look or feel: " + e.getMessage());
        }

        // Creates and displays the GUI
        // Current impl, will change when MainWindow gets implemented fully (general idea)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create the window
                    MainWindow mainWindow = new MainWindow();

                    // Center it
                    mainWindow.setLocationRelativeTo(null);

                    // Ensures visibility
                    mainWindow.setVisible(true);

                    System.out.println("Inventory Management System running . . . ");
                } catch (Exception e) {
                    // Handle errors, obv
                    System.err.println("Application failing to start: " + e.getMessage());
                    e.printStackTrace();

                    JOptionPane.showMessageDialog(null,
                            "App failing to start: " + e.getMessage(),
                            "Startup error", JOptionPane.ERROR_MESSAGE);

                    // If all fails then close app
                    System.exit(1);
                }
            }
        });
    }
}
