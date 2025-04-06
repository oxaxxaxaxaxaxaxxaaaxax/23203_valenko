package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

import java.io.PrintStream;

/**
 * Class for "." command in brainfuck.
 * Print a value to the console.
 */
public class Output implements Command {
    public Output(Brainfuck brainfuckCopy){
        outputStream = System.out;
        brainfuck=brainfuckCopy;
    }
    /**
     * Print the current node.
     */
    @Override
    public void execute(){
        outputStream.print((char) brainfuck.memory[brainfuck.dataPointer]);
    }
    Brainfuck brainfuck;
    public PrintStream outputStream;
    //public Output(String ...args){}
}
