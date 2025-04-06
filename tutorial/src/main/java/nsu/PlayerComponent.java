package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;


public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    public PlayerComponent(){}
    @Override
    public void onAdded(){
        //Image image = image("player.png");
        //Image image = FXGL.image("player.png");
        physics = getEntity().getComponent(PhysicsComponent.class);
        physics.setBodyType(BodyType.DYNAMIC);
    }

    public void left(){
        physics.setVelocityX(-150);
    }
    public void right(){
        physics.setVelocityX(150);
    }
    public void stop() {
        physics.setVelocityX(0);
    }
    public void jump(){
        physics.setVelocityY(-300);
    }

}
