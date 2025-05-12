package nsu;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class GameText
{
    Text text;
    String name;
    GameText(String name, double x, double y, int initVal){
        this.name = name;
        text = new Text(name + initVal);
        text.setX(x);//15
        text.setY(y);//22
        text.setFont(Font.font("Arial", 18));
        text.setFill(Color.WHITE);
        getGameScene().addUINode(text);
    }
    public void setTextValue(int value){
        text.setText(name + value);
    }
}
