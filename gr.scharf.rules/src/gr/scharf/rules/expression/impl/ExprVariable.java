package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;

public class ExprVariable extends AbstractExpr {

    private final String name;
    private StateStore   store;

    public ExprVariable(IToken token, String name) {
        super(token);
        this.name = name;
    }

    @Override
    public Object eval() throws ExpressionException {
        try {
            return store.getValue(name);
        } catch (Exception e) {
            throw newExpressionException(e.getLocalizedMessage());
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        this.store = store;
        try {
            store.getValue(name);
        } catch (Exception e) {
            throw newExpressionException(e.getLocalizedMessage());
        }
    }
}
