package ecs.entities;

import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.ITransition;
import ecs.systems.AISystem;

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
        new HealthComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();

        new AIComponent(this);
        new AISystem();
    }
}
