package clients.backDoor;

import middle.MiddleFactory;

import middle.StockReadWriter;
import utils.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the BackDoor view.
 */
public class BackDoorView implements Observer {
    private static final String RESTOCK = "Add";
    private static final String CLEAR = "Clear";
    private static final String QUERY = "Query";

    private static final int H = 350;       // Height of window pixels
    private static final int W = 500;       // Width of window pixels

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextField theInputNo = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtClear = new JButton(CLEAR);
    private final JButton theBtRStock = new JButton(RESTOCK);
    private final JButton theBtQuery = new JButton(QUERY);

    private BackDoorController cont;

    /**
     * Constructs the BackDoor view.
     *
     * @param rpc RootPaneContainer to hold the view.
     * @param mf  MiddleFactory for creating connections.
     * @param x   x-coordinate for the window position.
     * @param y   y-coordinate for the window position.
     */
    public BackDoorView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            StockReadWriter theStock = mf.makeStockReadWriter(); // Database access
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Container cp = rpc.getContentPane();
        Container rootWindow = (Container) rpc;
        cp.setLayout(null);
        rootWindow.setSize(W, H);
        rootWindow.setLocation(x, y);

        // Apply consistent styling to the frame
        StyleUtils.styleFrame((JFrame) rootWindow);

        // Title
        pageTitle.setBounds(110, 10, 270, 30);
        pageTitle.setText("Staff Check and Manage Stock");
        StyleUtils.styleLabel(pageTitle);
        cp.add(pageTitle);

        // Query Button
        theBtQuery.setBounds(20, 50, 100, 40);
        theBtQuery.addActionListener(e -> cont.doQuery(theInput.getText()));
        StyleUtils.styleButton(theBtQuery);
        cp.add(theBtQuery);

        // Restock Button
        theBtRStock.setBounds(20, 100, 100, 40);
        theBtRStock.addActionListener(e -> cont.doRStock(theInput.getText(), theInputNo.getText()));
        StyleUtils.styleButton(theBtRStock);
        cp.add(theBtRStock);

        // Clear Button
        theBtClear.setBounds(20, 150, 100, 40);
        theBtClear.addActionListener(e -> cont.doClear());
        StyleUtils.styleButton(theBtClear);
        cp.add(theBtClear);

        // Input Field
        theInput.setBounds(140, 50, 150, 40);
        StyleUtils.styleTextField(theInput);
        cp.add(theInput);

        // Input Quantity Field
        theInputNo.setBounds(300, 50, 150, 40);
        StyleUtils.styleTextField(theInputNo);
        cp.add(theInputNo);

        // Message Area
        theAction.setBounds(140, 100, 310, 30);
        StyleUtils.styleLabel(theAction);
        cp.add(theAction);

        // Output Area
        theSP.setBounds(140, 140, 310, 150);
        theOutput.setEditable(false);
        StyleUtils.styleTextArea(theOutput);
        cp.add(theSP);
        theSP.getViewport().add(theOutput);

        rootWindow.setVisible(true);
        theInput.requestFocus();
    }

    /**
     * Links the controller to this view.
     *
     * @param c BackDoorController instance.
     */
    public void setController(BackDoorController c) {
        cont = c;
    }

    /**
     * Updates the view with the latest model data.
     *
     * @param modelC The observed model.
     * @param arg    Specific arguments or messages from the model.
     */
    @Override
    public void update(Observable modelC, Object arg) {
        BackDoorModel model = (BackDoorModel) modelC;
        String message = (String) arg;
        theAction.setText(message);

        theOutput.setText(model.getBasket().getDetails());
        theInput.requestFocus();
    }
}

