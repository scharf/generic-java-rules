package gr.scharf.rules.expression.impl;

import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.IExpression;

enum COMP_OP {
    LT, LE, EQ, NE, GE, GT, UNKNOWN
};

class ExprCompare extends AbstractBinaryExpr {
    protected final COMP_OP operation;

    ExprCompare(IToken token, IExpression left, IExpression right) {
        super(token, left, right);
        switch (token.getString()) {
        case "<":
            operation = COMP_OP.LT;
            break;
        case "<=":
            operation = COMP_OP.LE;
            break;
        case "==":
            operation = COMP_OP.EQ;
            break;
        case "!=":
            operation = COMP_OP.NE;
            break;
        case ">=":
            operation = COMP_OP.GE;
            break;
        case ">":
            operation = COMP_OP.GT;
            break;
        default:
            operation = COMP_OP.UNKNOWN;
            break;
        }
    }

    @Override
    public Object eval() throws ExpressionException {
        Object leftObj = left.eval();
        Object rightObj = right.eval();
        if (leftObj instanceof Number && rightObj instanceof Number && leftObj.getClass() != rightObj.getClass()) {
            if (leftObj instanceof Double) {
                rightObj = new Double(((Number) rightObj).doubleValue());
            } else if (rightObj instanceof Double) {
                leftObj = new Double(((Number) leftObj).doubleValue());
            } else {
                leftObj = new Long(((Number) leftObj).longValue());
                rightObj = new Long(((Number) rightObj).longValue());
            }
        }
        switch (operation) {
        case EQ: {
            if (leftObj == null || rightObj == null)
                return leftObj == rightObj;
            else
                return leftObj.equals(rightObj);
        }
        case NE: {
            if (leftObj == null || rightObj == null)
                return leftObj != rightObj;
            else
                return !leftObj.equals(rightObj);
        }
        default:
            break;
        }
        if (leftObj == null) {
            throw left.newExpressionException("left operand is null");
        }
        if (rightObj == null) {
            throw right.newExpressionException("right operand is null");
        }
        if (!(leftObj instanceof Comparable)) {
            throw left.newExpressionException("left operand is not comparable");
        }
        if (!(rightObj instanceof Comparable)) {
            throw right.newExpressionException("right operand is not comparable");
        }
        int cmp = -1;
        try {
            cmp = compareTo(leftObj, rightObj);
        } catch (Throwable t) {
            throw newExpressionException("" + t);
        }
        switch (operation) {
        case LT:
            return cmp < 0;
        case LE:
            return cmp <= 0;
        case GE:
            return cmp >= 0;
        case GT:
            return cmp > 0;
        default:
            throw newExpressionException("operator '" + token.getString() + "' not supported");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private int compareTo(Object left, Object right) {
        return ((Comparable) left).compareTo(right);
    }

    @Override
    public void setStore(StateStore store) throws ExpressionException {
        if (operation == COMP_OP.UNKNOWN) {
            throw newExpressionException("Unknown operator: " + token.getString());
        }
        super.setStore(store);
    }
}
