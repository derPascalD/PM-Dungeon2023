package ecs.entities.Traps;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.Random;
import java.util.logging.Logger;

public abstract class Trap extends Entity {

    public boolean visible;

    public boolean active;
    public int damageValue;

    public Animation idle;

    protected Logger traplogger = Logger.getLogger(getClass().getName());

    public Trap() {
        super();
        new PositionComponent(this);
    }

    /**
     * @return defines if a trap will spawn in Level or not
     */
    public boolean createRandomBooleanValue() {
        Random rand = new Random();
        return rand.nextBoolean();
    }
}
