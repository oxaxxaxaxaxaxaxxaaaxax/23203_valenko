package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.logging.Handler;

enum BulletOrientation{
    RIGHT,
    LEFT
}


public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private int jumpCount =0;
    private BulletOrientation orient ;
    private CollisionManager handler;
    public PlayerComponent(CollisionManager handler){
        orient = BulletOrientation.RIGHT;
        this.handler = handler;
    }
    public void setJump(){
        jumpCount =2;
    }
    public void setOrientation(BulletOrientation newOrient){
        orient = newOrient;
    }
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
        if(jumpCount ==2){
            physics.setVelocityY(-300);
            jumpCount--;
        }else if(jumpCount ==1){
            physics.setVelocityY(-300);
            jumpCount--;
        }
    }
    public void shot() {
        if (orient == BulletOrientation.RIGHT) {
            Entity bullet = FXGL.spawn("bullet", new SpawnData(entity.getX() + entity.getWidth(), entity.getY() + entity.getHeight() / 2)
                    .put("width", 14).put("height", 7));
            bullet.getComponent(PhysicsComponent.class).setVelocityX(100);
            handler.addEntity(bullet);

        } else {
            Entity bullet = FXGL.spawn("bullet", new SpawnData(entity.getX() -10, entity.getY() + entity.getHeight() / 2)
                    .put("width", 14).put("height", 7));
            bullet.getComponent(PhysicsComponent.class).setVelocityX(-100);
            handler.addEntity(bullet);
        }
    }
}
