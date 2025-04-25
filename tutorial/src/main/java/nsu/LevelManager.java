package nsu;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import java.util.HashMap;
import java.util.Map;



public class LevelManager {
    private int levelNumber = 1;
    private int levelCount = 2;
    public interface Level{
        void loadLevel();
    }
    public void showWinGame(){}
    public void incrLevel(){
        levelNumber++;
        if(levelNumber >= levelCount){
            showWinGame();
        }
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
                Entity platform5 = FXGL.spawn("platform", new SpawnData(50,550).put("width", 300).put("height", 30));
                Entity coin1 = FXGL.spawn("coin", new SpawnData(220,90).put("radius", 15));
                Entity coin2 = FXGL.spawn("coin", new SpawnData(110,150).put("radius", 15));
                Entity coin3 = FXGL.spawn("coin", new SpawnData(330,150).put("radius", 15));
                Entity enemy1 = FXGL.spawn("enemy", new SpawnData(100,300).put("width",100));
                Entity enemy3 = FXGL.spawn("smart enemy", new SpawnData(200,200).put("width",250));
                Entity enemy2 = FXGL.spawn("enemy", new SpawnData(400,170).put("width",100));
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
                Entity coin2 = FXGL.spawn("coin", new SpawnData(110,150).put("radius", 15));
                Entity coin3 = FXGL.spawn("coin", new SpawnData(330,150).put("radius", 15));
                //Entity enemy1 = FXGL.spawn("enemy", new SpawnData(100,400).put("width",100));
                //Entity enemy3 = FXGL.spawn("smart enemy", new SpawnData(200,300).put("width",250));
                //Entity enemy2 = FXGL.spawn("enemy", new SpawnData(400,180).put("width",100));
            }
        });
    }
    public void loadGame(){
        levels.get(levelNumber).loadLevel();
    }
    public void EndLevel(){
        incrLevel();
        System.out.println(levelNumber);
        loadGame();
    }
    boolean isLastLevel(){
        return levelNumber == levelCount;
    }
    private Map<Integer, Level> levels = new HashMap<Integer,Level>();

}
