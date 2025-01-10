package clients.cashier;

import middle.RemoteMiddleFactory;
import middle.MiddleFactory;
import javax.swing.*;

/**
 * Cashier Client Application
 */
public class CashierClient {
    public static void main(String[] args) {
        try {
            // Initialize RemoteMiddleFactory
            MiddleFactory mf = new RemoteMiddleFactory();

            // Set up the GUI in the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                try {
                    // Create and display the Cashier View
                    JFrame frame = new JFrame("Cashier Client");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    // Create the model
                    CashierModel cashierModel = new CashierModel(mf);

                    // Instantiate the Cashier View
                    CashierView cashierView = new CashierView(frame, mf, 100, 100);

                    // Instantiate the Controller and link it to the View and Model
                    CashierController cashierController = new CashierController(cashierModel, cashierView);
                    cashierView.setController(cashierController);

                    // Set the window visible
                    frame.setVisible(true);
                } catch (Exception e) {
                    System.out.println("Error initializing Cashier Client: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.out.println("Failed to initialize MiddleFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


