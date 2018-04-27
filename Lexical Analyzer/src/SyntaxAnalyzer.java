/*
COURSE: COSC455001
SUBMITTER: tkasah1
NAMES: Tewodros,Kasahun
*/
/**
 * COSC 455 Programming Languages: Implementation and Design.
 *
 * A Simple Lexical Analyzer Adapted from Sebesta (2010) by Josh Dehlinger
 * further modified by Adam Conover (2012-2013)
 *
 * This syntax analyzer implements a top-down, left-to-right, recursive-descent
 * parser based on the production rules for the simple English language provided
 * by Weber in Section 2.2. Helper methods to get, set and reset the error flag.
 * 
 * @author Tewodros Kasahun (Implemented the  BNF rule)
 */
public class SyntaxAnalyzer {

	private LexicalAnalyzer lexer; // The lexer which will provide the tokens
	TOKEN token;

	/**
	 * The constructor initializes the terminal literals in their vectors.
	 */
	public SyntaxAnalyzer(LexicalAnalyzer lexer) {
		this.lexer = lexer;
	}

	/**
	 * Begin analyzing...
	 */
	public void analyze() throws ParseException {
		parseSentance(0);
	}

	// This method implements the BNF rule for a sentence from Section 2.2.
	// <S> ::= <NP> <V> <NP>
	protected void parseSentance(int treeDepth) throws ParseException {
		log("<S>", treeDepth++);

		NounPhrase(treeDepth);
		VP(treeDepth);
		NounPhrase(treeDepth);
		pp(treeDepth);
		sentanceTail(treeDepth);
	}

	protected void pp(int treeDepth) throws ParseException {
		log("<PP>", treeDepth++);

		if (lexer.curToken == token.PREPOSITION) {
			prep(treeDepth);
			NounPhrase(treeDepth);
		} else if (lexer.curToken == token.CONJUNCTION) {
			sentanceTail(treeDepth);
		} else {

		}

	}

	protected void prep(int treeDepth) throws ParseException {
		log("<prep> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.PREPOSITION != lexer.curToken) {
			String msg = "A prep was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	// <SENTENCE_TAIL> ::= <CONJ> <SENTENCE> | <EOS>
	protected void sentanceTail(int treeDepth) throws ParseException {
		log("<SENTENCE_TAIL>", treeDepth++);
		if (lexer.curToken == token.CONJUNCTION) {
			CONJ(treeDepth);
			parseSentance(treeDepth);
		} else {
			EOS(treeDepth);
		
		}
	}

	protected void EOS(int treeDepth) throws ParseException {
		log("<EOS> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.EOS != lexer.curToken) {
			String msg = "A EOS was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}
		return;
		
	}

	protected void CONJ(int treeDepth) throws ParseException {
		log("<CONJ> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.CONJUNCTION != lexer.curToken) {
			String msg = "A conjunction was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	// This method implements the BNF rule for a noun phrase from Section 2.2.
	// <NP> ::= <ART> <ADJ_LIST> <NOUN>|
	protected void NounPhrase(int treeDepth) throws ParseException {
		log("<NP>", treeDepth++);
		if (lexer.curToken == token.NOUN) {
			Noun(treeDepth);
		} else {
			Article(treeDepth);
			adjlist(treeDepth);
			Noun(treeDepth);
		}

	}

	// <ADJ_LIST> ::= <ADJ> <ADJ_TAIL>
	protected void adjlist(int treeDepth) throws ParseException {
		log("<ADJ_List>", treeDepth++);

		if (lexer.curToken == token.NOUN) {

		} else {
			Adj(treeDepth);
			ADJ_TAIL(treeDepth);
		}

	}

	// <ADJ_TAIL> ::= <COMMA> <ADJ_LIST>| <<EMPTY>>
	protected void ADJ_TAIL(int treeDepth) throws ParseException {
		log("<ADJ_TAIL>", treeDepth++);
		if (lexer.curToken == token.COMMA) {

			COMMA(treeDepth);
			adjlist(treeDepth);
		} else {

		}

	}

	// <VP> ::= <ADV> <VERB> | <VERB>
	protected void VP(int treeDepth) throws ParseException {
		log("<VP>", treeDepth++);

		if (lexer.curToken == token.ADVERBS) {
			ADV(treeDepth);
			Verb(treeDepth);
		} else {
			Verb(treeDepth);
		}

	}

	protected void ADV(int treeDepth) throws ParseException {
		log("<ADV> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.ADVERBS != lexer.curToken) {
			String msg = "A adverbs was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	protected void COMMA(int treeDepth) throws ParseException {
		log("<COMMA> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.COMMA != lexer.curToken) {
			String msg = "A Comma was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	protected void Adj(int treeDepth) throws ParseException {
		log("<Adj> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.ADJECTIVE != lexer.curToken) {
			String msg = "A adj was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}
	// vp

	// This method implements the BNF rule for a verb from Section 2.2.
	// <V> ::= loves | hates | eats
	protected void Verb(int treeDepth) throws ParseException {
		log("<V> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.VERB != lexer.curToken) {
			String msg = "A verb was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	// This method implements the BNF rule for a noun from Section 2.2.
	// <N> ::= dog | cat | rat
	protected void Noun(int treeDepth) throws ParseException {
		log("<N> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.NOUN != lexer.curToken) {
			String msg = "A noun was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	// This method implements the BNF rule for an article from Section 2.2.
	// <A> ::= a | the
	protected void Article(int treeDepth) throws ParseException {
		log("<A> = " + lexer.lexemeBuffer, treeDepth);

		if (TOKEN.ARTICLE != lexer.curToken) {
			String msg = "An article was expected when '" + lexer.lexemeBuffer + "' was found.";
			throw new ParseException(msg);
		}

		lexer.parseNextToken();
	}

	private void log(String msg, int treeDepth) {
		for (int i = 0; i < treeDepth; i++) {
			System.out.print("  ");
		}
		System.out.println(msg);
	}
}