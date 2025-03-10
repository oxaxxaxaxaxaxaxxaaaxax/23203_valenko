package nsu.command;

import nsu.Context;
import nsu.Command;
import nsu.LoopError;

/**
 * Class for "]" command in brainfuck.
 * Handle brainfuck's cycle.
 */
public class EndLoop implements Command {
    public EndLoop(Context brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * End brainfuck's cycle.
     * @throws LoopError Error in cycle's stack
     */
    @Override
    public void execute() throws LoopError{
            brainfuck.EndLoop();
//        if(brainfuck.brackets.peek().equals("]") || brainfuck.brackets.empty()){
//            throw new LoopError("]");
//        }
//        if(brainfuck.memory[brainfuck.dataPointer] != 0){
//            //System.out.println(Brainfuck.commandPointer);
//            //Brainfuck.commandPointer = Brainfuck.symbolCounter;//&&&&&&&&&&&&&&&&&&&&&&&
//            if(brainfuck.commandPointer ==  brainfuck.pointers.peek() +1){
//                throw new LoopError("Wrong cycle");
//            }
//            brainfuck.commandPointer =  brainfuck.pointers.peek();
//        }
//        else{
//            brainfuck.brackets.push("]");
//            if(!brainfuck.brackets.pop().equals("]")){
//                throw new LoopError("]");
//            }
//            if(!brainfuck.brackets.pop().equals("[")){
//                throw new LoopError("]");
//            }
//            Integer prevPtr = brainfuck.pointers.pop();
//
//            //Brainfuck.symbolCounter = Brainfuck.pointers.peek();
//        }
    }
    Context brainfuck;
}
