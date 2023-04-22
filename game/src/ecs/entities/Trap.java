package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import graphic.Animation;

import java.util.Random;

public abstract class  Trap extends Entity{

    public boolean visible;

    public boolean active;

    public float damageValue;
    public  String pathToIdleLeft;
    public  String pathToIdleRight;

    public  Animation idle;

    public Trap()
    {
        super();
        new PositionComponent(this);
    }

    public boolean createRandomBooleanValue()
    {
        Random rand = new Random();
        return rand.nextBoolean();
    }




}
