package clients.packing;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view.
 */
public class PackingView implements Observer {
    private static final String PACKED = "Packed";

    private static final int H = 350;  // Height of the window
    private static final int W = 500;  // Width of the window

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtPack = new JButton(PACKED);

    private OrderProcessing theOrder = null;

    private PackingController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factory to deliver order and stock objects
     * @param x   x-coordinate of the position of the window on the screen
     * @param y   y-coordinate of the position of the window on the screen
     */
    public PackingView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            theOrder = mf.makeOrderProcessing();  // Process order
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        Container cp = rpc.getContentPane();  // Content Pane
        Container rootWindow = (Container) rpc;  // Root Window
        cp.setLayout(null);  // No layout manager
        rootWindow.setSize(W, H);  // Window size
        rootWindow.setLocation(x, y);

        // Fonts
        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        Font outputFont = new Font("Arial", Font.PLAIN, 12);

        // Page Title
        pageTitle.setBounds(50, 10, 400, 30);
        pageTitle.setText("Packing Bought Orders");
        pageTitle.setFont(titleFont);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(pageTitle);

        // Pack Button
        theBtPack.setBounds(30, 60, 100, 40);
        theBtPack.setFont(buttonFont);
        theBtPack.setBackground(Color.BLACK);
        theBtPack.setForeground(Color.WHITE);
        theBtPack.addActionListener(e -> cont.doPacked());
        cp.add(theBtPack);

        // Action Label
        theAction.setBounds(150, 60, 300, 30);
        theAction.setFont(outputFont);
        cp.add(theAction);

        // Output Area
        theSP.setBounds(30, 120, 420, 150);
        theOutput.setFont(outputFont);
        theOutput.setBackground(Color.WHITE);
        theOutput.setForeground(Color.BLACK);
        theOutput.setEditable(false);
        theSP.getViewport().add(theOutput);
        cp.add(theSP);

        rootWindow.setVisible(true);  // Make visible
    }

    /**
     * Set the controller object.
     *
     * @param c The controller
     */
    public void setController(PackingController c) {
        cont = c;
    }

    /**
     * Update the view.
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */
    @Override
    public void update(Observable modelC, Object arg) {
        PackingModel model = (PackingModel) modelC;
        String message = (String) arg;
        theAction.setText(message);

        Basket basket = model.getBasket();
        if (basket != null) {
            theOutput.setText(basket.getDetails());
        } else {
            theOutput.setText("");
        }
    }
}

