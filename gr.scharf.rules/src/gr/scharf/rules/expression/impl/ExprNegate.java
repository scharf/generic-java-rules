package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public class ExprNegate extends AbstractUnaryExpr {

    public ExprNegate(IToken token, IExpression inner) {
        super(token, inner);
    }

    @Override
    public Object eval() throws ExpressionException {
        Object number = inner.eval();
        if (number instanceof Double || number instanceof Float) {
            return new Double(-((Number) number).doubleValue());
        } else if (number instanceof Number) {
            return new Long(-((Number) number).longValue());
        }
        throw newExpressionException("is not a number: " + number);
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        inner.setStore(store);
        super.setStore(store);
    }

    @Override
    public String toString() {
        return "(-" + inner + ")";
    }
}
