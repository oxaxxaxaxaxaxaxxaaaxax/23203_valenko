package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

public class Decrement implements Command {
    @Override
    public void execute(){
        Brainfuck.memory[Brainfuck.dataPointer]--;
    }
    //public Decrement(String ...args){}
}
