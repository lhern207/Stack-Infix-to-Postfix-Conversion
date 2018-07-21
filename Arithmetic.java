/*
 * Implementation of infix to postfix conversion using a stack
 */

/**
 * @author Lester
 */
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.Scanner;

class Arithmetic {

    private static final char LEFT_NORMAL = '(';
    private static final char RIGHT_NORMAL = ')';

    private Stack stk;
    private String expression,
            postfix;
    private int length;

    Arithmetic(String s) {
        expression = s;
        postfix = "";
        length = expression.length();
        stk = new Stack();
    }

    // Validate the expression - make sure parentheses are balanced
    public boolean isBalance() {
        int index = 0;
        boolean fail = false;

        try {
            while (index < length && !fail) {
                char ch = expression.charAt(index);

                switch (ch) {
                    case LEFT_NORMAL:
                        stk.push(new Character(ch));
                        break;
                    case RIGHT_NORMAL:
                        stk.pop();
                        break;
                    default:

                        break;
                }//end of swtich
                index++;
            }//end of while
        }//end of try
        catch (EmptyStackException e) {
            System.out.println(e.toString());
            fail = true;
        }
        if (stk.empty() && !fail) {
            return true;
        } else {
            return false;
        }

    }

    // Convert expression to postfix notation
    public void postfixExpression() {
        stk.clear(); // Re-using the stack object
        Scanner scan = new Scanner(expression);
        char current;
        // The algorithm for doing the conversion.... Follow the bullets
        while (scan.hasNext()) {
            String token = scan.next();

            if (isNumber(token)) // Bullet # 1
            {
                postfix = postfix + token + " ";
            } else {
                current = token.charAt(0);

                if (isParentheses(current)) // Bullet # 2 begins
                {
                    if (current == LEFT_NORMAL) {
                        stk.push(current);

                    } else if (current == RIGHT_NORMAL) {
                        try {
                            /* Some details ... whatever is popped from the
                             * stack is an object, hence you must cast this
                             * object to its proper type, then extract its
                             * primitive data (type) value.
                             */
                            Character ch = (Character) stk.pop();
                            char top = ch.charValue();

                            while (top != LEFT_NORMAL) {

                                /* Append token popped onto the output string
                                 * Place at least one blank space between 
                                 * each token
                                 */
                                postfix = postfix + top + " ";
                                ch = (Character) stk.pop();
                                top = ch.charValue();

                            }
                        } catch (EmptyStackException e) {

                        }
                    }
                }// Bullet # 2 ends
                else if (isOperator(current))// Bullet # 3 begins
                {
                    if (stk.empty()) {
                        stk.push(new Character(current));
                    } else {
                        try {
                            // Remember the method peek simply looks at the top
                            // element on the stack, but does not remove it.

                            char top = (Character) stk.peek();
                            boolean higher = hasHigherPrecedence(top, current);

                            while (top != LEFT_NORMAL && higher) {
                                postfix = postfix + stk.pop() + " ";
                                top = (Character) stk.peek();
                                higher = hasHigherPrecedence(top, current);
                            }
                            stk.push(new Character(current));
                        } catch (EmptyStackException e) {
                            stk.push(new Character(current));
                        }
                    }
                }// Bullet # 3 ends
            }
        } // Outer loop ends

        try {
            while (!stk.empty()) // Bullet # 4
            {
                postfix = postfix + stk.pop() + " ";
            }
        } catch (EmptyStackException e) {

        }
    }

    public String evaluateRPN() {
        stk.clear();
        Scanner scan = new Scanner(postfix);
        char current;

        while (scan.hasNext()) {
            try {
                String currentSymbol = scan.next();

                if (currentSymbol.equals("+")) {
                    stk.push((Integer) stk.pop() + (Integer) stk.pop());
                } else if (currentSymbol.equals("-")) {
                    Integer arg2 = (Integer) stk.pop();
                    stk.push((Integer) stk.pop() - arg2);
                } else if (currentSymbol.equals("*")) {
                    stk.push((Integer) stk.pop() * (Integer) stk.pop());
                } else if (currentSymbol.equals("/")) {
                    Integer arg2 = (Integer) stk.pop();
                    stk.push((Integer) stk.pop() / arg2);
                } else if (currentSymbol.equals("%")) {
                    Integer arg2 = (Integer) stk.pop();
                    stk.push((Integer) stk.pop() % arg2);
                } else {
                    // Not an operator--push the input value 
                    stk.push(Integer.parseInt(currentSymbol));
                }
            }catch (EmptyStackException e){ 
                System.out.println("Original expression is malformed. "
                        + "Make sure negative numbers are expressed correctly "
                        + "(e.g. '-3' instead of '- 3'). \nAlso check that the "
                        + "number and/or position of Arithmetic Operators is correct.");
                return "NA";
                
            }
        }
        return "" + ((Integer) stk.pop());
    }

    

    

    private boolean isNumber(String str) {
        char[] ch = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);

            if (!Character.isDigit(ch[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isParentheses(char ch) {
        if (ch == '(' || ch == ')') {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOperator(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%') {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasHigherPrecedence(char top, char current) {
        if ((top == '*' || top == '/' || top == '%') && (current == '+' || current == '-')) {
            return true;
        } else {
            //Every other combination results in equal or lower precedence
            return false;
        }
    }

    public String getPostfix() {
        return postfix;
    }
}
