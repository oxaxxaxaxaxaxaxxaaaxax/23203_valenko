package nsu;

import nsu.command.Input;
import nsu.command.Output;
import nsu.command.Increment;

public class InputOutputTest {
    @Test
    void SimpleTestIncr(){
        String program = ",+.";
        Brainfuck.inputString="A";
        Input input = new Input();
        Output output = new Output();
        Increment increment = new Increment();
        Brainfuck.inputString=program;
        input.execute();
        increment.execute();
        output.execute();

    }
}
