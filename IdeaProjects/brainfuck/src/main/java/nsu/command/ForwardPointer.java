package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

/**
 * Class for ">" command in brainfuck.
 * Moves the data pointer one step forwards in the memory array.
 */
public class ForwardPointer implements Command {
    /**
     * Increment the data pointer.
     */
    @Override
    public void execute(){
        Brainfuck.dataPointer++;
    }
    //public ForwardPointer(String ...args){}
}
