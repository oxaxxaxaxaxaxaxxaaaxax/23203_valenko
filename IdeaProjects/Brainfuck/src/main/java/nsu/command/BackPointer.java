package nsu.command;

import nsu.Context;
import nsu.Command;


/**
 * Class for "<" command in brainfuck.
 * Moves the data pointer one step backwards in the memory array.
 */
public class BackPointer implements Command {
    public BackPointer(Context brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Decrement the data pointer.
     * @throws Exception Wrong pointer's value
     */
    @Override
    public void execute() throws Exception{
//        if(brainfuck.dataPointer == 0){
//            throw new Exception("Error in brainfuck's code");
//        }
//        brainfuck.dataPointer--;
        brainfuck.BackPtr();
    }
    Context brainfuck;
}

