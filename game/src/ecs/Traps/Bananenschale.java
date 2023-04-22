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

public class Bananenschale extends Trap implements ICollide {

    public Bananenschale() {
        super();
        visible = active = this.createRandomBooleanValue();
        if (visible && active)
        {
            this.idle = AnimationBuilder.buildAnimation("traps/Bananenschale/bananapeel.png");
            AnimationComponent animationComponent = new AnimationComponent(this,idle);
            new HitboxComponent(this, this, this);
        }

    }

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {

        if (a != null || b != null) System.out.println(a.getClass().getName() +" "+ b.getClass().getName());;
        if (!active) return;

        var optinalHealth = b.getComponent(HealthComponent.class);
        if (!optinalHealth.isPresent()) System.exit(0);

        var  healthComponent = (HealthComponent) optinalHealth.get();
        int healthpoints = healthComponent.getCurrentHealthpoints();
        healthpoints = healthpoints - 2;
        healthComponent.setCurrentHealthpoints(healthpoints);
        visible = false;

    }
}
