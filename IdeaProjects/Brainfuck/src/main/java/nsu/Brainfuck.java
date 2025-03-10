package nsu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Brainfuck {
    public Brainfuck(Context contextCopy, Factory fCopy){
        logger = LogManager.getLogger(Brainfuck.class);
        context = contextCopy;
        f= fCopy;
    }
    public void interpret() throws Exception {
        logger.trace("Start interpret function");
        try {
            while(context.commandPointer < context.commands.length()) {
                stepCount++;
                if(stepCount > maxSteps){
                    logger.error("Infinity loop");
                    throw new LoopError("Wrong input");
                }
                String s = f.properties.getProperty(String.valueOf(context.commands.charAt(context.commandPointer)));
                logger.debug("properties normal");

                try{
                    Object o = f.getObject(s);
                    Method m = f.getCommand(s);
                    logger.debug("before int");
                    m.invoke(o);
                    logger.debug("after int");
                }
                catch(InvocationTargetException e){
                    System.err.println("Some errors in code");//убрать
                    throw new Exception("Loop Exception");
                }
                context.commandPointer++;
            }
            context.checkStackTop();
        }
        catch(NullPointerException e){
            System.out.println("\nSymbol not found");
        }
        catch(LoopError e){
            System.out.println("Some errors in code");
            throw new Exception("Loop Error");
        }
        System.out.println(" ");
    }
    private Context context;
    private Factory f;
    private Logger logger;
    private int maxSteps = 10000000;
    private int stepCount = 0;
}
//engine нужно превратить в brainfuck а brainfuck в context
//в каждом классе свой логер йоу