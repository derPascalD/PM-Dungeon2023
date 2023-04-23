package ecs.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.VelocityComponent;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Trap;
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
            AnimationComponent animationComponent = new AnimationComponent(this,idle);
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

        // Makes sure the functions only runs if the entities are not null.
        if (a == null || b == null) return;
        // Same here but in this case it checks is the trap still active?
        if (!active) return;

        // gets the healthcomponent from the b entity
        var optionalHealth = b.getComponent(HealthComponent.class);
        if (!optionalHealth.isPresent()) return;
        HealthComponent  healthComponent = (HealthComponent) optionalHealth.get();

        // gets the Currenthealthpoints from b
        int healthpoints = healthComponent.getCurrentHealthpoints();
        System.out.println("actuall healthhpoints: " +  healthpoints);

        // sets the new healthpoints after the damage
        healthComponent.setCurrentHealthpoints(healthpoints = healthpoints - damageValue);
        System.out.println("new healthpoints: "+ healthpoints);

        // gets the Velocitycomponent from b
        var optionalVelocity = b.getComponent(VelocityComponent.class);
        if (!optionalVelocity.isPresent()) return;
        var velocityComponent = (VelocityComponent) optionalVelocity.get();

        // saves the originalVelocity
        float orginialXVelocity = velocityComponent.getXVelocity();
        float orginialYVelocity = velocityComponent.getYVelocity();

        // sets the new X,Y Velocity of b
        velocityComponent.setYVelocity(velocityComponent.getYVelocity()/10);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity()/10);


        // new Timer which sets the Velocity to the originalVelocity after a delay of 5 seconds.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                velocityComponent.setYVelocity(orginialYVelocity);
                velocityComponent.setXVelocity(orginialXVelocity);
                System.out.println("5s Sekunden sind vorbei. Der Spieler hat jetzt wieder die selbe Geschwindigkeit.");
                timer.cancel();
            }
        }, 5*1000);

        // defines that the trap can be triggered just once.
        active = false;

    }

}
