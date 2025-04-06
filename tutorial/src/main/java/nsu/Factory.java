package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Factory implements EntityFactory {
    CollisionManager handler;

    public Factory(CollisionManager handler){
        this.handler = handler;
    }
    @Spawns("player")
    public Entity Player(SpawnData data){
        Texture texture = FXGL.getAssetLoader().loadTexture("player.png");
        Entity player = FXGL.entityBuilder().view(new Rectangle(25,25, Color.WHITE)).at(100,200).type(EntityType.PLAYER).bbox(new HitBox(BoundingShape.box(25,25)))
                .with(new PhysicsComponent()).with(new CollidableComponent(true)).with(new CollisionComponent())
                .with(new PlayerComponent()).build();
        handler.addEntity(player);
        return player;
    }

    //FXGL.spawn("platform", new SpawnData(100, 500).put("width", 200).put("height", 20));
    @Spawns("platform")
    public Entity Platform(SpawnData data){
        Entity plat = FXGL.entityBuilder(data).view(new Rectangle(data.<Integer>get("width"),data.<Integer>get("height"),Color.GREENYELLOW)).type(EntityType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new PhysicsComponent()).with(new CollidableComponent(true)).with(new CollisionComponent()).build();
        plat.getComponent(PhysicsComponent.class).setBodyType(BodyType.STATIC);
        handler.addEntity(plat);
        return plat;
    }
    @Spawns("coin")
    public Entity coin(SpawnData data){//x 220   y100
        Entity coin = FXGL.entityBuilder(data).view(new Circle(data.<Integer>get("radius"), Color.YELLOW)).type(EntityType.COIN).bbox(new HitBox(BoundingShape.circle(data.<Integer>get("radius"))))
                .with(new CollidableComponent(true)).with(new CollisionComponent()).build();
        handler.addEntity(coin);
        return coin;
    }
    @Spawns("enemy")
    public Entity enemy(SpawnData data){
        Entity enemy = FXGL.entityBuilder(data).view(new Rectangle(40,40, Color.DARKRED)).type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(40,40))).with(new PhysicsComponent()).with(new CollidableComponent(true))
                .with(new CollisionComponent()).with(new EnemyComponent(data.getX(), data.<Integer>get("width")+data.getX())).build();
        handler.addEntity(enemy);
        return enemy;
    }
    @Spawns("smart enemy")
    public Entity smartEnemy(SpawnData data){
        Entity smartEnemy = FXGL.entityBuilder(data).view(new Rectangle(30,30,Color.PURPLE)).type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(30,30))).with(new PhysicsComponent()).with(new CollidableComponent(true))
                .with(new CollisionComponent())
                .with(new SmartEnemyComponent(data.getX(), data.<Integer>get("width")+data.getX())).build();
        handler.addEntity(smartEnemy);
        return smartEnemy;
    }

}
