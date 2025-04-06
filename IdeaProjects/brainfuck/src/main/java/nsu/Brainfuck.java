package nsu;
import java.util.Stack;

/**
 * Utility-class for brainfuck's functional.
 */
public class Brainfuck {
    public void setCommandSequence(String sequence){
        commands = sequence;
    }
    public int symbolCounter=0;
    public int dataPointer =0;
    public byte memory[] = new byte[30000];
    public String commands;
    public int commandPointer=0;
    public Stack<String> brackets = new Stack<String>();
    public Stack<Integer> pointers = new Stack<Integer>();
    public String inputString;
}
