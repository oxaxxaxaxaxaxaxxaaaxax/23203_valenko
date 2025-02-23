package nsu;
import java.util.Map;
import java.util.HashMap;
import java.lang.Class;

public class Factory {
    static public Factory getInstance(){
        return f;
    }

    void register(String name, Class command){
        commands.put(name, command);
    }

    Class<?> createByName(String name){
        Class<?> command = commands.get(name);
        if(command == null){
            System.out.println("sos");
        }
        return command;
    }

    private Factory(){};
    private static Factory f = new Factory();
    public static Map<String, Class<?>> commands = new HashMap<String, Class<?>>();

}
