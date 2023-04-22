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
        //System.out.println(active + " " + visible);
        if (visible && active)
        {

            this.idle = AnimationBuilder.buildAnimation("traps/Wolke/clouds");
            AnimationComponent animationComponent = new AnimationComponent(this,idle);
            new HitboxComponent(this, this, this);
        }


    }




    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {

        if (a == null || b == null) System.out.println(a +" "+ b);;
        if (!active) return;
        var optinalVelocity = b.getComponent(VelocityComponent.class);
        if (!optinalVelocity.isPresent()) System.exit(0);

        var  velocityComponent = (VelocityComponent) optinalVelocity.get();
        //TODO: verlangsamen.

        active = false;

    }


}

