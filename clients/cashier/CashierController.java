package clients.cashier;

/**
 * The Cashier Controller
 */
public class CashierController {
    private final CashierModel model;
    private final CashierView view;

    /**
     * Constructor
     * @param model The model
     * @param view  The view from which the interaction came
     */
    public CashierController(CashierModel model, CashierView view) {
        this.model = model;
        this.view = view;

        // Link the model and view
        model.addObserver(view);
    }

    /**
     * Check interaction from view
     * @param pn The product number to be checked
     */
    public void doCheck(String pn) {
        if (pn != null && !pn.trim().isEmpty()) {
            model.doCheck(pn.trim());
        } else {
            view.displayError("Please enter a valid product number.");
        }
    }

    /**
     * Buy interaction from view
     */
    public void doBuy() {
        model.doBuy();
    }

    /**
     * Bought interaction from view
     */
    public void doBought() {
        model.doBought();
    }
}

