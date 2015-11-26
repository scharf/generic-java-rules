package gr.scharf.rules;

import gr.scharf.rules.actions.SetValueExpression;
import gr.scharf.rules.expression.ExpressionException;
import gr.scharf.rules.expression.ExpressionParser;

public class RuleMain {

    public static void main(String[] args) throws ExpressionException {
        RuleEngine ruleEngine = createStore();
        System.out.println("");
        System.out.println("Run");
        System.out.println(ruleEngine.getStore());
        System.out.println("-----------------------------------------------------------------------------");
        ruleEngine.run();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(ruleEngine.getStore());

        ruleEngine = createStore();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Run each rule at most one time:");
        System.out.println(ruleEngine.getStore());
        System.out.println("-----------------------------------------------------------------------------");
        ruleEngine.runOnce();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(ruleEngine.getStore());

        System.out.println("-----------------------------------------------------------------------------");
        ruleEngine = new RuleEngine();
        new ExpressionParser().parseRules(ruleEngine, "define x = 2; x<3: set x 3; x<10: print(x*2);");
        System.out.println(ruleEngine.getStore());
        System.out.println("-----------------------------------------------------------------------------");
        ruleEngine.runOnce();
    }

    private static RuleEngine createStore() throws ExpressionException {
        RuleEngine ruleEngine = new RuleEngine();

        System.out.println("");
        System.out.println("");
        ruleEngine.defineState("SVNR", new State(42));
        ruleEngine.defineState("Background", new State("blue"));
        ruleEngine.defineState("style", new State("male"));
        ruleEngine.defineState("counter", new State(1));
        ruleEngine.defineState("n", new State(null));
        System.out.println("");
        ruleEngine.addRule(new Rule("SVNR==44", new SetValueExpression("SVNR", "42")));
        ruleEngine.addRule(new Rule("SVNR==43", new SetValueExpression("SVNR", "44")));
        ruleEngine.addRule(new Rule("SVNR==42", new SetValueExpression("Background", "'red'")));
        ruleEngine.addRule(new Rule("SVNR!=42", new SetValueExpression("Background", "'blue'")));
        ruleEngine.addRule(new Rule("SVNR==42", new SetValueExpression("SVNR", "43")));
        ruleEngine.addRule(new Rule("SVNR>=44", new SetValueExpression("SVNR", "100")));
        ruleEngine.addRule(new Rule("Background == 'red'", new SetValueExpression("style", "'female'")));
        ruleEngine.addRule(new Rule("counter<5 and SVNR>43", new SetValueExpression("counter", "counter+1")));
        ruleEngine.addRule(new Rule("counter<5", new SetValueExpression("Background", "n.matches('')")));
        return ruleEngine;
    }

}
