package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.IOnDeathFunction;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.RangeTransition;



public class Demon extends Monster implements IOnDeathFunction {

    public Demon() {
        this.lifePoints = 8;
        this.xSpeed = 0.1f;
        this.ySpeed = 0.1f;
        this.diagonal = false;
        this.pathToIdleLeft = "monster/demon/idleLeft";
        this.pathToIdleRight = "monster/demon/idleRight";
        this.pathToRunLeft = "monster/demon/runLeft";
        this.pathToRunRight = "monster/demon/runRight";
        this.hit= AnimationBuilder.buildAnimation("monster/demon/hit");
        this.die = AnimationBuilder.buildAnimation("monster/demon/hit");


        new HealthComponent(this,
            8,
            this,
            hit,
            die);


        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();

        new HitboxComponent(this);




        new AIComponent(
            this,
            new CollideAI(0f),
            new PatrouilleWalk(3f,
                4,
                1000,
                PatrouilleWalk.MODE.BACK_AND_FORTH),
            new RangeTransition(2f));
    }

    @Override
    void attack() {

    }


    @Override
    public void onDeath(Entity entity) {
        System.out.println("Demon tot!");
    }


}
