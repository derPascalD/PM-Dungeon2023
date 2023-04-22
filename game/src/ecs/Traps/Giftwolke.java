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




    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {

        if (a == null || b == null) return;

        if (!active) return;
        var optinalVelocity = b.getComponent(VelocityComponent.class);
        if (!optinalVelocity.isPresent()) return;

        var  velocityComponent = (VelocityComponent) optinalVelocity.get();
        velocityComponent.setYVelocity(velocityComponent.getYVelocity()/2);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity()/2);
        active = false;

    }


}

