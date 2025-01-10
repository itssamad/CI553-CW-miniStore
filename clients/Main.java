package clients;

import clients.backDoor.BackDoorController;

import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Starts all the clients (user interface) as a single application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().begin());
    }

    /**
     * Starts the system (Non-distributed).
     */
    public void begin() {
        // Initialize MiddleFactory
        MiddleFactory mlf = new LocalMiddleFactory();

        // Start all GUIs
        startCustomerGUI_MVC(mlf, 50, 50);  // Top-left
        startCashierGUI_MVC(mlf, 600, 50);  // Top-right
        startPackingGUI_MVC(mlf, 50, 400);  // Bottom-left
        startBackDoorGUI_MVC(mlf, 600, 400); // Bottom-right
    }

    /**
     * Start the Customer client - search products.
     * @param mlf A factory to create objects to access the stock list.
     * @param x   The x-coordinate of the window position.
     * @param y   The y-coordinate of the window position.
     */
    public void startCustomerGUI_MVC(MiddleFactory mlf, int x, int y) {
        JFrame window = createWindow("Customer Client MVC", x, y);

        CustomerModel model = new CustomerModel(mlf);
        CustomerView view = new CustomerView(window, mlf, x, y);
        CustomerController controller = new CustomerController(model, view);
        view.setController(controller);

        model.addObserver(view);  // Add observer to the model
        window.setVisible(true);
    }

    /**
     * Start the Cashier client - customer checks stock, buys products.
     * @param mlf A factory to create objects to access the stock list.
     * @param x   The x-coordinate of the window position.
     * @param y   The y-coordinate of the window position.
     */
    public void startCashierGUI_MVC(MiddleFactory mlf, int x, int y) {
        JFrame window = createWindow("Cashier Client MVC", x, y);

        CashierModel model = new CashierModel(mlf);
        CashierView view = new CashierView(window, mlf, x, y);
        CashierController controller = new CashierController(model, view);
        view.setController(controller);

        model.addObserver(view);  // Add observer to the model
        window.setVisible(true);
        model.askForUpdate();  // Initial display
    }

    /**
     * Start the Packing client - warehouse staff packs bought orders.
     * @param mlf A factory to create objects to access the stock list.
     * @param x   The x-coordinate of the window position.
     * @param y   The y-coordinate of the window position.
     */
    public void startPackingGUI_MVC(MiddleFactory mlf, int x, int y) {
        JFrame window = createWindow("Packing Client MVC", x, y);

        PackingModel model = new PackingModel(mlf);
        PackingView view = new PackingView(window, mlf, x, y);
        PackingController controller = new PackingController(model, view);
        view.setController(controller);

        model.addObserver(view);  // Add observer to the model
        window.setVisible(true);
    }

    /**
     * Start the BackDoor client - store staff checks and updates stock.
     * @param mlf A factory to create objects to access the stock list.
     * @param x   The x-coordinate of the window position.
     * @param y   The y-coordinate of the window position.
     */
    public void startBackDoorGUI_MVC(MiddleFactory mlf, int x, int y) {
        JFrame window = createWindow("BackDoor Client MVC", x, y);

        BackDoorModel model = new BackDoorModel(mlf);
        BackDoorView view = new BackDoorView(window, mlf, x, y);
        BackDoorController controller = new BackDoorController(model, view);
        view.setController(controller);

        model.addObserver(view);  // Add observer to the model
        window.setVisible(true);
    }

    /**
     * Creates a consistent JFrame for each client.
     * @param title The title of the window.
     * @param x     The x-coordinate of the window position.
     * @param y     The y-coordinate of the window position.
     * @return A JFrame with standardized settings.
     */
    private JFrame createWindow(String title, int x, int y) {
        JFrame window = new JFrame();
        window.setTitle(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 400);  // Consistent size for all windows
        window.setLocation(x, y);  // Set position on screen
        window.setLayout(new BorderLayout());
        return window;
    }
}

