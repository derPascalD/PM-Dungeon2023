package ecs.entities.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import level.elements.tile.Tile;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Giftwolke extends Trap implements ICollide {

    public Giftwolke() {
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

        float originalYVelocity = velocityComponent.getYVelocity();
        float originalXVelocity = velocityComponent.getXVelocity();

        // sets the new X,Y Velocity of b
        velocityComponent.setYVelocity(velocityComponent.getYVelocity() / 2);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity() / 2);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                velocityComponent.setYVelocity(originalYVelocity);
                velocityComponent.setXVelocity(originalXVelocity);
                System.out.println("10 Sekunden sind vorbei. Der Spieler hat jetzt wieder die selbe Geschwindigkeit.");
            }
        }, 10 * 1000);

        // defines that the trap can be triggered just once.
        active = false;
    }
}

