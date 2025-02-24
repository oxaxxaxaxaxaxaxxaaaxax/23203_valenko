package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

import java.io.PrintStream;

/**
 * Class for "." command in brainfuck.
 * Print a value to the console.
 */
public class Output implements Command {
    public Output(){
        outputStream = System.out;
    }
    /**
     * Print the current node.
     */
    @Override
    public void execute(){
        outputStream.print((char) Brainfuck.memory[Brainfuck.dataPointer]);
    }

    private PrintStream outputStream;
    //public Output(String ...args){}
}
