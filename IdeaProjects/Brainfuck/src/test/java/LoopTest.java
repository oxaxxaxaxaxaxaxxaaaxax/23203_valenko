import nsu.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import nsu.Brainfuck;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import nsu.LoopError;

public class LoopTest {
    @Test
    void DoubleBracket(){
        Context context = new Context();
        Factory f = new Factory(context);
        ClassLoader classLoader = LoopTest.class.getClassLoader();
        try(InputStream inp = classLoader.getResourceAsStream("config.properties")){
            f.properties.load(inp);
        } catch(IOException e){
            System.out.println("Read error" + e.getMessage());
        }
        String filePath = "src/main/resources/program/DoubleCloseBracket.txt";
        try(FileReader reader = new FileReader(filePath)){
            BufferedReader buff = new BufferedReader(reader);
            context.setCommandSequence(buff.readLine());
        }catch(IOException e){
            System.out.println("Wrong Class Path");
        }

        Brainfuck brainfuck= new Brainfuck(context, f);
        Exception exception = assertThrows(Exception.class, () -> { brainfuck.interpret();});
        assertEquals("Loop Error", exception.getMessage());
    }
}
