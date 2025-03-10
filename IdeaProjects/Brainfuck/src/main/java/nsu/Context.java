package nsu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.lang.Exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Utility-class for brainfuck's functional.
 */
public class Context {
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
    private Logger logger = LogManager.getLogger(Context.class);

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
    public void Input() {
        logger.trace("Start Input function");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            String input = reader.readLine();
            System.out.print(input);
            setInputString(String.valueOf(input.charAt(0)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//
        memory[dataPointer] = (byte)inputString.toCharArray()[0];
        System.out.print((char) memory[dataPointer]);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            System.out.print("Введите строку: ");
//            String line = reader.readLine(); // Считываем строку
//            if (line != null) {
//                System.out.println("Вы ввели: " + line);
//            } else {
//                System.out.println("Конец ввода.");
//            }
//        } catch (Exception e) {
//            System.out.println("Ошибка чтения: " + e.getMessage());
//        }
        //inputStringCounter++;
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
                logger.error("pop open");
                throw new LoopError("Open Bracket");
            }//throws нужно прописать тут или в брейнфаке в объявлении
            Integer prevPtr = pointers.pop();
            if(pointers.empty()){
                throw new LoopError("Wrong cycle");
            }
            //brainfuck.symbolCounter = brainfuck.pointers.peek();
        }
    }
    public void EndLoop()throws LoopError{
        logger.trace("Start EndLoop function");
        if(brackets.empty()){
            logger.error("Double close bracket");
            throw new LoopError("Close Bracket");
        }
        if(memory[dataPointer] != 0){
            //System.out.println(Brainfuck.commandPointer);
            //Brainfuck.commandPointer = Brainfuck.symbolCounter;//&&&&&&&&&&&&&&&&&&&&&&&
            if(commandPointer ==  pointers.peek() +1){
                logger.error("Infinity loop");
                throw new LoopError("Wrong input");
            }
            commandPointer =  pointers.peek();
        }
        else{
            brackets.push(Brackets.CLOSE);
            if(!brackets.pop().equals(Brackets.CLOSE)){
                throw new LoopError("Close Bracket");
            }
            logger.debug("pop close");
            if(!brackets.pop().equals(Brackets.OPEN)){
                throw new LoopError("Open Bracket");
            }
            logger.debug("pop open");
            Integer prevPtr = pointers.pop();

            //Brainfuck.symbolCounter = Brainfuck.pointers.peek();
        }
    }
    public void checkStackTop(){
        if(!brackets.empty()){
            logger.error("Double Bracket");
            throw new LoopError("Double Bracket");
        }
    }

    public byte getCellValue(int idx){
        return memory[idx];
    }

    public void printCell(){
        System.out.println(memory[0]);
    }

    public void setInputString(String input){
        inputString = input;
    }
}

//возвращать??????