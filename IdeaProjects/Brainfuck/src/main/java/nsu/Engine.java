package nsu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import nsu.LoopError;


public class Engine {
    Engine(Brainfuck brainfuckCopy, Factory fCopy){
        logger = LogManager.getLogger(Engine.class);
        brainfuck = brainfuckCopy;
        f= fCopy;
    }
    public void interpret() throws Exception {
        logger.trace("Start interpret function");
        while(brainfuck.commandPointer < brainfuck.commands.length()) {
            stepCount++;
            try{
                if(stepCount > maxSteps){
                    logger.error("Infinity loop");
                    throw new LoopError("Wrong input");
                }
                String s = f.properties.getProperty(String.valueOf(brainfuck.commands.charAt(brainfuck.commandPointer)));
                try{
                    if (!f.commands.containsKey((s))) {
                        Class<?> c = Class.forName(s);
                        f.register(s, c);
                    }
                    Class<?> c = f.createByName(s);
                    Object o = c.getConstructor(Brainfuck.class).newInstance(brainfuck);
                    Method m = c.getMethod("execute");
                    m.invoke(o);
                }
                catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                      InvocationTargetException e){
                    // ??????????????????
                    //???????????????????
                }
            }
            catch(NullPointerException e){
                System.out.println("\nSymbol not found");
            }
            catch(LoopError e){
                System.out.println("Some errors in code");
                throw new Exception();
            }
            brainfuck.commandPointer++;
        }
        System.out.println(" ");
        //brainfuck.printCell();
    }
    private Brainfuck brainfuck;
    private Factory f;
    private Logger logger;
    private int maxSteps = 10000000;
    private int stepCount = 0;
}
//engine нужно превратить в brainfuck а brainfuck в context
//в каждом классе свой логер йоу