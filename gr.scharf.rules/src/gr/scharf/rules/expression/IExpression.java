package gr.scharf.rules.expression;

import gr.scharf.rules.StateStore;

public interface IExpression {
    Object eval() throws ExpressionException;

    /**
     * @return calls eval and makes sure the result is boolean (or throws an
     *         exception)
     * @throws ExpressionException
     */
    boolean test() throws ExpressionException;

    void setStore(StateStore store) throws ExpressionException;

    ExpressionException newExpressionException(String msg);
}
