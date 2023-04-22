package ecs.entities;

import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.ITransition;
import ecs.systems.AISystem;

public class Demon extends Monster{

    public Demon(){
        this.xSpeed = 0.1f;
        this.ySpeed = 0.1f;
        this.diagonal = false;
        this.lifePoints = 3;
        this.pathToIdleLeft = "monster/demon/idleLeft";
        this.pathToIdleRight = "monster/demon/idleRight";
        this.pathToRunLeft = "monster/demon/runLeft";
        this.pathToRunRight = "monster/demon/runRight";

        new PositionComponent(this);
        HealthComponent health = new HealthComponent(this);

        HitboxComponent hit = new HitboxComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        new AIComponent(this);
    }
    private void setupHitboxComponent() {
       HitboxComponent hit =  new HitboxComponent(
            this,
            (you, hero, direction) -> System.out.println("Kaempfen"),
            (you, hero, direction) -> System.out.println("Defensiv"));
    }
}
