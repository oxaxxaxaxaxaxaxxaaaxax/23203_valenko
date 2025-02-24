package org.example;

public class ClassToLoad  implements  Command{
    @Override
    public void execute() {
        System.out.println("execute");
    }

    public void anotherMethod(String[] args) {
        System.out.println("AnotherAAAAAAAAA");
    }
}
