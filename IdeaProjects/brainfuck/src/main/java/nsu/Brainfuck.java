package nsu;
import java.util.Stack;

/**
 * Utility-class for brainfuck's functional.
 */
public class Brainfuck {
    public static void setCommandSequence(String sequence){
        commands = sequence;
    }
    public static int symbolCounter=0;
    public static int dataPointer =0;
    public static byte memory[] = new byte[30000];
    public static String commands;
    public static int commandPointer=0;
    public static Stack<String> brackets = new Stack<String>();
    public static Stack<Integer> pointers = new Stack<Integer>();
    public static String inputString;
}
