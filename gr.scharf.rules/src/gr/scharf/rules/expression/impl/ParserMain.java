package gr.scharf.rules.expression.impl;

import gr.scharf.rules.State;
import gr.scharf.rules.StateStore;
import gr.scharf.rules.expression.ExpressionParser;
import gr.scharf.rules.expression.IExpression;

public class ParserMain {

    private static StateStore store;

    public static void main(String[] args) {
        store = new StateStore();
        store.define("t", new State(true));
        store.define("f", new State(false));
        store.define("a", new State(1));
        store.define("b", new State(2));
        store.define("str", new State("\"\\\n\tx"));
        parse("42");
        parse("t");
        parse("f");
        parse("not true");
        parse("not f");
        parse("f or f");
        parse("f or t");
        parse("t and dontknow");
        parse("f and t");
        parse("f and t or false");
        parse("t and print(42)");
        parse("t = 6");
        parse("t then");
        parse("1 + 2 * 3 / 4 % 5 ^ 6 | 7 & 8 >> 9 << 10 mod 12");
        parse("1 > 2");
        parse("1 >= 2");
        parse("2 ==  2");
        parse("1 != 2");
        parse("1 <= 2");
        parse("1 < 2");
        parse("'xxx'.matches('.*')");
        parse("'<<xxx\\nt=\\tsl=\\\\q=\\\">>'");
        parse("3.14");
        parse("null == null");
        parse("a+b");
        parse("-1*a+b");
        parse("'xx'+'bb'");
        parse("-a");
    }

    private static void parse(String expression) {

        ExpressionParser parser = new ExpressionParser();
        try {
            IExpression expr = parser.parseExpression(expression);
            expr.setStore(store);
            System.out.println(expr + " -> " + expr.eval() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
