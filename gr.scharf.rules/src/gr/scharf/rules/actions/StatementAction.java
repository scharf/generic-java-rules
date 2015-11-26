package gr.scharf.rules.actions;

import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public class StatementAction extends AbstractStoreAction {

    IExpression expression;

    public StatementAction(IExpression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public void run() throws ExpressionException {
        expression.eval();
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        expression.setStore(store);
        super.setStore(store);
    }

    @Override
    public String toString() {
        return expression + ";";
    }
}
