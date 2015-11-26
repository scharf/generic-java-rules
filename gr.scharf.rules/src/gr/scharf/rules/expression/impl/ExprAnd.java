package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public class ExprAnd extends AbstractBinaryExpr {

    public ExprAnd(IToken token, IExpression left, IExpression right) {
        super(token, left, right);
    }

    @Override
    public Object eval() throws ExpressionException {
        return left.test() && right.test();
    }

}
