package gr.scharf.rules.actions;

import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;

public abstract class AbstractStoreAction implements IAction {

    protected StateStore store;

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        this.store = store;
    }

}
