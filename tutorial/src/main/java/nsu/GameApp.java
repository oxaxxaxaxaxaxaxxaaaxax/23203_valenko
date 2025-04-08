package nsu;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Map;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class GameApp extends GameApplication{
    CollisionManager handler = new CollisionManager();

    @Override
    protected void initSettings(GameSettings settings){
        settings.setHeight(900);
        settings.setWidth(600);
        settings.setTitle("Game");
        settings.setVersion("0.1");
    }
    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().setGravity(0,600);
//        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
//            @Override
//            protected void onCollisionBegin(Entity player, Entity coin) {
//                coin.removeFromWorld();
//            }
//        });
    }
    @Override
    public void onUpdate(double tpf){
        handler.findCollision();
    }


    public void initEntities(){
        Entity platform1 = FXGL.spawn("platform", new SpawnData(200,300).put("width", 250).put("height", 50));
        Entity platform2 = FXGL.spawn("platform", new SpawnData(400,190).put("width", 100).put("height", 50));
        Entity platform3 = FXGL.spawn("platform", new SpawnData(100,400).put("width", 100).put("height", 50));
        Entity platform4 = FXGL.spawn("platform", new SpawnData(50,550).put("width", 300).put("height", 50));
        //FXGL.spawn("platform", new SpawnData(100, 500).put("width", 200).put("height", 20));
        player = FXGL.spawn("player");
        Entity coin1 = FXGL.spawn("coin", new SpawnData(220,90).put("radius", 15));
        Entity coin2 = FXGL.spawn("coin", new SpawnData(110,150).put("radius", 15));
        Entity coin3 = FXGL.spawn("coin", new SpawnData(330,150).put("radius", 15));
        getGameScene().setBackgroundColor(Color.LIGHTSKYBLUE);
        Entity enemy1 = FXGL.spawn("enemy", new SpawnData(100,400).put("width",100));
        Entity enemy3 = FXGL.spawn("smart enemy", new SpawnData(200,300).put("width",250));
        Entity enemy2 = FXGL.spawn("enemy", new SpawnData(400,190).put("width",100));
    }

    public void initWalls(){
        Entity walls = FXGL.entityBuilder().with(new CollisionComponent()).buildScreenBounds(150);
        getGameWorld().addEntity(walls);
    }

    void showWinPage(){
        FXGL.getDialogService().showMessageBox("You win!!!!:)", getGameController()::exit);
    }
    void showGameOver(){
        FXGL.getDialogService().showMessageBox("You lose:(", getGameController()::exit);
    }

    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new Factory(handler));
        initEntities();
        initWalls();
        FXGL.getWorldProperties().<Integer>addListener("health", (oldValue,newValue)->{
            if(newValue < 0){
                showGameOver();
            }
        });
        FXGL.getWorldProperties().<Integer>addListener("score", (oldValue, newValue)->{
            if(newValue == 43){
                showWinPage();
            }
        });

        //player = FXGL.entityBuilder().at(300,300).view(new Rectangle(25,25,Color.BLUE)).buildAndAttach();
    }
    @Override
    protected void initInput(){
        FXGL.getInput().addAction(new UserAction("Move Right") {//обязательно указывать имя
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
                player.getComponent(PlayerComponent.class).setOrientation(BulletOrientation.RIGHT);
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);
        FXGL.getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
                player.getComponent(PlayerComponent.class).setOrientation(BulletOrientation.LEFT);
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Jump"){
            @Override
            protected void onActionBegin(){
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("Shot"){
            @Override
            protected void onActionBegin(){
                player.getComponent(PlayerComponent.class).shot();}
        },KeyCode.SPACE);
    }
    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("score", 0);
        vars.put("health", 1000);
    }

    @Override
    protected void initUI(){
        Text scoreText = FXGL.getUIFactoryService().newText("",Color.WHITE, 24);
        FXGL.addUINode(scoreText,20,20);
        scoreText.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString());
        //scoreText.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString("Score: %d"));
        Text livesText = FXGL.getUIFactoryService().newText("",Color.WHITE, 24);
        FXGL.addUINode(livesText, 20, 50);
        livesText.textProperty().bind(FXGL.getWorldProperties().intProperty("health").asString());
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void initGameObjects(){
    }
    private Entity player;
    //private Entity platform1;
    //private Entity platform2;
    //private Entity platform3;
    //private Entity coin;
}
