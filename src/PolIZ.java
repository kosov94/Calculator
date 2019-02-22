import java.util.Stack;

public class PolIZ {
    public static double Calculate(String input) {
        String piz = Convert(input);
        double resulte = Solve(piz);
        return resulte;
    }


    static private String Convert(String input) {
        String output = "";
        Stack operatorStack = new Stack();

        for (int i = 0; i < input.length(); i++) {

            if (IsSpace(input.charAt(i)))
                continue;

            if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
                while (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
                    output += input.charAt(i);
                    i++;

                    if (i == input.length()) break;
                }
                output += ' ';
                i--;
            }

            if (IsOperator(input.charAt(i))) {
                if (i == 0&&"(".indexOf(input.charAt(i))==-1) {
                    if ("-".indexOf(input.charAt(i)) != -1)
                        output += '-';
                } else if (("-".indexOf(input.charAt(i)) != -1) && IsOperator(input.charAt(i - 1)))
                    output += '-';
                else {
                    if (input.charAt(i) == '(')
                        operatorStack.push(input.charAt(i));
                    else if (input.charAt(i) == ')') {
                        char ch = (char) operatorStack.pop();

                        while (ch != '(') {
                            output += ch;
                            output += ' ';
                            ch = (char) operatorStack.pop();
                        }
                    } else {
                        if (!operatorStack.empty())
                            if (GetPriorrity(input.charAt(i)) <= GetPriorrity((char) operatorStack.peek())) {
                                output += (char) operatorStack.pop();
                                output += " ";
                            }

                        operatorStack.push(input.charAt(i));
                    }
                }
            }
        }

        while (!operatorStack.empty()) {
            output += (char) operatorStack.pop();
            output += ' ';
        }

        return output;
    }

    static private double Solve(String input) {
        double result = 0;
        Stack buff = new Stack();

        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.' ||
                    ("-".indexOf(input.charAt(i)) != -1 && Character.isDigit(input.charAt(i + 1)))) {
                String digit = "";
                if ("-".indexOf(input.charAt(i)) != -1 && Character.isDigit(input.charAt(i + 1))) {
                    digit += '-';
                    i++;
                }
                while (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
                    digit += input.charAt(i);
                    i++;
                    if (i == input.length()) break;
                }

                buff.push(digit);
                i--;
            } else if (IsOperator(input.charAt(i))) {
                double digit1 = Double.parseDouble(buff.pop().toString());
                double digit2 = Double.parseDouble(buff.pop().toString());

                switch (input.charAt(i)) {
                    case '+':
                        result = digit1 + digit2;
                        break;
                    case '-':
                        result = digit2 - digit1;
                        break;
                    case '*':
                        result = digit2 * digit1;
                        break;
                    case '/':
                       // if(digit1!=0)
                        result = digit2 / digit1;
                        break;
                }
                buff.push(result);
            }
        }

        return (double) buff.peek();
    }

    static public boolean IsOperator(char ch) {
        if ("+-*/()".indexOf(ch) != -1)
            return true;
        return false;
    }

    static public int GetPriorrity(char ch) {
        switch (ch) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 4;
            default:
                return 5;
        }

    }

    static public boolean IsSpace(char ch) {
        if (ch == ' ')
            return true;
        return false;
    }
}