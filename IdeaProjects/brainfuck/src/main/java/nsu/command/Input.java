package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

/**
 * Class for "," command in brainfuck.
 * Read and set a value in memory array from the console.
 */
public class Input implements Command {
    /**
     * Set input value in current node in memory array.
     */
    @Override
    public void execute(){
        Brainfuck.memory[Brainfuck.dataPointer] = (byte)Brainfuck.inputString.toCharArray()[counter];
        counter++;
    }
    static int counter=0;
}
