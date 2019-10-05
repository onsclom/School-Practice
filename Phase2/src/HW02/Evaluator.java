public class Evaluator
{
    public static boolean isOperator(char ch)
    {
        return (ch == '+' || ch == '-' 
        || ch == '*'  || ch == '/' || ch == '^');
    }

    public static boolean isOperand(char ch)
    {
        return ( (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') );
    }

    public static int getWeight(char ch)
    {
        if (ch == '^')
        {
            return 3;
        }
        if (ch == '*' || ch == '/')
        {
            return 2;
        }
        if (ch == '+' || ch == '-')
        {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) 
    {
        for(int i = 0; i < args.length; ++i) 
        {
            char[] myStack = new char[args[i].length()];
            int size = 0;
            String output = "";
            String infix = args[i].replace("\"", "");
            for(int j = 0; j < infix.length(); ++j) 
            {
                char ch = infix.charAt(j);

                if (isOperand(ch))
                {
                    output += ch;
                }
                else if (ch == '(')
                {
                    //push
                    myStack[size]=ch;
                    size++;
                }
                else if (isOperator(ch))
                {
                    while (size != 0 && myStack[size-1] != '(' && getWeight(myStack[size-1]) >= getWeight(ch))
                    {
                        //pop
                        size--;
                        output += myStack[size];
                    } 
                    
                    //push
                    myStack[size]=ch;
                    size++;
                }
                else if (ch == ')')
                {
                    while (myStack[size-1] != '(')
                    {
                        //pop
                        size--;
                        output += myStack[size];
                    }
                    //pop (
                    size--;
                }
            }
            //pop remaining operators off the stack
            while (size>0)
            {
                //pop
                size--;
                output += myStack[size];
            }

            // Your code here
            output = output.replace("", " ").trim();
            System.out.println(infix + " -> " + output);
        }
    }
}

/*
SAMPLE INPUT:
 "2 * (3 + 3) /4" "6 - (3 * 5 + 6) * (9 - 6 / 2)" "a / (b + c) - d" "(a + b) * (c ^ (d-e) + p) -g" "2 + 3 * 4" "a * b+ 5" "(1 + 2) * 7" "a * b / c" "(a / (b - c + d)) * (e - a) * c" "a / b - c + d * e - a * c"
*/