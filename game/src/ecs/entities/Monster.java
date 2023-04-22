package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.VelocityComponent;
import graphic.Animation;

public abstract class Monster extends Entity {
    public String pathToIdleLeft;
    public String pathToIdleRight;
    public String pathToRunLeft;
    public String pathToRunRight;

    float lifePoints;
    public float xSpeed;
    public float ySpeed;
    public boolean diagonal;

    Monster() {


    }

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
