package ecs.entities;

import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.systems.AISystem;

public class Zombie extends Monster{

    public Zombie(){
        this.xSpeed = 0.04f;
        this.ySpeed = 0.04f;
        this.diagonal = false;
        this.pathToIdleLeft = "monster/zombie/idleLeft";
        this.pathToIdleRight = "monster/zombie/idleRight";
        this.pathToRunLeft = "monster/zombie/runLeft";
        this.pathToRunRight = "monster/zombie/runRight";

        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();

        new AIComponent(this, new CollideAI(0f), new PatrouilleWalk(3f,2,1000, PatrouilleWalk.MODE.BACK_AND_FORTH),new RangeTransition(1f));

    }

    @Override
    void attack() {

    }

}
