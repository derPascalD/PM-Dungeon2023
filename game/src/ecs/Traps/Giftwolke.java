package ecs.Traps;

import com.badlogic.gdx.utils.Null;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Trap;
import graphic.Animation;
import level.elements.tile.Tile;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Giftwolke extends Trap implements ICollide {


    public Giftwolke()
    {
        super();
        visible = active = this.createRandomBooleanValue();
        if (visible && active)
        {
            this.idle = AnimationBuilder.buildAnimation("traps/Wolke/clouds");
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

        // gets the Velocitycomponent from b
        var optinalVelocity = b.getComponent(VelocityComponent.class);
        if (!optinalVelocity.isPresent()) return;
        var  velocityComponent = (VelocityComponent) optinalVelocity.get();

        // gets the original X and Y Velocity
        float orginialYVelocity = velocityComponent.getYVelocity();
        float orginialXVelocity = velocityComponent.getXVelocity();

        // sets the new X,Y Velocity of b
        velocityComponent.setYVelocity(velocityComponent.getYVelocity()/2);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity()/2);


        // new Timer which sets the Velocity to the originalVelocity after a delay of 10 seconds.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                velocityComponent.setYVelocity(orginialYVelocity);
                velocityComponent.setXVelocity(orginialXVelocity);
                System.out.println("10 Sekunden sind vorbei. Der Spieler hat jetzt wieder die selbe Geschwindigkeit.");
                timer.cancel();
            }
        }, 10*1000);

        // defines that the trap can be triggered just once.
        active = false;

    }


}

