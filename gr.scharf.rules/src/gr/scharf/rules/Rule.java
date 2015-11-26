package gr.scharf.rules;

import gr.scharf.rules.actions.IAction;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.ExpressionParser;
import gr.scharf.rules.expression.IExpression;

public class Rule {
    private IExpression condition;
    private IAction     action;

    public Rule(IExpression condition, IAction action) {
        this.condition = condition;
        this.action = action;
    }

    public Rule(String condition, IAction action) throws ExpressionException {
        this.condition = new ExpressionParser().parseExpression(condition);
        this.action = action;
    }

    public void setStore(StateStore store) throws ExpressionException {
        condition.setStore(store);
        action.setStore(store);
    }

    /**
     * @return true if the condition has matched
     * @throws ExpressionException
     */
    public boolean execute() throws ExpressionException {
        if (condition.test()) {
            System.out.println(this);

            action.run();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return condition.toString() + " -> " + action.toString();
    }
}
