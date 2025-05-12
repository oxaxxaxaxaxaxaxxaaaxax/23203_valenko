package nsu;

import com.almasb.fxgl.entity.Entity;

public class Pair {
    EntityType type1;
    EntityType type2;

    Pair(EntityType object1,EntityType object2) {
        this.type1 = object1;
        this.type2 = object2;
    }
    EntityType getFirstType(){
        return type1;
    }
    EntityType getSecondType(){
        return type2;
    }
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object==null){
            return false;
        }
        if(this.getClass() != object.getClass()){
            return false;
        }
        Pair pair = (Pair)object;
        return ((type1 == pair.getFirstType()) &&(type2 == pair.getSecondType())) || ((type2 == pair.getFirstType()) &&(type1 == pair.getSecondType()));
    }
}