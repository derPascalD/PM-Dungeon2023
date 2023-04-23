package ecs.entities;

import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.systems.AISystem;

public class Dragon extends Monster{

    public Dragon(){
        this.xSpeed = 0.1f;
        this.ySpeed = 0.1f;
        this.diagonal = true;
        this.pathToIdleLeft = "monster/dragon/idleLeft";
        this.pathToIdleRight = "monster/dragon/idleRight";
        this.pathToRunLeft = "monster/dragon/runLeft";
        this.pathToRunRight = "monster/dragon/runRight";

        new PositionComponent(this);
        new HealthComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        new AIComponent(this, new CollideAI(0f), new PatrouilleWalk(4f,4,2000, PatrouilleWalk.MODE.RANDOM),new RangeTransition(2f));

    }

    @Override
    void attack() {

    }
}
