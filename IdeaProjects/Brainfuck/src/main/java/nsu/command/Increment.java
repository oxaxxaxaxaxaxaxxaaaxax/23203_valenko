package nsu.command;

import nsu.Command;
import nsu.Context;

/**
 * Class for "+" command in brainfuck.
 * Increment a value in the memory array.
 */
public class Increment implements Command{
    public Increment(Context brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Increment current node.
     */
    @Override
    public void execute(){
        brainfuck.IncrMemoryCell();
        //brainfuck.memory[brainfuck.dataPointer]++;
    }
    Context brainfuck;
}

