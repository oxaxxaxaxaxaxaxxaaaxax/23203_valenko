package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

/**
 * Class for "-" command in brainfuck.
 * Decrement a value in the memory array.
 */
public class Decrement implements Command {
    /**
     * Decrement current node.
     */
    @Override
    public void execute(){
        Brainfuck.memory[Brainfuck.dataPointer]--;
    }
    //public Decrement(String ...args){}
}
