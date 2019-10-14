// Generated from /Users/austinmerrick/Desktop/expression/src/Infix.g4 by ANTLR 4.7.2
package interpreter;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link InfixParser}.
 */
public interface InfixListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link InfixParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(InfixParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(InfixParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link InfixParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(InfixParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(InfixParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link InfixParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(InfixParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(InfixParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link InfixParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(InfixParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(InfixParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InfixParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(InfixParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(InfixParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link InfixParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(InfixParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(InfixParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link InfixParser#digit}.
	 * @param ctx the parse tree
	 */
	void enterDigit(InfixParser.DigitContext ctx);
	/**
	 * Exit a parse tree produced by {@link InfixParser#digit}.
	 * @param ctx the parse tree
	 */
	void exitDigit(InfixParser.DigitContext ctx);
}