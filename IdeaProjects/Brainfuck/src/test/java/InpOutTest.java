
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import nsu.Context;
import nsu.command.Input;
import nsu.command.Output;
import nsu.command.Increment;

import java.io.*;

public class InpOutTest {
    @Test
    void SimpleTestInput() throws IOException {
        Context brainTest = new Context();



        String program = ",+.";
        InputStream currIn = System.in;
        byte[] data = "A".getBytes();
        try(InputStream inputStream = new ByteArrayInputStream(data)){
            System.setIn(inputStream);
            Input input = new Input(brainTest);
            Increment increment = new Increment(brainTest);
            input.execute();
            increment.execute();
            PrintStream currOut = System.out;
            try(ByteArrayOutputStream outputByteStream = new ByteArrayOutputStream()){
                try(PrintStream testOut = new PrintStream(outputByteStream)) {
                    System.setOut(testOut);
                    Output output = new Output(brainTest);
                    output.execute();
                    String outputTest = outputByteStream.toString();
                    assertEquals("B", outputTest);
                    System.setOut(currOut);
                    System.setIn(currIn);
                }
            }
        }



    }
}

