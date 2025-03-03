
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import nsu.Brainfuck;
import nsu.command.Input;
import nsu.command.Output;
import nsu.command.Increment;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;

public class InpOutTest {
    @Test
    void SimpleTestInput(){
        Brainfuck brainTest = new Brainfuck();
        String program = ",+.";
        brainTest.inputString="A";
        Input input = new Input(brainTest);
        Increment increment = new Increment(brainTest);
        input.execute();
        increment.execute();
        //assertEquals();
        //assertEquals('B',(char) brainTest.memory[0]);
        PrintStream currOut = System.out;
        ByteArrayOutputStream outputByteStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputByteStream);
        System.setOut(testOut);
        Output output = new Output(brainTest);
        output.execute();
        String outputTest = outputByteStream.toString();
        assertEquals("B", outputTest);
        System.setOut(currOut);

    }
}

