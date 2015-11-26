package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

enum OP {
    PLUS, MINUS, MULT, DIVIDE, MODULO, LSHIFT, RSHIFT, BIT_AND, BIT_XOR, BIT_OR, UNKNOWN

}

class ExprInfixOperator extends AbstractBinaryExpr {
    private final OP operation;

    ExprInfixOperator(IToken token, IExpression left, IExpression right) {
        super(token, left, right);
        switch (token.getString()) {
        case "+":
            operation = OP.PLUS;
            break;
        case "-":
            operation = OP.MINUS;
            break;
        case "*":
            operation = OP.MULT;
            break;
        case "/":
            operation = OP.DIVIDE;
            break;
        case "%":
        case "mod":
            operation = OP.MODULO;
            break;
        case "<<":
            operation = OP.LSHIFT;
            break;
        case ">>":
            operation = OP.RSHIFT;
            break;
        case "&":
            operation = OP.BIT_AND;
            break;
        case "^":
            operation = OP.BIT_XOR;
            break;
        case "|":
            operation = OP.BIT_OR;
            break;
        default:
            operation = OP.UNKNOWN;
            break;
        }
    }

    @Override
    public Object eval() throws ExpressionException {
        Object leftObject = left.eval();
        Object rightObject = right.eval();
        if (operation == OP.PLUS) {
            if (leftObject instanceof String || rightObject instanceof String) {
                return "" + leftObject + rightObject;
            }
        }
        if (!(leftObject instanceof Number)) {
            throw left
                    .newExpressionException("'" + token.getString() + "' requires Number as leftObject operand!");
        }
        if (!(rightObject instanceof Number)) {
            throw right
                    .newExpressionException("'" + token.getString() + "' requires Number as RightObject operand!");
        }
        if (leftObject instanceof Double || rightObject instanceof Double) {
            return evalDouble(((Number) leftObject).doubleValue(), ((Number) rightObject).doubleValue());
        } else {
            return evalLong(((Number) leftObject).longValue(), ((Number) rightObject).longValue());

        }
    }

    private Object evalLong(long left, long right)
            throws ExpressionException {
        long result = 0;
        switch (operation) {
        case PLUS:
            result = left + right;
            break;
        case MINUS:
            result = left - right;
            break;
        case MULT:
            result = left * right;
            break;
        case DIVIDE:
            result = left / right;
            break;
        case MODULO:
            result = left % right;
            break;
        case LSHIFT:
            result = left << right;
            break;
        case RSHIFT:
            result = left >> right;
            break;
        case BIT_AND:
            result = left & right;
            break;
        case BIT_XOR:
            result = left ^ right;
            break;
        case BIT_OR:
            result = left | right;
            break;
        default:
            throw newExpressionException("unsupported operand!");
        }
        // System.out.println(this+"="+result);
        return new Long(result);
    }

    private Object evalDouble(double left, double right) throws ExpressionException {
        double result = 0;
        switch (operation) {
        case PLUS:
            result = left + right;
            break;
        case MINUS:
            result = left - right;
            break;
        case MULT:
            result = left * right;
            break;
        case DIVIDE:
            result = left / right;
            break;
        case MODULO:
            result = left % right;
            break;
        default:
            throw newExpressionException("unsupported operand!");
        }
        // System.out.println(this+"="+result);
        return new Double(result);
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        if (operation == OP.UNKNOWN) {
            throw newExpressionException("Unknown operator: " + token.getString());
        }
        super.setStore(store);
    }

}
