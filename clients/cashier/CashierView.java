package clients.cashier;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Enhanced View of the model with a black-and-white theme
 */
public class CashierView implements Observer {
    private static final int H = 350;  // Height of the window
    private static final int W = 500;  // Width of the window

    private static final String CHECK = "Check";
    private static final String BUY = "Buy";
    private static final String BOUGHT = "Bought/Pay";

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtCheck = new JButton(CHECK);
    private final JButton theBtBuy = new JButton(BUY);
    private final JButton theBtBought = new JButton(BOUGHT);

    private StockReadWriter theStock = null;
    private OrderProcessing theOrder = null;
    private CashierController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factory to deliver order and stock objects
     * @param x   x-coordinate of the window position
     * @param y   y-coordinate of the window position
     */
    public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            theStock = mf.makeStockReadWriter();  // Database access
            theOrder = mf.makeOrderProcessing();  // Process order
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        Container cp = rpc.getContentPane();  // Content Pane
        Container rootWindow = (Container) rpc;  // Root Window
        cp.setLayout(null);  // No layout manager
        rootWindow.setSize(W, H);  // Window size
        rootWindow.setLocation(x, y);

        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 12);

        // Page Title
        pageTitle.setBounds(50, 10, 400, 30);
        pageTitle.setText("Thank You for Shopping at MiniStore");
        pageTitle.setFont(titleFont);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(pageTitle);

        // Check Button
        theBtCheck.setBounds(30, 60, 100, 40);
        theBtCheck.setFont(buttonFont);
        theBtCheck.setBackground(Color.BLACK);
        theBtCheck.setForeground(Color.WHITE);
        theBtCheck.addActionListener(e -> cont.doCheck(theInput.getText()));
        cp.add(theBtCheck);

        // Buy Button
        theBtBuy.setBounds(30, 110, 100, 40);
        theBtBuy.setFont(buttonFont);
        theBtBuy.setBackground(Color.BLACK);
        theBtBuy.setForeground(Color.WHITE);
        theBtBuy.addActionListener(e -> cont.doBuy());
        cp.add(theBtBuy);

        // Bought Button
        theBtBought.setBounds(30, 160, 100, 40);
        theBtBought.setFont(buttonFont);
        theBtBought.setBackground(Color.BLACK);
        theBtBought.setForeground(Color.WHITE);
        theBtBought.addActionListener(e -> cont.doBought());
        cp.add(theBtBought);

        // Input Field
        theInput.setBounds(150, 60, 300, 30);
        theInput.setFont(inputFont);
        theInput.setBackground(Color.WHITE);
        theInput.setForeground(Color.BLACK);
        cp.add(theInput);

        // Action Label
        theAction.setBounds(150, 100, 300, 30);
        theAction.setFont(inputFont);
        cp.add(theAction);

        // Output Area
        theSP.setBounds(150, 140, 300, 150);
        theOutput.setFont(inputFont);
        theOutput.setBackground(Color.WHITE);
        theOutput.setForeground(Color.BLACK);
        theSP.getViewport().add(theOutput);
        cp.add(theSP);

        rootWindow.setVisible(true);  // Make visible
        theInput.requestFocus();  // Focus is here
    }

    /**
     * Set the controller
     *
     * @param c The controller
     */
    public void setController(CashierController c) {
        cont = c;
    }

    /**
     * Update the view
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */
    @Override
    public void update(Observable modelC, Object arg) {
        CashierModel model = (CashierModel) modelC;
        String message = (String) arg;
        theAction.setText(message);
        Basket basket = model.getBasket();
        if (basket == null)
            theOutput.setText("Customers order");
        else
            theOutput.setText(basket.getDetails());

        theInput.requestFocus();  // Focus is here
    }

	public void displayError(String string) {
		// TODO Auto-generated method stub
		
	}
}


