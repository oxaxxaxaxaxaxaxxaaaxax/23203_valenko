package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

public class ForwardPointer implements Command {
    @Override
    public void execute(){
        Brainfuck.dataPointer++;
    }
    //public ForwardPointer(String ...args){}
}
