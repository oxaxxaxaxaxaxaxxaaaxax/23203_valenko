package nsu;

import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.info("Start");
        Context context = new Context();
        Factory f = new Factory(context);
        ClassLoader classLoader = Main.class.getClassLoader();

        try(InputStream inp = classLoader.getResourceAsStream("config.properties")){
            f.properties.load(inp);
        } catch(IOException e){
            logger.error("Read error");
        }
        String filePath = args[0];
        try(FileReader reader = new FileReader(filePath)){
            BufferedReader buff = new BufferedReader(reader);
            context.setCommandSequence(buff.readLine());
        }catch(IOException e){
            System.out.println("Wrong Class Path");
        }
        if(args.length >1){
            context.inputString = args[1];
        }
        Brainfuck brainfuck= new Brainfuck(context, f);
        try{
            brainfuck.interpret();
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }
        logger.info("End");
    }
}