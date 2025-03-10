import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import nsu.Context;
import nsu.command.Increment;

public class IncrDecTest {
    void SimpleTest(){
        Context brainTest = new Context();
        Increment incr = new Increment(brainTest);
        for(int i=0;i<65;i++){
            incr.execute();
        }
        assertEquals('A', (char)brainTest.getCellValue(0));
    }
}