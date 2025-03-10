package nsu.command;

import nsu.Context;
import nsu.Command;

import java.io.PrintStream;

/**
 * Class for "." command in brainfuck.
 * Print a value to the console.
 */
public class Output implements Command {
    public Output(Context brainfuckCopy){
        //outputStream = System.out;
        brainfuck=brainfuckCopy;
    }
    /**
     * Print the current node.
     */
    @Override
    public void execute(){
        //System.out.print((char) brainfuck.memory[brainfuck.dataPointer]);
        brainfuck.Output();
    }
    Context brainfuck;
    //public PrintStream outputStream;
}
