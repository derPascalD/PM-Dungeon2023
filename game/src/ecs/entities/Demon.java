package ecs.entities;

import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.ITransition;

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
        new AIComponent(this).setTransitionAI(new ITransition() {
            @Override
            public boolean isInFightMode(Entity entity) {
                return false;
            }
        });
    }
}
