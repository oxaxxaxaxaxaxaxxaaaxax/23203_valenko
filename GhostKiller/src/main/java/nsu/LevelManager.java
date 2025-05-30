package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import java.util.HashMap;
import java.util.Map;

public class LevelManager {
    private int levelNumber = 1;
    private int levelCount = 3;
    public interface Level{
        void loadLevel();
    }
    public void showWinGame(){}
    public void incrLevel(){
        levelNumber++;
    }
    LevelManager(){
        levels.put(1,new Level(){
            @Override
            public void loadLevel(){
                Entity background = FXGL.spawn("background", new SpawnData().put("level",1));
                background.setZIndex(-1);
                Entity platform1 = FXGL.spawn("platform", new SpawnData(200,300).put("width", 250).put("height", 30));
                Entity platform2 = FXGL.spawn("platform", new SpawnData(400,190).put("width", 100).put("height", 30));
                Entity platform3 = FXGL.spawn("platform", new SpawnData(100,400).put("width", 100).put("height", 30));
                Entity platform4 = FXGL.spawn("platform", new SpawnData(300,500).put("width", 300).put("height", 30));
                Entity coin1 = FXGL.spawn("coin", new SpawnData(220,90).put("radius", 15));
                Entity coin2 = FXGL.spawn("coin", new SpawnData(110,150).put("radius", 15));
                Entity coin3 = FXGL.spawn("coin", new SpawnData(330,150).put("radius", 15));
                Entity enemy1 = FXGL.spawn("enemy", new SpawnData(100,300).put("width",100).put("countStates",5));
                Entity enemy3 = FXGL.spawn("smart enemy", new SpawnData(200,200).put("width",250).put("countStates",5));
                Entity enemy2 = FXGL.spawn("enemy", new SpawnData(400,170).put("width",100).put("countStates",5));
            }
        });
        levels.put(2, new Level() {
            @Override
            public void loadLevel() {
                Entity background = FXGL.spawn("background", new SpawnData().put("level",2));
                background.setZIndex(-1);
                Entity platform1 = FXGL.spawn("platform", new SpawnData(150,400).put("width", 250).put("height", 30));
                Entity platform2 = FXGL.spawn("platform", new SpawnData(400,300).put("width", 100).put("height", 30));
                Entity platform3 = FXGL.spawn("platform", new SpawnData(100,100).put("width", 100).put("height", 30));
                Entity coin1 = FXGL.spawn("coin", new SpawnData(220,90).put("radius", 15));
                Entity coin2 = FXGL.spawn("coin", new SpawnData(450,250).put("radius", 15));
                Entity coin3 = FXGL.spawn("coin", new SpawnData(100,70).put("radius", 15));
                Entity enemy1 = FXGL.spawn("smart enemy", new SpawnData(150,380).put("width",250).put("countStates",5));
                Entity enemy3 = FXGL.spawn("smart enemy", new SpawnData(400,280).put("width",100).put("countStates",5));
                Entity enemy2 = FXGL.spawn("smart enemy", new SpawnData(100,80).put("width",100).put("countStates",5));
            }
        });
        levels.put(3,new Level(){
            @Override
            public void loadLevel(){
                Entity background = FXGL.spawn("background", new SpawnData().put("level",3));
                background.setZIndex(-1);
                Entity platform1 = FXGL.spawn("platform", new SpawnData(125,520).put("width", 250).put("height", 30));
                Entity platform2 = FXGL.spawn("platform", new SpawnData(234,323).put("width", 100).put("height", 30));
                Entity platform3 = FXGL.spawn("platform", new SpawnData(460,254).put("width", 50).put("height", 30));
                Entity platform4 = FXGL.spawn("platform", new SpawnData(60,195).put("width", 100).put("height", 30));
                Entity platform5 = FXGL.spawn("platform", new SpawnData(100,66).put("width", 50).put("height", 30));
                Entity coin1 = FXGL.spawn("coin", new SpawnData(270,250).put("radius", 15));
                Entity coin2 = FXGL.spawn("coin", new SpawnData(110,20).put("radius", 15));
                Entity coin3 = FXGL.spawn("coin", new SpawnData(550,80).put("radius", 15));
                Entity enemy3 = FXGL.spawn("smart enemy", new SpawnData(234,313).put("width",100).put("countStates",5));
                enemy3.getComponent(SmartEnemyComponent.class).setVelocity(200);
            }
        });
    }
    public void loadGame(){
        levels.get(levelNumber).loadLevel();
    }
    public void EndLevel(){
        incrLevel();
        loadGame();
    }
    boolean isLastLevel(){
        System.out.println(levelNumber);
        return levelNumber == levelCount;
    }
    private Map<Integer, Level> levels = new HashMap<Integer,Level>();

}
