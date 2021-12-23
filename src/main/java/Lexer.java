import Exceptions.IncompleteStringException;

import java.util.List;


public interface Lexer {
    public List<Token> createToken(List<String> input)
            throws Exception;
}
