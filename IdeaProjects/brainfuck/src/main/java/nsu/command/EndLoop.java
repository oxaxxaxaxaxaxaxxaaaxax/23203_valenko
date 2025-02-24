package nsu.command;

import nsu.Brainfuck;
import nsu.Command;
import nsu.StackError;

/**
 * Class for "]" command in brainfuck.
 * Handle brainfuck's cycle.
 */
public class EndLoop implements Command {
    /**
     * End brainfuck's cycle.
     * @throws StackError Error in cycle's stack
     */
    @Override
    public void execute() throws StackError{
        if(Brainfuck.brackets.peek().equals("]") || Brainfuck.brackets.empty()){
            throw new StackError("]");
        }
        if(Brainfuck.memory[Brainfuck.dataPointer] != 0){
            //System.out.println(Brainfuck.commandPointer);
            //Brainfuck.commandPointer = Brainfuck.symbolCounter;//&&&&&&&&&&&&&&&&&&&&&&&
            if(Brainfuck.commandPointer ==  Brainfuck.pointers.peek() +1){
                throw new StackError("Wrong cycle");
            }
            Brainfuck.commandPointer =  Brainfuck.pointers.peek();
        }
        else{
            Brainfuck.brackets.push("]");
            if(!Brainfuck.brackets.pop().equals("]")){
                throw new StackError("]");
            }
            if(!Brainfuck.brackets.pop().equals("[")){
                throw new StackError("]");
            }
            Integer prevPtr = Brainfuck.pointers.pop();

            //Brainfuck.symbolCounter = Brainfuck.pointers.peek();
        }
    }
    //public EndLoop(String ...args){}
}
