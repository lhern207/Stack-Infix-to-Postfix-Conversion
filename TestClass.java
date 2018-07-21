/*
 * Test class
 */

/**
 * @author Lester
 */
public class TestClass {

    public static void main(String[] arg) {
        String s[] = {"5 + ) * ( 2",
            " 2 + ( - 3 * 5 ) ",
            "(( 2 + 3 ) * 5 ) * 8 ",
            "5 * 10 + ( 15 - 20 ) )  - 25",
            " 5 + ( 5 *  10 + ( 15 - 20 )  - 25 ) * 9"
        };
        
        for (int i = 0; i < s.length; i++) {
            Arithmetic a = new Arithmetic(s[i]);
            if (a.isBalance()) {
                System.out.println("Expression " + s[i] + " is balanced");
                a.postfixExpression();
                System.out.println("The post fixed expression is: " + a.getPostfix());
                System.out.println("The final value of the expression is: " + a.evaluateRPN()+ "\n");
            } else {
                System.out.println("Expression " + s[i] + " is not balanced\n");
            }
        }
    }

}
