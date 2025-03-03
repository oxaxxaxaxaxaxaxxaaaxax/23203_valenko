package nsu;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.lang.reflect.Method;
import java.lang.Class;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.info("Start");
        Factory f = new Factory();
        ClassLoader classLoader = Main.class.getClassLoader();
        try(InputStream inp = classLoader.getResourceAsStream("config.properties")){
            f.properties.load(inp);
        } catch(IOException e){
            System.out.println("Read error" + e.getMessage());
        }
        Brainfuck brainfuck = new Brainfuck();
        String filePath = args[0];
        try(FileReader reader = new FileReader(filePath)){
            BufferedReader buff = new BufferedReader(reader);
            brainfuck.setCommandSequence(buff.readLine());
        }catch(IOException e){
            System.out.println("Wrong Class Path");
        }
        if(args.length >1){
            brainfuck.inputString = args[1];
        }
        Engine engine = new Engine(brainfuck, f);
        try{
            engine.interpret();
        }
        catch(Exception e){
            logger.error("ERROR");
        }
        logger.info("End");
    }
}