package nsu.command;

import nsu.Context;
import nsu.Command;

/**
 * Class for ">" command in Context.
 * Moves the data pointer one step forwards in the memory array.
 */
public class ForwardPointer implements Command {
    public ForwardPointer(Context brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Increment the data pointer.
     */
    @Override
    public void execute(){
        brainfuck.ForwardPtr();
        //brainfuck.dataPointer++;
    }
    Context brainfuck;
}
