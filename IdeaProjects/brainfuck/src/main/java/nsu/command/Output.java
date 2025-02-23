package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

public class Output implements Command {
    @Override
    public void execute(){
        System.out.print((char) Brainfuck.memory[Brainfuck.dataPointer]);
    }
    //public Output(String ...args){}
}
