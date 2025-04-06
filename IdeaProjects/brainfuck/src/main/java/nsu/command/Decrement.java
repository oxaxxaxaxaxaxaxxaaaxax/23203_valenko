package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

/**
 * Class for "-" command in brainfuck.
 * Decrement a value in the memory array.
 */
public class Decrement implements Command {
    public Decrement(Brainfuck brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Decrement current node.
     */
    @Override
    public void execute(){
        brainfuck.memory[brainfuck.dataPointer]--;
    }
    Brainfuck brainfuck;
    //public Decrement(String ...args){}
}
