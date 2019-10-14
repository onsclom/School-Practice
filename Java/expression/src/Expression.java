import interpreter.InfixParser;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.*;
import interpreter.InfixLexer;

public class Expression {

    public static void main(String[] args) {
        InfixLexer infixLexer =
                new InfixLexer(CharStreams.fromString("1 + 2 * 7 + 5"));
        InfixParser parser =
                new InfixParser(new CommonTokenStream(infixLexer));

        ParserRuleContext ruleContext = parser.goal();
        Trees.inspect(ruleContext, parser);
    }
}
