package clients.customer;

import catalogue.Basket;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */
public class CustomerView implements Observer {
    class Name {
        public static final String CHECK = "Check";
        public static final String CLEAR = "Clear";
    }

    private static final int H = 350;  // Height of the window
    private static final int W = 500;  // Width of the window

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtCheck = new JButton(Name.CHECK);
    private final JButton theBtClear = new JButton(Name.CLEAR);

    private final Picture thePicture = new Picture(80, 80);
    private StockReader theStock = null;
    private CustomerController cont = null;

    public CustomerView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            theStock = mf.makeStockReader();  // Database access
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
        Font inputFont = new Font("Arial", Font.PLAIN, 12);

        // Page Title
        pageTitle.setBounds(50, 10, 400, 30);
        pageTitle.setText("Search Products");
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

        // Clear Button
        theBtClear.setBounds(30, 110, 100, 40);
        theBtClear.setFont(buttonFont);
        theBtClear.setBackground(Color.BLACK);
        theBtClear.setForeground(Color.WHITE);
        theBtClear.addActionListener(e -> cont.doClear());
        cp.add(theBtClear);

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
        theSP.setBounds(150, 140, 300, 100);
        theOutput.setFont(inputFont);
        theOutput.setBackground(Color.WHITE);
        theOutput.setForeground(Color.BLACK);
        theSP.getViewport().add(theOutput);
        cp.add(theSP);

        // Picture Area
        thePicture.setBounds(30, 190, 100, 100);
        cp.add(thePicture);
        thePicture.clear();

        rootWindow.setVisible(true);  // Make visible
        theInput.requestFocus();  // Focus here
    }

    public void setController(CustomerController c) {
        cont = c;
    }

    @Override
    public void update(Observable modelC, Object arg) {
        CustomerModel model = (CustomerModel) modelC;
        String message = (String) arg;
        theAction.setText(message);
        ImageIcon image = model.getPicture();  // Image of product
        if (image == null) {
            thePicture.clear();  // Clear picture
        } else {
            thePicture.set(image);  // Display picture
        }
        theOutput.setText(model.getBasket().getDetails());
        theInput.requestFocus();  // Focus is here
    }
}

