package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.VelocityComponent;
import graphic.Animation;

public abstract class Monster extends Entity {
    protected String pathToIdleLeft;
    protected String pathToIdleRight;
    protected String pathToRunLeft;
    protected String pathToRunRight;
    protected Animation hit;
    protected Animation die;
    protected HealthComponent health;
    protected HitboxComponent hitBox;
    protected int lifePoints;
    protected float xSpeed;
    protected float ySpeed;
    protected boolean diagonal;
    protected int attackDamage;


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

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }


    public int getLifePoints() {
        return lifePoints;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
}
