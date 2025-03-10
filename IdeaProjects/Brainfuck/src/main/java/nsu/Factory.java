package nsu;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;
import java.lang.Class;
import java.util.Properties;

public class Factory {
    public Factory(Context copyContext){
        context = copyContext;
    }

    public void register(String name, Class command){
        commands.put(name, command);
    }

    public Class<?> createByName(String name){
        try{
            if (!commands.containsKey((name))) {
                Class<?> c = Class.forName(name);
                register(name, c);
            }
            Class<?> command = commands.get(name);
            if(command == null){
                System.out.println("sos");
            }
            return command;
        }catch(ClassNotFoundException e){
            throw new FactoryException("Class not found");
        }
    }



    public Object getObject(String s){
        try{
            Class<?> c = createByName(s);
            return c.getConstructor(Context.class).newInstance(context);
        }
        catch(NoSuchMethodException | InstantiationException | IllegalAccessException |
              InvocationTargetException e){
            throw new FactoryException(e.getMessage());
        }
    }

    public Method getCommand(String s){
        try{
            Class<?> c = createByName(s);
            return c.getMethod("execute");
        }catch(NoSuchMethodException e){
            throw new FactoryException("Method not found");
        }
    }

    //Factory(){};private
    //private static Factory f = new Factory();
    public Properties properties = new Properties();
    private final Context context;
    public Map<String, Class<?>> commands = new HashMap<String, Class<?>>();////////?????где то должен храниться
}
