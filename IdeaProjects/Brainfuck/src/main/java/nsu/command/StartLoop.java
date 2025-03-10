package nsu.command;

import nsu.Context;
import nsu.Command;
import nsu.LoopError;

/**
 * Class for "[" command in brainfuck.
 * Handle brainfuck's cycle.
 */
public class StartLoop implements Command {
    public StartLoop(Context brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Begin brainfuck's cycle.
     * @throws LoopError Error in cycle's stack
     */
    @Override
    public void execute() throws LoopError{
        brainfuck.StartLoop();
        //Brainfuck.symbolCounter = Brainfuck.commandPointer;//на один меньше
        //System.out.println(Brainfuck.commandPointer);
//        brainfuck.brackets.push("[");
//        brainfuck.pointers.push(brainfuck.commandPointer);
//        if(brainfuck.memory[brainfuck.dataPointer] == 0) {
//            while(brainfuck.commands.toCharArray()[brainfuck.commandPointer] != ']'){
//                brainfuck.commandPointer++;
//            }
//            brainfuck.commandPointer++;
//            if(!brainfuck.brackets.pop().equals("[")){
//                throw new LoopError("[");
//            }
//            Integer prevPtr = brainfuck.pointers.pop();
//            if(brainfuck.pointers.empty()){
//                throw new LoopError("Wrong cycle");
//            }
//            //brainfuck.symbolCounter = brainfuck.pointers.peek();
//        }
    }
    Context brainfuck;
    //symb counter не должна быть глобальной
    //public StartLoop(String inputData){}
    int startPointer = 0;
}

