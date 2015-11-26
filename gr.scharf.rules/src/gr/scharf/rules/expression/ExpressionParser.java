package gr.scharf.rules.expression;

import gr.scharf.expr.parser.builder.ExprParser;
import gr.scharf.expr.parser.lexer.ILexer;
import gr.scharf.expr.parser.lexer.IToken;
import gr.scharf.expr.parser.lexer.Lexer;
import gr.scharf.expr.parser.parser.ParseException;
import gr.scharf.rules.Rule;
import gr.scharf.rules.RuleEngine;
import gr.scharf.rules.State;
import gr.scharf.rules.actions.IAction;
import gr.scharf.rules.actions.SetValueExpression;
import gr.scharf.rules.actions.StatementAction;
import gr.scharf.rules.expression.impl.ExpressionBuilder;

public class ExpressionParser {

    private RuleEngine engine;
    private IToken     token;
    private ILexer     lexer;

    public ExpressionParser() {

    }

    public void parseRules(RuleEngine engine, String rules) throws ExpressionException {
        this.engine = engine;
        lexer = new Lexer(rules);
        nextToken();
        while (!token.getType().isEOF()) {
            parseDefineOrRule();
        }
    }

    private void parseDefineOrRule() throws ExpressionException {
        switch (token.getString()) {
        case "define":
            nextToken();
            parseDefine();
            break;
        default:
            parseRule();
            break;
        }
    }

    private void parseRule() throws ParseException, ExpressionException {
        IExpression condition = parseExpression();
        if (!(":".equals(token.getString()))) {
            throw new ParseException(token, ": expected got " + token);
        }
        nextToken();
        if ("set".equals(token.getString())) {
            nextToken();
            String name = parseIdentifier();
            IExpression expr = parseExpression();
            IAction action = new SetValueExpression(name, expr);
            engine.addRule(new Rule(condition, action));
        } else {
            IExpression expr = parseExpression();
            IAction action = new StatementAction(expr);
            engine.addRule(new Rule(condition, action));

        }
        if (!(";".equals(token.getString()))) {
            throw new ParseException(token, "; expected");

        }
        nextToken();
    }

    private IExpression parseExpression() throws ExpressionException {
        ExprParser<IExpression, ExpressionException> exprParser = new ExprParser<IExpression, ExpressionException>(
                new ExpressionBuilder(),
                false);
        IExpression expr = exprParser.parse(lexer);
        lexer.useCurrentTokenAsNextToken();
        nextToken();
        return expr;
    }

    IAction parseAction() {

        return null;
    }

    private void nextToken() {
        do {
            token = lexer.nextToken();
            // skip the whitespace
        } while (token.getType().isWhitespaceOrComment());
    }

    private void parseDefine() throws ExpressionException {
        String varName = parseIdentifier();
        if (!"=".equals(token.getString())) {
            throw new ParseException(token, "'=' expected");

        }
        nextToken();
        IExpression expr = parseExpression();
        expr.setStore(engine.getStore());
        engine.defineState(varName, new State(expr.eval()));
        if (!(";".equals(token.getString()))) {
            throw new ParseException(token, "; expected");

        }
        nextToken();
    }

    private String parseIdentifier() {
        if (!token.getType().isIdentifier()) {
            throw new ParseException(token, "Identifier expected");
        }
        String id = token.getString();
        nextToken();
        return id;
    }

    public IExpression parseExpression(String expression) throws ExpressionException {
        lexer = new Lexer(expression);
        nextToken();
        return parseExpression();
    }
}
