package ecs.entities.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monsters.Monster;
import ecs.entities.characterclasses.Archer;
import ecs.entities.characterclasses.Rouge;
import ecs.entities.characterclasses.Tank;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import level.elements.tile.Tile;

public class Bananapeel extends Trap implements ICollide {

    public Bananapeel() {
        super();
        // 50% chance that a trap appears in a level
        visible = active = this.createRandomBooleanValue();
        this.damageValue = 10;

        // if it appears than build the animation and the hitbox
        if (visible && active) {
            this.idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeel.png");
            AnimationComponent animationComponent = new AnimationComponent(this, idle);
            new HitboxComponent(this, this, this);
        }
    }

    /**
     * @param damage is the damage a bananapeel can do to an entity by collision
     */
    public Bananapeel(int damage) {
        super();
        // 50% chance that a trap appears in a level
        visible = active = this.createRandomBooleanValue();
        damageValue = damage;

        // if it appears than build the animation and the hitbox
        if (visible && active) {
            this.idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeel.png");
            new AnimationComponent(this, idle);
            new HitboxComponent(this, this, this);
        }
    }

    /**
     * @param a is the current Entity
     * @param b is the Entity with whom the Collision happened
     * @param from the direction from a to b
     */
    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        Hero hero = null;
        // Makes sure the functions only runs if the entities are not null.
        if (a == null || b == null) return;
        // Same here but in this case it checks is the trap still active?
        if (!active) return;

        if (b instanceof Hero || b instanceof Monster) {

            // gets the components from the b entity
            HealthComponent healthComponent =
                    (HealthComponent)
                            b.getComponent(HealthComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new IllegalStateException(
                                                            "Entity does not have a HealthComponent"));

            AnimationComponent animationComponent =
                    (AnimationComponent)
                            b.getComponent(AnimationComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new IllegalStateException(
                                                            "Entity does not have a AnimationComponent"));

            VelocityComponent velocityComponent =
                    (VelocityComponent)
                            b.getComponent(VelocityComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new IllegalStateException(
                                                            "Entity does not have a VelocityComponent"));

            // gets the Currenthealthpoints from b
            int healthpoints = healthComponent.getCurrentHealthpoints();

            // sets the new healthpoints after the damage
            healthComponent.receiveHit(new Damage(damageValue, DamageType.PHYSICAL, this));
            // Original Speed from Hero
            float xSpeed = 0;
            float ySpeed = 0;
            // sets the new X,Y Velocity of b
            velocityComponent.setYVelocity(velocityComponent.getYVelocity() / 10);
            velocityComponent.setXVelocity(velocityComponent.getXVelocity() / 10);

            // in case b is the hero - animations will be setted
            if (b instanceof Hero) {
                hero = (Hero) b;
                xSpeed = hero.getxSpeed();
                ySpeed = hero.getySpeed();
                if (hero instanceof Archer) {
                    animationComponent.setIdleLeft(
                            AnimationBuilder.buildAnimation("archer/blood_idleLeft"));
                    animationComponent.setIdleRight(
                            AnimationBuilder.buildAnimation("archer/blood_idleRight"));
                    velocityComponent.setMoveRightAnimation(
                            AnimationBuilder.buildAnimation("archer/blood_runRight"));
                    velocityComponent.setMoveLeftAnimation(
                            AnimationBuilder.buildAnimation("archer/blood_runLeft"));
                } else if (hero instanceof Tank) {
                    animationComponent.setIdleLeft(
                            AnimationBuilder.buildAnimation("tank/blood_idleLeft"));
                    animationComponent.setIdleRight(
                            AnimationBuilder.buildAnimation("tank/blood_idleRight"));
                    velocityComponent.setMoveRightAnimation(
                            AnimationBuilder.buildAnimation("tank/blood_runRight"));
                    velocityComponent.setMoveLeftAnimation(
                            AnimationBuilder.buildAnimation("tank/blood_runLeft"));
                } else if (hero instanceof Rouge) {
                    animationComponent.setIdleLeft(
                            AnimationBuilder.buildAnimation("rouge/blood_idleLeft"));
                    animationComponent.setIdleRight(
                            AnimationBuilder.buildAnimation("rouge/blood_idleRight"));
                    velocityComponent.setMoveRightAnimation(
                            AnimationBuilder.buildAnimation("rouge/blood_runRight"));
                    velocityComponent.setMoveLeftAnimation(
                            AnimationBuilder.buildAnimation("rouge/blood_runLeft"));
                }
            }
            // set the new crushed animations for the banana peel.
            idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeelcrushed.png");
            new AnimationComponent(this, idle);

            // new Timer which sets the Velocity to the originalVelocity after a delay of 5 seconds.
            Timer timer = new Timer();
            Hero finalHero = hero;
            float finalXSpeed = xSpeed;
            float finalYSpeed = ySpeed;
            timer.schedule(
                    new TimerTask() {
                        public void run() {
                            resetPlayerVelocity(
                                    timer,
                                    finalXSpeed,
                                    finalYSpeed,
                                    animationComponent,
                                    velocityComponent,
                                    finalHero);
                        }
                    },
                    5 * 1000);

            // defines that the trap can be triggered just once.
            active = false;
            traplogger.log(Level.INFO, getClass().getSimpleName() + " activated");
            startTimer(hero, xSpeed, ySpeed, animationComponent, velocityComponent);
        }
    }

    /**
     * @param timer Timer object
     * @param originalXVelocity is the X-Velocity before the trap was triggered
     * @param originalYVelocity is the X-Velocity before the trap was triggered
     * @param animationComponent is the animationComponent of the b entity
     * @param velocityComponent is the velocityComponent of the b entity
     * @param finalHero is the hero
     */
    private void resetPlayerVelocity(
            Timer timer,
            float originalXVelocity,
            float originalYVelocity,
            AnimationComponent animationComponent,
            VelocityComponent velocityComponent,
            Hero finalHero) {

        velocityComponent.setYVelocity(originalYVelocity);
        velocityComponent.setXVelocity(originalXVelocity);
        if (finalHero != null) {
            if (finalHero instanceof Archer) {
                animationComponent.setIdleLeft(AnimationBuilder.buildAnimation("archer/idleLeft"));
                animationComponent.setIdleRight(
                        AnimationBuilder.buildAnimation("archer/idleRight"));
                velocityComponent.setMoveRightAnimation(
                        AnimationBuilder.buildAnimation("archer/runRight"));
                velocityComponent.setMoveLeftAnimation(
                        AnimationBuilder.buildAnimation("archer/runLeft"));
            } else if (finalHero instanceof Tank) {
                animationComponent.setIdleLeft(AnimationBuilder.buildAnimation("tank/idleLeft"));
                animationComponent.setIdleRight(AnimationBuilder.buildAnimation("tank/idleRight"));
                velocityComponent.setMoveRightAnimation(
                        AnimationBuilder.buildAnimation("tank/runRight"));
                velocityComponent.setMoveLeftAnimation(
                        AnimationBuilder.buildAnimation("tank/runLeft"));
            } else if (finalHero instanceof Rouge) {
                animationComponent.setIdleLeft(AnimationBuilder.buildAnimation("rouge/idleLeft"));
                animationComponent.setIdleRight(AnimationBuilder.buildAnimation("rouge/idleRight"));
                velocityComponent.setMoveRightAnimation(
                        AnimationBuilder.buildAnimation("rouge/runRight"));
                velocityComponent.setMoveLeftAnimation(
                        AnimationBuilder.buildAnimation("rouge/runLeft"));
            }
        }
        timer.cancel();
    }

    private void startTimer(
            Hero hero,
            float xSpeed,
            float ySpeed,
            AnimationComponent animationComponent,
            VelocityComponent velocityComponent) {
        // new Timer which sets the Velocity to the originalVelocity after a delay of 5 seconds.
        Timer timer = new Timer();
        Hero finalHero = hero;
        float finalXSpeed = xSpeed;
        float finalYSpeed = ySpeed;
        timer.schedule(
                new TimerTask() {
                    public void run() {
                        resetPlayerVelocity(
                                timer,
                                finalXSpeed,
                                finalYSpeed,
                                animationComponent,
                                velocityComponent,
                                finalHero);
                    }
                },
                5 * 1000);

        // defines that the trap can be triggered just once.
        active = false;
    }
}
