package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public class ExprPrint extends AbstractUnaryExpr {

    public ExprPrint(IToken token, IExpression arg) {
        super(token, arg);
    }

    @Override
    public Object eval() throws ExpressionException {
        Object arg = inner.eval();
        System.out.println("*******" + arg);
        return null;
    }

    @Override
    public String toString() {
        return "print(" + inner.toString() + ")";
    }
}
