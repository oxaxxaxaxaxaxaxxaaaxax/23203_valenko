package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

public class EnemyComponent extends Component {
    private double minX;
    private double maxX;
    protected PhysicsComponent physics;
    double currentSpeed = 100;
    protected int damage = -1;
    double rightX;
    //EnemyComponent(){}
    EnemyComponent(double beginX, double endX){
        minX = beginX;
        maxX = endX;
    }

    public int getDamage(){
        return damage;
    }
    @Override
    public void onAdded(){
        physics = entity.getComponent(PhysicsComponent.class);
        physics.setBodyType(BodyType.DYNAMIC);
        //physics.setVelocityX(100);

    }
    @Override
    public void onUpdate(double tpf){

        //currentSpeed = entity.getComponent(PhysicsComponent.class).getVelocityX();
        double newX = entity.getX() + currentSpeed*tpf;
        if(newX + entity.getWidth() >= maxX){
            newX = maxX - entity.getWidth();
            currentSpeed = -Math.abs(currentSpeed);
        }else if(newX <= minX){
            newX = minX;
            currentSpeed = Math.abs(currentSpeed);
        }
        physics.setVelocityX(currentSpeed);
        //entity.setX(newX);

    }
}
