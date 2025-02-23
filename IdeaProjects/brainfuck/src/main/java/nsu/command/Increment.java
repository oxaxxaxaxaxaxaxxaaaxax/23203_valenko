package nsu.command;

import nsu.Command;
import nsu.Brainfuck;

public class Increment implements Command{
    @Override
    public void execute(){
        Brainfuck.memory[Brainfuck.dataPointer]++;
    }
    //public Increment(String ...args){}


}
