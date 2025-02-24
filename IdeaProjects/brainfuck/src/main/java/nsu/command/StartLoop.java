package nsu.command;

import nsu.Brainfuck;
import nsu.Command;
import nsu.StackError;

/**
 * Class for "[" command in brainfuck.
 * Handle brainfuck's cycle.
 */
public class StartLoop implements Command {
    /**
     * Begin brainfuck's cycle.
     * @throws StackError Error in cycle's stack
     */
    @Override
    public void execute() throws StackError{
        //Brainfuck.symbolCounter = Brainfuck.commandPointer;//на один меньше
        //System.out.println(Brainfuck.commandPointer);
        Brainfuck.brackets.push("[");
        Brainfuck.pointers.push(Brainfuck.commandPointer);
        if(Brainfuck.memory[Brainfuck.dataPointer] == 0) {
            while(Brainfuck.commands.toCharArray()[Brainfuck.commandPointer] != ']'){
                Brainfuck.commandPointer++;
            }
            Brainfuck.commandPointer++;
            if(!Brainfuck.brackets.pop().equals("[")){
                throw new StackError("[");
            }
            Integer prevPtr = Brainfuck.pointers.pop();
            if(Brainfuck.pointers.empty()){
                throw new StackError("Wrong cycle");
            }
            Brainfuck.symbolCounter = Brainfuck.pointers.peek();
        }
    }
    //symb counter не должна быть глобальной
    //public StartLoop(String inputData){}
    int startPointer = 0;
}
