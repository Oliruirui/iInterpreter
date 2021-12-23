import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

public class Token {
    /*OPERATOR, // Add, Sub,Mul,Div,Pow,And,Or
    COMMAND, // Print
    ASSIGNMENT, // =
    PUNCTATION, // ( , )
    NUMBERS, // 0~9
    STRING,*/

    public static final String ADD = "Add", SUBTRACT = "Sub",
            MULTIPLY = "Mul", DIVIDE = "Div",
            POWER = "Power", AND = "And",
            OR = "Or", PRINT = "Print",
            EQUALS = "=", LEFTPAREN = "(",
            COMMA = ",", RIGHTPAREN = ")";

    /*public static final List<String> tokenTypes = new ArrayList<>(
            Arrays.asList(Token.ADD, Token.SUBTRACT, Token.MULTIPLY, Token.DIVIDE,
                    Token.POWER, Token.AND, Token.OR, Token.PRINT,
                    Token.EQUALS, Token.LEFTPAREN, Token.COMMA, Token.RIGHTPAREN));*/

    TokenTypes type;
    String value;
    Token(TokenTypes type, String value){
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
