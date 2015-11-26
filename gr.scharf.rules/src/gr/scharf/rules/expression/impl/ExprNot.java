package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public class ExprNot extends AbstractUnaryExpr {

    public ExprNot(IToken token, IExpression inner) {
        super(token, inner);
    }

    @Override
    public Object eval() throws ExpressionException {
        return !toBoolean(inner.eval());
    }

    @Override
    public String toString() {
        return "(not " + inner + ")";
    }
}
