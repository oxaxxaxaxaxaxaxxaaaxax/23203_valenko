package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

public class BackPointer implements Command {
    @Override
    public void execute() {
        Brainfuck.dataPointer--;
    }
    //public BackPointer(String ...args){}
}
