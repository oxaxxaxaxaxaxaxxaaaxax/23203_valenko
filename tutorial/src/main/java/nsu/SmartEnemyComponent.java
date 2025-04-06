package nsu;

public class SmartEnemyComponent extends EnemyComponent{
    private double minX;
    private double maxX;
    protected int damage = -5;
    SmartEnemyComponent(double beginX, double endX){
        super(beginX, endX);
    }
    @Override
    public int getDamage(){
        return damage;
    }
}
