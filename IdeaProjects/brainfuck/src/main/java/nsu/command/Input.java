package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

/**
 * Class for "," command in brainfuck.
 * Read and set a value in memory array from the console.
 */
public class Input implements Command {
    public Input(Brainfuck brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Set input value in current node in memory array.
     */
    @Override
    public void execute(){
        brainfuck.memory[brainfuck.dataPointer] = (byte)brainfuck.inputString.toCharArray()[counter];
        counter++;
    }
    Brainfuck brainfuck;
    static int counter=0;
}
