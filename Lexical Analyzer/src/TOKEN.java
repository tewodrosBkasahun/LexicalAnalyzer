import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
COURSE: COSC455001
SUBMITTER: tkasah1
NAMES: Tewodros,Kasahun
*/

/**
 * The Token set as an Enum type. This is just "binds" each token to its list of
 * valid lexemes.
 *
 * @author Adam J. Conover
 */
public enum TOKEN {

    ARTICLE("a", "the"), // a list of articles
    NOUN("dog", "cat", "rat","house","tree"), // a list of nouns
    VERB("loves", "hates", "eats","chased","stalks","chases"), // a list of verbs
    ADJECTIVE("furry", "fast", "lazy","sneaky","slow"), // a list of Ajectives
    ADVERBS("quickly", "silently"), // a list of Adverbs
    CONJUNCTION("and", "of"),//list of conjunction
    PREPOSITION("with", "around","up"),//list of preposition
  
    COMMA(","),
    EOS(".","!"),
    EMPTY(" "),
    
    UNKNOWN(); // keep our lexemes "type-safe"!
	
    //
    // The lexemes under this token
    private List<String> lexemeList;

    // Construct the token with the list of lexems
    private TOKEN(String... tokenStrings) {
        lexemeList = new ArrayList<>(tokenStrings.length);
        lexemeList.addAll(Arrays.asList(tokenStrings));
    }

    // Gets a token from a lexeme
    public static TOKEN fromLexeme(String str) {
        // Search through the lexemes looking for a match.
        for (TOKEN t : TOKEN.values()) {
            if (t.lexemeList.contains(str)) {
                return t;
            }
        }

        // If nothing matches then return UNKNOWN.
        return UNKNOWN;
    }
}