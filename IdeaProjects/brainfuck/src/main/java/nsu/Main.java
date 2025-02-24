package nsu;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.lang.reflect.Method;
import java.io.FileInputStream;
import java.lang.Class;


public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inp = classLoader.getResourceAsStream("nsu/config.properties");
        try{
            properties.load(inp);
        }
        catch(IOException e){
            System.out.println("Read error" + e.getMessage());
        }

        String sequence = args[0];
        Brainfuck.setCommandSequence(sequence);
        if(args.length >1){
            Brainfuck.inputString = args[1];
        }

        while(Brainfuck.commandPointer< sequence.length()) {
            try{
                String s = properties.getProperty(String.valueOf(sequence.charAt(Brainfuck.commandPointer)));

                if (!Factory.commands.containsKey((s))) {
                    try {
                        Class<?> c = Class.forName(s);
                        Factory.getInstance().register(s, c);
                    }
                    catch(ClassNotFoundException e) {
                        System.out.println("Class not found" + e.getMessage());
                    }
                }
                //Class<?>[] parameterTypes = {String.class};
                Class<?> c = Factory.getInstance().createByName(s);
                try{
                    Object o = c.getConstructor().newInstance();
                    Method m = c.getMethod("execute");
                    m.invoke(o);
                }
                catch(NoSuchMethodException e){
                    System.out.println("Method not found"+ e.getMessage());
                }
                catch(InstantiationException e){
                    System.out.println("Instantiation error" + e.getMessage());
                }
                catch(IllegalAccessException e){
                    System.out.println("Access error" + e.getMessage());
                }
                catch(InvocationTargetException e){
                    Throwable cause = e.getCause();
                    System.out.println("Error in method: oksana " + cause.getMessage());
                    return;
                }
            }
            catch(NullPointerException e){
                System.out.println("\nSymbol not found");
            }
            Brainfuck.commandPointer++;
        }
    }
}
