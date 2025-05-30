package nsu;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class GameApp extends GameApplication{
    int winScore = 9;
    int loseHealth =0;
    CollisionManager handler = new CollisionManager();
    LevelManager levelManager = new LevelManager();
    boolean EndLevel = false;

    @Override
    protected void initSettings(GameSettings settings){
        settings.setHeight(580);
        settings.setWidth(600);
        settings.setTitle("Ghost killer");
    }
    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().setGravity(0,600);
    }
    @Override
    public void onUpdate(double tpf){
        if(!EndLevel){
            handler.findCollision();
        }
        if(!EndLevel){
            handler.checkBulletPosition();
        }
        if(player == null){
            return;
        }
        player.getComponent(ViewEntityComponent.class).setView(player.getComponent(PlayerComponent.class).getOrientation());
        if(player.getComponent(PlayerComponent.class).getScore() == winScore){
             if(levelManager.isLastLevel()){
                showGameWinPage();
            }else{
                showWinPage();
                EndLevel = true;
                player.getComponent(PlayerComponent.class).setScore(0);
                handler.remAllEntities();
                ArrayList<Entity> entitiesCopy = new ArrayList<>(FXGL.getGameWorld().getEntities());
                FXGL.getGameWorld().removeEntities(entitiesCopy);
                levelManager.EndLevel();
                EndLevel = false;
            }
        }
        if(player.getComponent(PlayerComponent.class).getHealth() <= loseHealth){
            showGameOver();
        }
        if(player.getX() > getAppWidth() || player.getX() + player.getWidth()< 0 || player.getY() + player.getHeight() < 0 || player.getY() > getAppHeight() ){
            showGameOver();
        }
    }

    public void initEntities(){
        levelManager.loadGame();
        player = FXGL.spawn("player");

    }

    void showWinPage(){
        FXGL.getDialogService().showMessageBox("You completed the level!", () -> {});
    }
    void showGameOver(){
        FXGL.getDialogService().showMessageBox("You lose", getGameController()::exit);
    }
    void showGameWinPage(){FXGL.getDialogService().showMessageBox("You WON", getGameController()::exit);}

    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new Factory(handler));
        initEntities();
    }
    @Override
    protected void initInput(){
        FXGL.getInput().addAction(new UserAction("Move Right") {//обязательно указывать имя
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
                player.getComponent(PlayerComponent.class).setOrientation(Orientation.RIGHT);
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
                player.getComponent(PlayerComponent.class).setOrientation(Orientation.LEFT);
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
    
    public static void main(String[] args) {
        launch(args);
    }
    private Entity player;
}
