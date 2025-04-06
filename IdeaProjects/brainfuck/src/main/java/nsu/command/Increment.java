package nsu.command;

import nsu.Command;
import nsu.Brainfuck;

/**
 * Class for "+" command in brainfuck.
 * Increment a value in the memory array.
 */
public class Increment implements Command{
    public Increment(Brainfuck brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Increment current node.
     */
    @Override
    public void execute(){
        brainfuck.memory[brainfuck.dataPointer]++;
    }
    Brainfuck brainfuck;
}
