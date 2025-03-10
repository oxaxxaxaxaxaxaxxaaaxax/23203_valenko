package nsu.command;

import nsu.Context;
import nsu.Command;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class for "," command in brainfuck.
 * Read and set a value in memory array from the console.
 */
public class Input implements Command {
    public Input(Context brainfuckCopy){
        brainfuck=brainfuckCopy;
    }
    /**
     * Set input value in current node in memory array.
     */
    @Override
    public void execute()throws Exception{
//        brainfuck.memory[brainfuck.dataPointer] = (byte)brainfuck.inputString.toCharArray()[counter];
//        counter++;
        System.out.println("d");

        //System.out.println(String.valueOf(input.charAt(0)));
       // brainfuck.setCommandSequence(String.valueOf(input.charAt(0)));
        brainfuck.Input();
    }
    Context brainfuck;
    //static int counter=0;
}
