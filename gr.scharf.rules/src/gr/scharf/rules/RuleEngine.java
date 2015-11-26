package gr.scharf.rules;

import java.util.ArrayList;
import java.util.List;

import gr.scharf.rules.expression.ExpressionException;

public class RuleEngine {
    private StateStore store = new StateStore();
    private List<Rule> rules = new ArrayList<Rule>();

    public RuleEngine() {
        super();
    }

    public void runOnce() throws ExpressionException {
        List<Rule> currentRules = new ArrayList<Rule>(rules);
        List<Rule> nextRules = new ArrayList<Rule>();
        for (int i = 0; i < 5; i++) {
            store.clearDirtyState();
            for (Rule rule : currentRules) {
                boolean matched = rule.execute();
                if (!matched) {
                    nextRules.add(rule);
                }
            }

            if (!store.isDirtyState()) {
                break;
            }
            currentRules = nextRules;
            nextRules = new ArrayList<Rule>();
        }
    }

    public void run() throws ExpressionException {
        for (int i = 0; i < 5; i++) {
            store.clearDirtyState();
            for (Rule rule : rules) {
                rule.execute();
            }

            if (!store.isDirtyState()) {
                break;
            }
        }
    }

    public void addRule(Rule rule) throws ExpressionException {
        rule.setStore(store);
        rules.add(rule);
    }

    public void defineState(String name, State state) {
        store.define(name, state);
    }

    public StateStore getStore() {
        return store;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Rule rule : rules) {
            b.append(rule.toString());
            b.append("\n");
        }
        return b.toString();
    }
}
