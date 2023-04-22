package ecs.entities;

import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
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
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        new AIComponent(this, new CollideAI(0f), new PatrouilleWalk(3f,4,1000, PatrouilleWalk.MODE.BACK_AND_FORTH),new RangeTransition(3f));
    }
    private void setupHitboxComponent() {
       HitboxComponent hit =  new HitboxComponent(
            this,
            (you, hero, direction) -> System.out.println("Kaempfen"),
            (you, hero, direction) -> System.out.println("Defensiv"));
    }
}
