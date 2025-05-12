package nsu;

import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;

public class CollisionManager {
    private final Pair pairCoinPlayer = new Pair(EntityType.COIN,EntityType.PLAYER);
    private final Pair pairEnemyPlayer = new Pair(EntityType.ENEMY,EntityType.PLAYER);
    private final Pair pairPlayerPlatform = new Pair(EntityType.PLAYER,EntityType.PLATFORM);
    private final Pair pairBulletEnemy = new Pair(EntityType.BULLET,EntityType.ENEMY);
    private final Pair pairBulletPlatform = new Pair(EntityType.BULLET,EntityType.PLATFORM);
    private ArrayList<Entity> entities = new ArrayList<>();

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void remEntity(Entity entity){entities.remove(entity);}

    public void remAllEntities(){
        ArrayList<Entity> copy = new ArrayList<>(entities);
        for(Entity entity: copy){
            if(entity.getType() == EntityType.PLAYER){
                continue;
            }
            remEntity(entity);
        }
    }
    public void handCollision(Entity entity1, Entity entity2){
        Pair pair = new Pair((EntityType) entity1.getType(),(EntityType)entity2.getType());
        switch (pair){
            case Pair p when p.equals(pairCoinPlayer) -> {
                if(p.getFirstType() == EntityType.COIN){
                    entity1.removeFromWorld();
                    entity2.getComponent(PlayerComponent.class).collectCoin();
                    entities.remove(entity1);
                }else{
                    entity2.removeFromWorld();
                    entity1.getComponent(PlayerComponent.class).collectCoin();
                    entities.remove(entity2);
                }
            }
            case Pair p when p.equals(pairBulletPlatform) -> {
                if(p.getFirstType() == EntityType.BULLET){
                    entity1.removeFromWorld();
                    entities.remove(entity1);
                    entity1.setPosition(0,0);
                }else{
                    entity2.removeFromWorld();
                    entities.remove(entity2);
                    entity2.setPosition(0,0);
                }
            }
            case Pair p when p.equals(pairEnemyPlayer)->{
                if(p.getFirstType() == EntityType.ENEMY){
                    entity2.getComponent(PlayerComponent.class).setJump();
                    entity2.getComponent(PlayerComponent.class).collisionWithEnemy(entity1);
                }else{
                    entity1.getComponent(PlayerComponent.class).setJump();
                    entity1.getComponent(PlayerComponent.class).collisionWithEnemy(entity2);
                }
            }
            case Pair p when p.equals(pairPlayerPlatform)->{
                if(p.getFirstType() == EntityType.PLAYER){
                    entity1.getComponent(PlayerComponent.class).setJump();
                }else{
                    entity2.getComponent(PlayerComponent.class).setJump();
                }
            }
            case Pair p when p.equals(pairBulletEnemy)->{
                if(p.getFirstType() == EntityType.BULLET){
                    CollisionComponent cmpCollision = entity2.getComponent(CollisionComponent.class);
                    if(cmpCollision.isCollisionBegin(entity1)) {
                        if(entity2.hasComponent(SmartEnemyComponent.class)){
                            SmartEnemyComponent cmpEnemy = entity2.getComponent(SmartEnemyComponent.class);
                            cmpEnemy.hitEnemy();
                        }else{
                            EnemyComponent cmpEnemy = entity2.getComponent(EnemyComponent.class);
                            cmpEnemy.hitEnemy();
                        }
                        entity1.removeFromWorld();
                        entities.remove(entity1);
                        entity1.setPosition(0,0);
                    }
                    cmpCollision.isCollisionEnd(entity1);
                }else{
                    CollisionComponent cmpCollision = entity1.getComponent(CollisionComponent.class);
                    if(cmpCollision.isCollisionBegin(entity2)) {
                        if(entity1.hasComponent(SmartEnemyComponent.class)){
                            SmartEnemyComponent cmpEnemy = entity1.getComponent(SmartEnemyComponent.class);
                            cmpEnemy.hitEnemy();
                        }else{
                            EnemyComponent cmpEnemy = entity1.getComponent(EnemyComponent.class);
                            cmpEnemy.hitEnemy();
                        }
                        entity2.removeFromWorld();
                        entities.remove(entity2);
                        entity2.setPosition(0,0);
                    }
                    cmpCollision.isCollisionEnd(entity2);
                }
            }
            default -> {}
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
