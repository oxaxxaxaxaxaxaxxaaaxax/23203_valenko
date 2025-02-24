package nsu.command;

import nsu.Command;
import nsu.Brainfuck;

/**
 * Class for "+" command in brainfuck.
 * Increment a value in the memory array.
 */
public class Increment implements Command{
    /**
     * Increment current node.
     */
    @Override
    public void execute(){
        Brainfuck.memory[Brainfuck.dataPointer]++;
    }
    //public Increment(String ...args){}


}
