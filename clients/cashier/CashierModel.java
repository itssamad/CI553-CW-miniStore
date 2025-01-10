package clients.cashier;

import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.*;

import java.util.Observable;

/**
 * Implements the Model of the cashier client.
 */
public class CashierModel extends Observable {
    private enum State { process, checked }

    private State theState = State.process;   // Current state
    private Product theProduct = null;        // Current product
    private Basket theBasket = null;          // Bought items

    private String pn = "";                   // Product being processed

    private StockReadWriter theStock = null;
    private OrderProcessing theOrder = null;

    /**
     * Constructs the model of the Cashier.
     *
     * @param mf The factory to create the connection objects.
     */
    public CashierModel(MiddleFactory mf) {
        try {
            theStock = mf.makeStockReadWriter(); // Database access
            theOrder = mf.makeOrderProcessing(); // Process order
        } catch (Exception e) {
            DEBUG.error("CashierModel.constructor\n%s", e.getMessage());
        }
        theState = State.process; // Initial state
    }

    /**
     * Gets the basket of products.
     *
     * @return The current basket.
     */
    public Basket getBasket() {
        return theBasket;
    }

    /**
     * Checks if the product is in stock.
     *
     * @param productNum The product number.
     */
    public void doCheck(String productNum) {
        String theAction = "";
        theState = State.process; // Reset state
        pn = productNum.trim();   // Product number
        int amount = 1;           // Quantity to check

        try {
            if (theStock.exists(pn)) { // Stock exists?
                Product pr = theStock.getDetails(pn); // Get product details
                if (pr.getQuantity() >= amount) { // Enough stock available?
                    theAction = String.format("%s : %7.2f (%2d available)",
                        pr.getDescription(), pr.getPrice(), pr.getQuantity());
                    theProduct = pr; // Save product
                    theProduct.setQuantity(amount); // Set quantity to 1
                    theState = State.checked; // Ready for purchase
                } else {
                    theAction = pr.getDescription() + " not in stock.";
                }
            } else {
                theAction = "Unknown product number " + pn;
            }
        } catch (StockException e) {
            DEBUG.error("CashierModel.doCheck\n%s", e.getMessage());
            theAction = e.getMessage(); // Show error message
        }
        setChanged();
        notifyObservers(theAction);
    }

    /**
     * Buys the product or increases its quantity if it already exists in the basket.
     */
    public void doBuy() {
        String theAction = "";
        if (theState != State.checked) { // Product not checked yet
            theAction = "Please check its availability.";
        } else {
            makeBasketIfRequired(); // Create a basket if none exists
            Product existingProduct = theBasket.getProduct(theProduct.getProductNum());

            if (existingProduct != null) {
                // Product already exists in the basket; update its quantity
                existingProduct.setQuantity(existingProduct.getQuantity() + theProduct.getQuantity());
                theAction = "Updated quantity of " + theProduct.getDescription();
            } else {
                // Add new product to the basket
                theBasket.add(theProduct);
                theAction = "Purchased " + theProduct.getDescription();
            }

            theState = State.process; // Reset state
        }
        setChanged();
        notifyObservers(theAction);
    }

    /**
     * Completes the transaction by processing the basket.
     */
    public void doBought() {
        String theAction = "";
        try {
            if (theBasket != null && theBasket.size() >= 1) { // Basket has items
                theOrder.newOrder(theBasket); // Process the order
                theBasket = null; // Clear the basket
            }
            theAction = "Start New Order"; // Indicate new order process
            theState = State.process; // Reset state
        } catch (OrderException e) {
            DEBUG.error("CashierModel.doBought\n%s", e.getMessage());
            theAction = e.getMessage(); // Show error message
        }
        theBasket = null;
        setChanged();
        notifyObservers(theAction);
    }

    /**
     * Updates the view (usually called at the start of the application or after a reset).
     */
    public void askForUpdate() {
        setChanged();
        notifyObservers("Welcome");
    }

    /**
     * Creates a new basket if one does not already exist.
     */
    private void makeBasketIfRequired() {
        if (theBasket == null) {
            try {
                int uniqueOrderNum = theOrder.uniqueNumber(); // Generate unique order number
                theBasket = makeBasket(); // Create a new basket
                theBasket.setOrderNum(uniqueOrderNum); // Set the order number
            } catch (OrderException e) {
                DEBUG.error("CashierModel.makeBasketIfRequired\n%s", e.getMessage());
            }
        }
    }

    /**
     * Creates a new instance of a basket.
     *
     * @return A new basket instance.
     */
    protected Basket makeBasket() {
        return new Basket();
    }
}



