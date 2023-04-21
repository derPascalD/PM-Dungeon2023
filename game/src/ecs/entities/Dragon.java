package ecs.entities;

import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.ITransition;

public class Dragon extends Monster{

    public Dragon(){
        this.xSpeed = 0.2f;
        this.ySpeed = 0.2f;
        this.diagonal = true;
        this.lifePoints = 8;
        this.pathToIdleLeft = "monster/imp/idleLeft";
        this.pathToIdleRight = "monster/imp/idleRight";
        this.pathToRunLeft = "monster/imp/runLeft";
        this.pathToRunRight = "monster/imp/runRight";

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
