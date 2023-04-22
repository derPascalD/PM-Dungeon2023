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

        if (a == null || b == null) return;
        if (!active) return;


        var optionalHealth = b.getComponent(HealthComponent.class);
        if (!optionalHealth.isPresent()) return;

        HealthComponent  healthComponent = (HealthComponent) optionalHealth.get();
        int healthpoints = healthComponent.getCurrentHealthpoints();
        System.out.println("actuall healthhpoints: " +  healthpoints);

        healthComponent.setCurrentHealthpoints(healthpoints = healthpoints - 10);
        System.out.println("new healthpoints: "+ healthpoints);

        var optionalVelocity = b.getComponent(VelocityComponent.class);
        if (!optionalVelocity.isPresent()) return;

        var velocityComponent = (VelocityComponent) optionalVelocity.get();


        float orginialXVelocity = velocityComponent.getXVelocity();
        float orginialYVelocity = velocityComponent.getYVelocity();

        velocityComponent.setYVelocity(velocityComponent.getYVelocity()/10);
        velocityComponent.setXVelocity(velocityComponent.getXVelocity()/10);



        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                velocityComponent.setYVelocity(orginialYVelocity);
                velocityComponent.setXVelocity(orginialXVelocity);
                System.out.println("5s Sekunden sind vorbei. Der Spieler hat jetzt wieder die selbe Geschwindigkeit.");
                timer.cancel();
            }
        }, 5*1000);

        active = false;

    }

}
