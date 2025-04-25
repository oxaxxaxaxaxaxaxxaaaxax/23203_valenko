package nsu;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;


public class ViewEntityComponent extends Component {
    Orientation viewOrientation;
    Texture leftOrientation;
    Texture rightOrientation;
    ViewEntityComponent(Texture leftImage, Texture rigthImage){
        viewOrientation = Orientation.RIGHT;
        leftOrientation= leftImage;
        rightOrientation = rigthImage;
    }
    public Orientation getTextureOrientation(){
        return viewOrientation;
    }
    public void setOrientation(Orientation newOrientation){
        viewOrientation = newOrientation;
    }
    public Texture getTexture(){
        if (leftOrientation == null || rightOrientation == null) {
            throw new IllegalStateException("Texture is not initialized!");
        }
        switch (viewOrientation){
            case Orientation.LEFT:
                return leftOrientation;
            case Orientation.RIGHT:
                return rightOrientation;
            default: return rightOrientation;
        }
    }
    public void setView(Orientation modelOrientation){
        Entity entity = getEntity();
        if(modelOrientation != getTextureOrientation()){
            setOrientation(modelOrientation);
            entity.getComponent(ViewComponent.class).clearChildren();
            Texture newTexture = new Texture(getTexture().getImage());
            entity.getComponent(ViewComponent.class).addChild(newTexture);
        }
    }
}
