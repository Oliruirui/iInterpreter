import Exceptions.IncompleteStringException;
import Exceptions.IndexParsingException;
import Exceptions.InvalidCharacterSyntaxException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexerImpl implements Lexer{
    @Override
    public List<Token> createToken(List<String> input)
            throws Exception {
        List<Token> res = new ArrayList<>();
        for (String cur : input){
            res.addAll(stringToTokens(cur));
        }
        return res;
    }

    public static final Map<String, TokenTypes> checkToken = new HashMap<>(){{
        /*
        ADD = "Add", SUBTRACT = "Sub",
            MULTIPLY = "Mul", DIVIDE = "Div",
            POWER = "Power", AND = "And",
            OR = "Or", PRINT = "Print",
            EQUALS = "=", LEFTPAREN = "(",
            COMMA = ",", RIGHTPAREN = ")";
         */
        put(Token.ADD, TokenTypes.OPERATOR);
        put(Token.SUBTRACT, TokenTypes.OPERATOR);
        put(Token.MULTIPLY, TokenTypes.OPERATOR);
        put(Token.DIVIDE, TokenTypes.OPERATOR);
        put(Token.POWER, TokenTypes.OPERATOR);
        put(Token.AND, TokenTypes.OPERATOR);
        put(Token.OR, TokenTypes.OPERATOR);
        put(Token.PRINT, TokenTypes.COMMAND);
        put(Token.EQUALS, TokenTypes.ASSIGNMENT);
        put(Token.LEFTPAREN, TokenTypes.PUNCTATION);
        put(Token.COMMA, TokenTypes.PUNCTATION);
        put(Token.RIGHTPAREN, TokenTypes.PUNCTATION);
    }};

    private List<Token> stringToTokens(String s) throws Exception{
        List<Token> res = new ArrayList<>();
        int start = 0;
        int end = 0;
        //Add (a b)
        //s
        //e

        //Invariant: Current string includes [s,e]
        //
        //    (
        //      if (s == e) -> stringToToken([s,e]), s++, e++;
        //      else if (s < e) -> stringToToken([s,e - 1]), s = e;
        //      else throw invalidCharacterSyntaxException(diff err message);

        //    _
        //      stringToToken([s,e - 1]), s = e + 1, e++;

        //    )
        //      if (s == e) -> stringToToken([s,e]), s++, e++;
        //      else if (s < e) -> stringToToken([s,e - 1]), s = e;
        //      else throw invalidCharacterSyntaxException(diff err message);

        //    abc"
        //       s
        //       e
        //    "
        //      if (s < e){
        //          stringToToken([s,e - 1]);
        //          s = e;
        //          continue;
        //      }
        //      while (e < s.length() && s.charAt(e) != '\"') e++
        //      if (e == s.length() && s.charAt(e - 1) != '\"')
        //          throw invalidCharacterSyntaxException(diff err message);
        //      else -> stringToToken([s,e]); s = e + 1; e++;
        //    else e++
        while(end < s.length()){
            char cur = s.charAt(end);
            //Add ("" b)
            if (cur == '(' || cur == ')'){
                if (start == end){
                    res.add(new Token(TokenTypes.PUNCTATION, "" + cur));
                    start = end + 1;
                    end = end + 1;
                }else if (start < end){
                    Token curToken = stringToToken(s.substring(start, end));
                    res.add(curToken);
                    start = end;
                }else{
                    throw new IndexParsingException("Parsing Error");
                }
            }else if (cur == ' '){
                if (start < end){
                    Token curToken = stringToToken(s.substring(start, end));
                    res.add(curToken);
                }else if (start > end){
                    throw new IndexParsingException("Parsing Error");
                }
                start = end + 1;
                end++;
            }else if (cur == '\"'){
                if (start < end){
                    Token curToken = stringToToken(s.substring(start, end));
                    res.add(curToken);
                    start = end;
                }else if (start == end){
                    end++;
                    while (end < s.length() && s.charAt(end) != '\"'){
                        end++;
                    }
                    if (end == s.length()){
                        throw new IncompleteStringException("quotation mark mismatch");
                    }

                    Token curToken = stringToToken(s.substring(start, end + 1));
                    res.add(curToken);
                    start = end + 1;
                    end++;
                }
            }else{
                end++;
            }

        }
        return res;
    }

    private Token stringToToken(String s) throws Exception{
        //
        //a
        if(s.charAt(0) == '\"'){
            if (s.equals("")){
                return new Token(TokenTypes.STRING, "");
            }
            String curTokenValue = s.substring(1,s.length() - 1);
            Token cur = new Token(TokenTypes.STRING, curTokenValue);
            return cur;
        }

        if(checkToken.containsKey(s)){
            TokenTypes curTokenType = checkToken.get(s);
            Token cur = new Token(curTokenType, s);
            return cur;
        }

        if(Character.isDigit(s.charAt(0))){
            Token cur = new Token(TokenTypes.NUMBERS, s);
            return cur;
        }

        if (Character.isAlphabetic(s.charAt(0))){
            Token cur = new Token(TokenTypes.VARIABLE, s);
            return cur;
        }

        throw new InvalidCharacterSyntaxException("Input contains invalid character");
    }
}
