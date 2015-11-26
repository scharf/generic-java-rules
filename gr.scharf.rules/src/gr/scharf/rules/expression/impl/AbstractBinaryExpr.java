package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public abstract class AbstractBinaryExpr extends AbstractExpr {

    protected IExpression left;
    protected IExpression right;

    public AbstractBinaryExpr(IToken token, IExpression left, IExpression right) {
        super(token);
        this.left = left;
        this.right = right;
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        left.setStore(store);
        right.setStore(store);
        super.setStore(store);
    }

    @Override
    public String toString() {
        return "(" + left + " " + token.getString() + " " + right + ")";
    }

}
