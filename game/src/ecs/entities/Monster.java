package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import graphic.Animation;

public abstract class Monster extends Entity {
    public String pathToIdleLeft;
    public String pathToIdleRight;
    public String pathToRunLeft;
    public String pathToRunRight;
    Animation hit;
    Animation die;

    int lifePoints;
    public float xSpeed;
    public float ySpeed;
    public boolean diagonal;

    Monster() {

    }


    abstract void attack();

    public void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    public void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }


}
