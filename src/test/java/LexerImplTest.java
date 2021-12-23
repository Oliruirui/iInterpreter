import Exceptions.IncompleteStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class LexerImplTest {

    Lexer tester;
    List<String> testInput;
    List<String> testInput2;
    List<Token> ans;
    List<Token> ans2;
    @BeforeEach
    void setUp() {
        tester = new LexerImpl();
        testInput = new ArrayList<>(Arrays.asList("Add (a b)"));
        testInput2 = new ArrayList<>(Arrays.asList("Add (\"a  \" \"b\")"));
        ans = getAnsOne();
        ans2 = getAnsTwo();
    }

    public static List<Token> getAnsOne() {
        List<Token> ans = new ArrayList<>();
        ans.add(new Token(TokenTypes.OPERATOR, Token.ADD));
        ans.add(new Token(TokenTypes.PUNCTATION, Token.LEFTPAREN));
        ans.add(new Token(TokenTypes.VARIABLE, "a"));
        ans.add(new Token(TokenTypes.VARIABLE, "b"));
        ans.add(new Token(TokenTypes.PUNCTATION, Token.RIGHTPAREN));
        return ans;
    }

    public static List<Token> getAnsTwo() {
        List<Token> ans = new ArrayList<>();
        ans.add(new Token(TokenTypes.OPERATOR, Token.ADD));
        ans.add(new Token(TokenTypes.PUNCTATION, Token.LEFTPAREN));
        ans.add(new Token(TokenTypes.STRING, "a  "));
        ans.add(new Token(TokenTypes.STRING, "b"));
        ans.add(new Token(TokenTypes.PUNCTATION, Token.RIGHTPAREN));
        return ans;
    }



    @Test
    @DisplayName("Lexer test - variable")
    //Add(a b)
    //O(add), LP((), N(a), N(b), RP())
    void variableTest() throws Exception {
        assertEquals(ans, tester.createToken(testInput),
                "Basic Add test failed");
    }

    @Test
    @DisplayName("Lexer test - string")
    /*
    Add(“a  ” “b”)
    O(add), LP((), String(a  ), String(b), RP())
    */
    void stringTest() throws Exception {
        assertEquals(ans2, tester.createToken(testInput2),
                "string test failed");
    }


    @Test
    @DisplayName("Lexer test - incomplete string")
    void stringIncompleteTest() {
        List<String> testInput3  = new ArrayList<>(Arrays.asList("\"a"));
        Throwable exception = assertThrows(IncompleteStringException.class,
                () -> tester.createToken(testInput3),
                "Expect incompleteStringException but failed");
        assertEquals("quotation mark mismatch", exception.getMessage(),
                "exception found but not incompleteStringException");
    }
     /*
    “a
    Exception: incompleteStringException

     */
}