package ecs.entities.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;

import java.util.Timer;
import java.util.TimerTask;



public class Bananenschale extends Trap implements ICollide {

    public Bananenschale() {
        super();

        // 50% chance that a trap appears in a level
        visible = active = this.createRandomBooleanValue();
        this.damageValue = 10;

        // if it appears than build the animation and the hitbox
        if (visible && active)
        {
            this.idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeel.png");
            AnimationComponent animationComponent = new AnimationComponent(this,idle);
            new HitboxComponent(this, this, this);
        }
    }

    /**
     *
     * @param damage is the damage a bananapeel can do to an entity by collision
     */
    public Bananenschale(int damage) {
        super();

        // 50% chance that a trap appears in a level
        visible = active = this.createRandomBooleanValue();
        damageValue = damage;


        // if it appears than build the animation and the hitbox
        if (visible && active)
        {
            this.idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeel.png");
            new AnimationComponent(this,idle);
            new HitboxComponent(this, this, this);
        }

    }

    /**
     *
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


        // gets the components from the b entity
        HealthComponent healthComponent = (HealthComponent) b.getComponent(HealthComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a HealthComponent"));

        AnimationComponent animationComponent = (AnimationComponent) b.getComponent(AnimationComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a AnimationComponent"));

        VelocityComponent velocityComponent = (VelocityComponent) b.getComponent(VelocityComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a VelocityComponent"));


        // gets the Currenthealthpoints from b
        int healthpoints = healthComponent.getCurrentHealthpoints();
        System.out.println("actuall healthhpoints: " +  healthpoints);

        // sets the new healthpoints after the damage
        healthComponent.setCurrentHealthpoints(healthpoints = healthpoints - damageValue);
        System.out.println("new healthpoints: "+ healthpoints);



        // sets the new X,Y Velocity of b
        velocityComponent.setYVelocity(velocityComponent.getYVelocity()/10);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity()/10);

        // in case b is the hero - animations will be setted
        if (b instanceof Hero)
        {
            hero = (Hero) b;

            animationComponent.setIdleLeft(AnimationBuilder.buildAnimation("knight/blood_idleLeft"));
            animationComponent.setIdleRight(AnimationBuilder.buildAnimation("knight/blood_idleRight"));
            velocityComponent.setMoveRightAnimation(AnimationBuilder.buildAnimation("knight/blood_runRight"));
            velocityComponent.setMoveLeftAnimation(AnimationBuilder.buildAnimation("knight/blood_runLeft"));
        }

        // set the new crushed animations for the banana peel.
        idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeelcrushed.png");
        new AnimationComponent(this,idle);



        // new Timer which sets the Velocity to the originalVelocity after a delay of 5 seconds.
        Timer timer = new Timer();
        Hero finalHero = hero;
        timer.schedule(new TimerTask() {
            public void run() {
                resetPlayerVelocity(timer, finalHero.getxSpeed(), finalHero.getySpeed(), animationComponent, velocityComponent, finalHero);
            }
        }, 5*1000);

        // defines that the trap can be triggered just once.
        active = false;

    }

    /**
     *
     * @param timer Timer object
     * @param originalXVelocity is the X-Velocity before the trap was triggered
     * @param originalYVelocity is the X-Velocity before the trap was triggered
     * @param animationComponent is the animationComponent of the b entity
     * @param velocityComponent is the velocityComponent of the b entity
     * @param finalHero is the hero
     */
    private void resetPlayerVelocity(Timer timer, float originalXVelocity, float originalYVelocity, AnimationComponent animationComponent, VelocityComponent velocityComponent, Hero finalHero) {
        velocityComponent.setYVelocity(originalYVelocity);
        velocityComponent.setXVelocity(originalXVelocity);
        if (finalHero != null)
        {
            animationComponent.setIdleLeft(AnimationBuilder.buildAnimation(finalHero.getPathToIdleLeft()));
            animationComponent.setIdleRight(AnimationBuilder.buildAnimation(finalHero.getPathToIdleRight()));
            velocityComponent.setMoveRightAnimation(AnimationBuilder.buildAnimation(finalHero.getPathToRunRight()));
            velocityComponent.setMoveLeftAnimation(AnimationBuilder.buildAnimation(finalHero.getPathToRunLeft()));
        }

        System.out.println("5s Sekunden sind vorbei. Der Spieler hat jetzt wieder die selbe Geschwindigkeit.");
        timer.cancel();
    }

}
