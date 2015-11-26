package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public abstract class AbstractUnaryExpr extends AbstractExpr {

    protected final IExpression inner;

    public AbstractUnaryExpr(IToken token, IExpression inner) {
        super(token);
        this.inner = inner;
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        inner.setStore(store);
        super.setStore(store);
    }
}
