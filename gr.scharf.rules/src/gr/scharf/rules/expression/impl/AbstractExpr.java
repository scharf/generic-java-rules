package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public abstract class AbstractExpr implements IExpression {
    protected IToken token;

    public AbstractExpr(IToken token) {
        this.token = token;
    }

    @Override
    public boolean test() throws ExpressionException {
        return toBoolean(eval());
    }

    protected boolean toBoolean(Object result) throws ExpressionException {
        if (result instanceof Boolean) {
            return ((Boolean) result).booleanValue();
        }
        throw newExpressionException(
            "value is not boolen but " + (result == null ? null : result.getClass().getSimpleName()));
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {

    }

    @Override
    public ExpressionException newExpressionException(String msg) {
        return new ExpressionException(token, msg);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
