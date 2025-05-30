package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Factory implements EntityFactory {
    CollisionManager handler;

    public Factory(CollisionManager handler){
        this.handler = handler;
    }
    @Spawns("player")
    public Entity Player(SpawnData data){
        System.out.println("PLAYER");
        Entity player = FXGL.entityBuilder().view("right_player.png").at(100,200).type(EntityType.PLAYER).bbox(new HitBox(BoundingShape.box(30,41)))
                .with(new PhysicsComponent()).with(new CollidableComponent(true)).with(new CollisionComponent())
                .with(new PlayerComponent(handler)).with(new IrremovableComponent()).with(new ViewEntityComponent(new Texture(new Image("assets/textures/left_player.png")),new Texture(new Image("assets/textures/right_player.png")))).build();
        handler.addEntity(player);
        return player;
    }

    @Spawns("background")
    public Entity Background(SpawnData data){
       int level = data.get("level");
       if(level == 1){
           return FXGL.entityBuilder().at(0,0).view("back2.jpg").build();
       }else if(level ==2){
           return FXGL.entityBuilder().at(0,0).view("background2.png").build();
       }else{
           return FXGL.entityBuilder().at(0,0).view("background3.png").build();
       }
    }

    @Spawns("platform")
    public Entity Platform(SpawnData data){
        Entity plat = FXGL.entityBuilder(data).view(FXGL.texture("block1.png", data.<Integer>get("width"), data.<Integer>get("height"))).type(EntityType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new PhysicsComponent()).with(new CollidableComponent(true)).with(new CollisionComponent()).build();
        plat.getComponent(PhysicsComponent.class).setBodyType(BodyType.STATIC);
        handler.addEntity(plat);
        return plat;
    }
    @Spawns("coin")
    public Entity coin(SpawnData data){
        Entity coin = FXGL.entityBuilder(data).view("coin.png").type(EntityType.COIN).bbox(new HitBox(BoundingShape.box(20,20)))
                .with(new CollidableComponent(true)).with(new CollisionComponent()).build();
        handler.addEntity(coin);
        return coin;
    }
    @Spawns("healthbar")
    public Entity healthbar(SpawnData data){
        Entity healthbar = FXGL.entityBuilder(data).view(FXGL.texture("healthbar5.png",44,15)).with(new PhysicsComponent()).build();
        healthbar.getComponent(PhysicsComponent.class).setBodyType(BodyType.KINEMATIC);
        return healthbar;
    }
    @Spawns("enemy")
    public Entity enemy(SpawnData data){
        Entity enemy = FXGL.entityBuilder(data).view("right_enemy.png").type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(40,40))).with(new PhysicsComponent()).with(new CollidableComponent(true))
                .with(new ViewEntityComponent(new Texture(new Image("assets/textures/left_enemy.png")), new Texture(new Image("assets/textures/right_enemy.png"))))
                .with(new CollisionComponent()).with(new EnemyComponent(data.getX(), data.<Integer>get("width")+data.getX(), handler)).build();
        handler.addEntity(enemy);
        Entity healthbar = FXGL.spawn("healthbar", new SpawnData(data.getX(), data.getY()));
        enemy.addComponent(new HealthComponent(data.get("countStates"),enemy.getComponent(EnemyComponent.class).getHealth(),healthbar));
        enemy.getComponent(HealthComponent.class).addStates(1,new Texture(new Image("assets/textures/healthbar1.png")));
        enemy.getComponent(HealthComponent.class).addStates(2,new Texture(new Image("assets/textures/healthbar2.png")));
        enemy.getComponent(HealthComponent.class).addStates(3,new Texture(new Image("assets/textures/healthbar3.png")));
        enemy.getComponent(HealthComponent.class).addStates(4,new Texture(new Image("assets/textures/healthbar4.png")));
        enemy.getComponent(HealthComponent.class).addStates(5,new Texture(new Image("assets/textures/healthbar5.png")));
        return enemy;
    }
    @Spawns("smart enemy")
    public Entity smartEnemy(SpawnData data){
        Entity smartEnemy = FXGL.entityBuilder(data).view("right_smart_enemy.png").type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(44,28))).with(new PhysicsComponent()).with(new CollidableComponent(true))
                .with(new CollisionComponent())
                .with(new ViewEntityComponent(new Texture(new Image("assets/textures/left_smart_enemy.png")), new Texture(new Image("assets/textures/right_smart_enemy.png"))))
                .with(new SmartEnemyComponent(data.getX(), data.<Integer>get("width")+data.getX(), handler)).build();
        handler.addEntity(smartEnemy);
        Entity healthbar = FXGL.spawn("healthbar", new SpawnData(data.getX(), data.getY()));
        smartEnemy.addComponent(new HealthComponent(data.get("countStates"),smartEnemy.getComponent(SmartEnemyComponent.class).getHealth(),healthbar));
        smartEnemy.getComponent(HealthComponent.class).addStates(1,new Texture(new Image("assets/textures/healthbar1.png")));
        smartEnemy.getComponent(HealthComponent.class).addStates(2,new Texture(new Image("assets/textures/healthbar2.png")));
        smartEnemy.getComponent(HealthComponent.class).addStates(3,new Texture(new Image("assets/textures/healthbar3.png")));
        smartEnemy.getComponent(HealthComponent.class).addStates(4,new Texture(new Image("assets/textures/healthbar4.png")));
        smartEnemy.getComponent(HealthComponent.class).addStates(5,new Texture(new Image("assets/textures/healthbar5.png")));
        return smartEnemy;
    }
    @Spawns("bullet")
    public Entity bullet(SpawnData data){
        Entity bullet = FXGL.entityBuilder(data).view(data.get("view").toString()).type(EntityType.BULLET)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new PhysicsComponent()).with(new CollidableComponent(true))
                .with(new CollisionComponent()).build();
        bullet.getComponent(PhysicsComponent.class).setBodyType(BodyType.KINEMATIC);
        handler.addEntity(bullet);
        return bullet;
    }
}
