package gr.scharf.rules.actions;

import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;

public interface IAction {
    void run() throws ExpressionException;

    void setStore(StateStore store) throws ExpressionException;

}
