package nsu;
import java.util.Stack;
import java.lang.Exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Utility-class for brainfuck's functional.
 */
public class Brainfuck {
    public void setCommandSequence(String sequence){
        commands = sequence;
    }
    private int dataPointer =0;
    private byte memory[] = new byte[30000];
    public String commands;
    public int commandPointer=0;
    private Stack<Brackets> brackets = new Stack<Brackets>();
    private Stack<Integer> pointers = new Stack<Integer>();
    public String inputString;
    private int inputStringCounter = 0;
    private Logger logger = LogManager.getLogger(Brainfuck.class);

    public enum Brackets{
        OPEN,
        CLOSE
    }

    public void IncrMemoryCell(){
        logger.trace("Start Incr function");
        memory[dataPointer]++;
    }
    public void DecrMemoryCell(){
        logger.trace("Start Decr function");
        memory[dataPointer]--;
    }
    public void ForwardPtr(){
        logger.trace("Start ForwardPtr function");
        dataPointer++;
    }
    public void BackPtr()throws Exception{
        logger.trace("Start BackPtr function");
        if(dataPointer == 0){
            throw new Exception("Error in brainfuck's code");
        }
        dataPointer--;
    }
    public void Input(){
        logger.trace("Start Input function");
        memory[dataPointer] = (byte)inputString.toCharArray()[inputStringCounter];
        inputStringCounter++;
    }
    public void Output(){
        logger.trace("Start Output function");
        logger.trace("Current output symbol is: ");
        System.out.print((char) memory[dataPointer]);
    }
    public void StartLoop()throws LoopError{
        logger.trace("Start StartLoop function");
        brackets.push(Brackets.OPEN);
        pointers.push(commandPointer);
        if(memory[dataPointer] == 0) {
            while(commands.toCharArray()[commandPointer] != ']'){
                commandPointer++;
            }
            commandPointer++;
            if(!brackets.pop().equals(Brackets.OPEN)){
                throw new LoopError("Open Bracket");
            }//throws нужно прописать тут или в брейнфаке в объявлении
            Integer prevPtr = pointers.pop();
            if(pointers.empty()){
                throw new LoopError("Wrong cycle");
            }
            //brainfuck.symbolCounter = brainfuck.pointers.peek();
        }
    }
    public void EndLoop(){
        logger.trace("Start EndLoop function");
        if(brackets.peek().equals(Brackets.CLOSE) || brackets.empty()){
            throw new LoopError("Close Bracket");
        }
        if(memory[dataPointer] != 0){
            //System.out.println(Brainfuck.commandPointer);
            //Brainfuck.commandPointer = Brainfuck.symbolCounter;//&&&&&&&&&&&&&&&&&&&&&&&
            if(commandPointer ==  pointers.peek() +1){
                throw new LoopError("Wrong cycle");
            }
            commandPointer =  pointers.peek();
        }
        else{
            brackets.push(Brackets.CLOSE);
            if(!brackets.pop().equals(Brackets.CLOSE)){
                throw new LoopError("Close Bracket");
            }
            if(!brackets.pop().equals(Brackets.OPEN)){
                throw new LoopError("Close Bracket");
            }
            Integer prevPtr = pointers.pop();

            //Brainfuck.symbolCounter = Brainfuck.pointers.peek();
        }
    }

    public byte getCellValue(int idx){
        return memory[idx];
    }

    public void printCell(){
        System.out.println(memory[0]);
    }

}

//возвращать??????