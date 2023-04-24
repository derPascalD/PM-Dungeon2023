package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import graphic.Animation;

import java.util.Random;

public abstract class  Trap extends Entity{

    public boolean visible;

    public boolean active;
    public int damageValue;

    public  Animation idle;

    public Trap()
    {
        super();
        new PositionComponent(this);
    }

    /**
     *
     * @return defines if a trap will spawn in Level or not
     */
    public boolean createRandomBooleanValue()
    {
        Random rand = new Random();
        return rand.nextBoolean();
    }




}
