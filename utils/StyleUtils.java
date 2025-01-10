package utils;

import javax.swing.*;
 
import java.awt.*;

public class StyleUtils {

    public static void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK); // Black text
    }

    public static void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.BLACK); // Black background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
    }

    public static void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE); // White background
        textField.setForeground(Color.BLACK); // Black text
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public static void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(Color.WHITE); // White background
        textArea.setForeground(Color.BLACK); // Black text
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(Color.WHITE); // White background
    }
}

