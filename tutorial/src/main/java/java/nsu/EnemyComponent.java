package nsu;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

public class EnemyComponent extends Component {
    private double minX;
    private double maxX;
    private PhysicsComponent physics;
    private Orientation orient;
    private double currentSpeed = 100;
    private int damage = 50;
    private int health = 200;
    private final int bulletDamage = 50;
    private CollisionManager handler;
    EnemyComponent(double beginX, double endX, CollisionManager handler){
        minX = beginX;
        maxX = endX;
        orient = Orientation.RIGHT;
        this.handler = handler;
    }
    public void setOrientation(Orientation newOrient){
        orient = newOrient;
    }
    public Orientation getOrientation(){
        return orient;
    }
    public int getDamage(){
        return damage;
    }
    public int getHealth(){return health;}
    @Override
    public void onAdded(){
        physics = entity.getComponent(PhysicsComponent.class);
        physics.setBodyType(BodyType.DYNAMIC);
    }
    @Override
    public void onUpdate(double tpf){
        entity.getComponent(ViewEntityComponent.class).setView(getOrientation());
        double newX = entity.getX() + currentSpeed*tpf;
        if(newX + entity.getWidth() >= maxX){
            newX = maxX - entity.getWidth();
            currentSpeed = -Math.abs(currentSpeed);
            setOrientation(Orientation.LEFT);
        }else if(newX <= minX){
            newX = minX;
            currentSpeed = Math.abs(currentSpeed);
            setOrientation(Orientation.RIGHT);
        }
        physics.setVelocityX(currentSpeed);
    }
    public void collisionWithBullet(){
        health-=bulletDamage;
    }
    public void hitEnemy(){
        collisionWithBullet();
        if(health<=0){
            getEntity().removeFromWorld();
            handler.remEntity(getEntity());
        }
    }
}
