package ecs.entities.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Poisoncloud extends Trap implements ICollide {

    public Poisoncloud() {
        super();
        visible = active = this.createRandomBooleanValue();
        if (visible && active) {
            this.idle = AnimationBuilder.buildAnimation("traps/Wolke/clouds");
            new AnimationComponent(this, idle);
            new HitboxComponent(this, this, this);
        }
    }

    /**
     * @param a     is the current Entity
     * @param b     is the Entity with whom the Collision happened
     * @param from  the direction from a to b
     */
    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        // Makes sure the functions only runs if the entities are not null.
        if (a == null || b == null) return;

        // Same here but in this case it checks is the trap still active?
        if (!active) return;

        Optional<Component> optionalVelocity = b.getComponent(VelocityComponent.class);
        if (!optionalVelocity.isPresent()) return;

        VelocityComponent velocityComponent = (VelocityComponent) optionalVelocity.get();

        // Original Speed from Hero
        float xSpeed = 0;
        float ySpeed = 0;

        Hero hero = null;
        if (b instanceof Hero)
        {
            hero = (Hero) b;
            xSpeed = hero.getxSpeed();
            ySpeed = hero.getySpeed();
        }

        // sets the new X,Y Velocity of b
        velocityComponent.setYVelocity(velocityComponent.getYVelocity() / 2);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity() / 2);

        Timer timer = new Timer();
        Hero finalHero = hero;
        float finalXSpeed = xSpeed;
        float finalYSpeed = ySpeed;
        timer.schedule(new TimerTask() {
            public void run() {
                assert finalHero != null;
                velocityComponent.setYVelocity(finalXSpeed);
                velocityComponent.setXVelocity(finalYSpeed);
                System.out.println("10 seconds over. The velocity is set back to the original values.");
            }
        }, 10 * 1000);

        // defines that the trap can be triggered just once.
        active = false;
    }
}

