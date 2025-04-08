package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import java.awt.*;
import java.util.ArrayList;

public class CollisionManager {
    protected void collisionBegin(){}
    protected void collisionContinues(){}
    protected void collisionEnd(){}
    private ArrayList<Entity> entities = new ArrayList<>();

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void handCollision(Entity entity1, Entity entity2){
        if((entity1.getType() == EntityType.COIN) && (entity2.getType()== EntityType.PLAYER)){
            entity1.removeFromWorld();
            FXGL.inc("score", +1);
            entities.remove(entity1);
            return;
        }
        if((entity1.getType() == EntityType.PLAYER )&& (entity2.getType()== EntityType.COIN)){
            entity2.removeFromWorld();
            FXGL.inc("score", +1);
            entities.remove(entity2);
            return;
        }
        if((entity1.getType() == EntityType.ENEMY) && (entity2.getType() == EntityType.PLAYER)){
            entity2.getComponent(PlayerComponent.class).setJump();
            if(entity1.hasComponent(SmartEnemyComponent.class)){
                FXGL.inc("health", entity1.getComponent(SmartEnemyComponent.class).getDamage());
            }else{
                FXGL.inc("health", entity1.getComponent(EnemyComponent.class).getDamage());
            }
            return;
        }
        if((entity1.getType() == EntityType.PLAYER) && (entity2.getType() == EntityType.ENEMY)){
            entity1.getComponent(PlayerComponent.class).setJump();
            if(entity2.hasComponent(SmartEnemyComponent.class)){
                FXGL.inc("health", entity2.getComponent(SmartEnemyComponent.class).getDamage());
            }else{
                FXGL.inc("health", entity2.getComponent(EnemyComponent.class).getDamage());
            }
            return;
        }
        if((entity1.getType() == EntityType.PLAYER) && (entity2.getType() == EntityType.PLATFORM)){
            entity1.getComponent(PlayerComponent.class).setJump();
            return;
        }
        if((entity1.getType() == EntityType.PLATFORM) && (entity2.getType() == EntityType.PLAYER)){
            entity2.getComponent(PlayerComponent.class).setJump();
            return;
        }
        if((entity1.getType() == EntityType.BULLET) && (entity2.getType()== EntityType.ENEMY)){
            if(entity2.hasComponent(SmartEnemyComponent.class)){
                FXGL.inc("score",+20);
            }else{
                FXGL.inc("score",+10);
            }
            entity2.removeFromWorld();
            entity1.removeFromWorld();
            entities.remove(entity2);
            return;
        }
        if((entity1.getType() == EntityType.ENEMY) && (entity2.getType()== EntityType.BULLET)){
            if(entity1.hasComponent(SmartEnemyComponent.class)){
                FXGL.inc("score",+20);
            }else{
                FXGL.inc("score",+10);
            }
            entity1.removeFromWorld();
            entity2.removeFromWorld();
            entities.remove(entity1);
            return;
        }
    }

    public void findCollision(){
        for(int i=0;i<entities.size();i++){
            Entity e1 = entities.get(i);
            for(int j =i+1 ;j< entities.size();j++){
                Entity e2 = entities.get(j);
                if(e2.getType() == e1.getType()){
                    continue;
                }
                CollisionComponent entityCol = e1.getComponent(CollisionComponent.class);
                if (entityCol.isCollision(e2)){
                    handCollision(e1,e2);
                }

            }
        }
    }

}
