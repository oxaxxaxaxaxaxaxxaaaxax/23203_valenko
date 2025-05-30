package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

enum Orientation{
    RIGHT,
    LEFT
}

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private int jumpCount = 0;
    private Orientation orient ;
    private CollisionManager handler;
    private int health;
    private double healthIndicator;
    private boolean LowhpFlag = false;
    private int score=0;
    private GameText scoreText;
    private GameText healthText;
    public PlayerComponent(CollisionManager handler){
        orient = Orientation.RIGHT;
        this.handler = handler;
        health = 1000;
        healthIndicator = 0.3 *health;
        scoreText = new GameText("Score: ",15,22, 0);
        healthText = new GameText("Health: ",15,50, 1000);
    }
    public int getHealth(){return health;}
    public int getScore(){return score;}
    public void setScore(int score){
        this.score = score;
        scoreText.setTextValue(0);
    }
    public void setJump(){
        jumpCount =1;
    }
    public int getJump(){ return jumpCount;}
    public GameText getTextScore(){
        return scoreText;
    }
    public void setOrientation(Orientation newOrient){
        orient = newOrient;
    }
    public Orientation getOrientation(){
        return orient;
    }
    @Override
    public void onAdded(){
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

    public void jump() {

        if (jumpCount == 2) {
            jumpCount--;
            physics.setVelocityY(-300);
        } else if (jumpCount == 1) {
            jumpCount--;
            physics.setVelocityY(-300);
        }
    }/*
     */
    public void shot() {
        if (orient == Orientation.RIGHT) {
            Entity bullet = FXGL.spawn("bullet", new SpawnData(entity.getX() + entity.getWidth(), entity.getY() +entity.getHeight() / 3)
                    .put("width", 20).put("height", 15).put("view", "bullet_right.png"));
            bullet.getComponent(PhysicsComponent.class).setVelocityX(200);
            handler.addEntity(bullet);
        } else {
            Entity bullet = FXGL.spawn("bullet", new SpawnData(entity.getX() -10, entity.getY() +entity.getHeight() / 3)
                    .put("width", 20).put("height", 15).put("view", "bullet_left.png"));
            bullet.getComponent(PhysicsComponent.class).setVelocityX(-200);
            handler.addEntity(bullet);

        }
    }
    public void collectCoin(){
        score+=3;
        scoreText.setTextValue(score);
    }
    public void collisionWithEnemy(Entity enemy){
        if(enemy.hasComponent(SmartEnemyComponent.class)){
            int damage = enemy.getComponent(SmartEnemyComponent.class).getDamage();
            health-=damage;
        }else{
            int damage = enemy.getComponent(EnemyComponent.class).getDamage();
            health-=damage;
        }
        if(health<0){
            return;
        }
        if(health < healthIndicator && !LowhpFlag){
            LowhpFlag =true;
            entity.getComponent(ViewEntityComponent.class).setNewTexture();
        }
        healthText.setTextValue(health);
    }
}
