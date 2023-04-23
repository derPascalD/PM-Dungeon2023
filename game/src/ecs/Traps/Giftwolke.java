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

        // sets the new X,Y Velocity of b
        velocityComponent.setYVelocity(velocityComponent.getYVelocity()/2);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity()/2);

        // defines that the trap can be triggered just once.
        active = false;

    }


}

