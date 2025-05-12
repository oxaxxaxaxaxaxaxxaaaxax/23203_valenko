package nsu;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

public class SmartEnemyComponent extends Component {
    private double minX;
    private double maxX;
    private PhysicsComponent physics;
    private Orientation orient;
    protected int damage = 50;
    protected int health = 500;
    private double currentSpeed = 100;
    private final int bulletDamage =20;
    private CollisionManager handler;

    SmartEnemyComponent(double beginX, double endX, CollisionManager handler) {
        minX = beginX;
        maxX = endX;
        orient = Orientation.RIGHT;
        this.handler = handler;
    }

    @Override
    public void onAdded(){
        physics = entity.getComponent(PhysicsComponent.class);
        physics.setBodyType(BodyType.DYNAMIC);
    }
    public void setVelocity(int velocity){
        currentSpeed = velocity;
    }
    public int getHealth(){return health;}
    public void setOrientation(Orientation newOrient){
        orient = newOrient;
    }
    public Orientation getOrientation(){
        return orient;
    }
    public double getMinX(){return minX;}
    public double getMaxX(){return maxX;}
    public double getSpeed(){return currentSpeed;}
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

    public int getDamage() {
        return damage;
    }

    public void collisionWithBullet() {
        health -= bulletDamage;
    }

    public void hitEnemy() {
        collisionWithBullet();
        entity.getComponent(HealthComponent.class).setHealthView(health);
        if (health <= 0) {
            getEntity().removeFromWorld();
            handler.remEntity(getEntity());
        }
    }
}
