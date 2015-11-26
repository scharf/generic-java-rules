package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.builder.AbstractExpressionBuilder;
import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

public class ExpressionBuilder extends AbstractExpressionBuilder<IExpression, ExpressionException> {

    @Override
    public IExpression buildLiteral(IToken token, Object value) throws ExpressionException {
        return new ExprLiteral(token, value);
    }

    @Override
    public IExpression buildFunction(IToken token, IExpression self, String function, Object... args)
            throws ExpressionException {
        switch (function.toLowerCase()) {
        case "__not__":
            return new ExprNot(token, (IExpression) args[0]);
        case "__or__":
            return new ExprOr(token, (IExpression) args[0], (IExpression) args[1]);
        case "__and__":
            return new ExprAnd(token, (IExpression) args[0], (IExpression) args[1]);
        case "__-__":
            if (args.length == 1) {
                return new ExprNegate(token, (IExpression) args[0]);
            }
            // NO break;
        case "__+__":
        case "__*__":
        case "__/__":
        case "__%__":
        case "__mod__":
        case "__^__":
        case "__|__":
        case "__&__":
        case "__<<__":
        case "__>>__":
            return new ExprInfixOperator(token, (IExpression) args[0], (IExpression) args[1]);
        case "__<__":
        case "__<=__":
        case "__==__":
        case "__!=__":
        case "__>__":
        case "__>=__":
            return new ExprCompare(token, (IExpression) args[0], (IExpression) args[1]);
        case "matches":
            return new ExprMatches(token, self, (IExpression) args[0]);
        case "print":
            return new ExprPrint(token, (IExpression) args[0]);
        }
        throw new ExpressionException(token, function);
    }

    @Override
    public IExpression buildVariable(IToken token, String name) throws ExpressionException {
        return new ExprVariable(token, name);
    }

}
