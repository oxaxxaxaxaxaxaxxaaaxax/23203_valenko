package nsu;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class CollisionComponent extends Component {
    public boolean isCollision(Entity other){
        return (entity.getX() <= other.getX()+ other.getWidth()+1) &&
        (entity.getX() + entity.getWidth()+1 >= other.getX()) &&
        (entity.getY() <= other.getY()+ other.getHeight() +1) &&
        (entity.getY() + entity.getHeight()+1 >= other.getY()) ;
    }
}
