package ecs.entities;

import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.ITransition;

public class Zombie extends Monster{

    public Zombie(){
        this.xSpeed = 0.01f;
        this.ySpeed = 0.01f;
        this.diagonal = false;
        this.lifePoints = 2;
        this.pathToIdleLeft = "monster/zombie/idleLeft";
        this.pathToIdleRight = "monster/zombie/idleRight";
        this.pathToRunLeft = "monster/zombie/runLeft";
        this.pathToRunRight = "monster/zombie/runRight";

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
