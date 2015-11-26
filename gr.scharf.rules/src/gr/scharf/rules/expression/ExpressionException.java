package gr.scharf.rules.expression;

import gr.scharf.expr.parser.lexer.IToken;

@SuppressWarnings("serial")
public class ExpressionException extends Exception {

    public ExpressionException(IToken token, String msg) {
        super(token.getFormattedErrorMessage(msg), token.getSourceException());
    }

}
