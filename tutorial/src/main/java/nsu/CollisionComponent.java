package nsu;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class CollisionComponent extends Component {
    public boolean isCollision(Entity other){
        return (entity.getX() <= other.getX()+ other.getWidth()) &&
                (entity.getX() + entity.getWidth() >= other.getX()) &&
                (entity.getY() <= other.getY()+ other.getHeight()) &&
                (entity.getY() + entity.getHeight() >= other.getY()) ;
    }
}
