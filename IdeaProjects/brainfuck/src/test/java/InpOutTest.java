
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import nsu.Brainfuck;
import nsu.command.Input;
import nsu.command.Output;
import nsu.command.Increment;

public class InpOutTest {
    @Test
    void SimpleTestIncr(){
        Brainfuck brainTest = new Brainfuck();
        String program = ",+";
        brainTest.inputString="A";
        Input input = new Input(brainTest);
        //Output output = new Output(brainTest);
        Increment increment = new Increment(brainTest);
        input.execute();
        increment.execute();
        //output.execute();
        //assertEquals();
        assertEquals('B',(char) brainTest.memory[0]);
    }
}

