package nsu.command;

import nsu.Brainfuck;
import nsu.Command;

public class Input implements Command {
    @Override
    public void execute(){
        Brainfuck.memory[Brainfuck.dataPointer] = (byte)data.toCharArray()[counter];
        counter++;
    }
    public Input(String ...args){ //тут не переменное колво
        data = args[0];
    }
    String data;
    static int counter=0;
}
