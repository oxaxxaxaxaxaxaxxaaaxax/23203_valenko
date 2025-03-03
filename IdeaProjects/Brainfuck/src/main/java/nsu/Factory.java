package nsu;
import java.util.Map;
import java.util.HashMap;
import java.lang.Class;
import java.util.Properties;

public class Factory {
//    static public Factory getInstance(){
//        return f;
//    }

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

    //Factory(){};private
    //private static Factory f = new Factory();
    Properties properties = new Properties();

    public Map<String, Class<?>> commands = new HashMap<String, Class<?>>();////////?????где то должен храниться
}
