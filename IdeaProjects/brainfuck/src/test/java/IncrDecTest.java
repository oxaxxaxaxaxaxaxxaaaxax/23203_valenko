
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import nsu.Brainfuck;
import nsu.command.Increment;

public class IncrDecTest {
    void SimpleTest(){
        Brainfuck brainTest = new Brainfuck();
        Increment incr = new Increment(brainTest);
        for(int i=0;i<65;i++){
            incr.execute();
        }
        assertEquals('A', (char)brainTest.memory[0]);
    }
}
