package nsu;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.entity.component.Component;

import java.util.HashMap;
import java.util.Map;

public class HealthComponent extends Component {
    private int state;
    private double stateLength;
    private Entity healthBar;
    private Map<Integer,Texture> states= new HashMap<>();
    public HealthComponent(int countStates, int health, Entity healthBar){
        state = countStates;
        this.healthBar = healthBar;
        stateLength = (double)health/(double)countStates;
    }
    public void addStates(int numberState, Texture newState){
        states.put(numberState,newState);
    }
    public void setHealthView(int currentHealth){
        int countCell = (int)(currentHealth/stateLength);
        while(countCell != state && countCell>0){
            state--;
            healthBar.getComponent(ViewComponent.class).clearChildren();
            Texture newTexture = new Texture(states.get(state).getImage());
            healthBar.getComponent(ViewComponent.class).addChild(newTexture);
        }
        if(currentHealth<=0){
            healthBar.removeFromWorld();
        }
    }
    @Override
    public void onUpdate(double tpf){
        healthBar.setPosition(entity.getX(), entity.getY() -25);
    }
}
