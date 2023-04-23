package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.ghost.FollowHeroAI;
import ecs.components.ai.ghost.RandomWalk;
import graphic.Animation;
import starter.Game;

import java.util.Random;

public class Ghost extends Entity {


    private Random rand = new Random();
    Animation idle;

    public Ghost() {
        super();
        setupAnimation();
        setupPosition();
        setupBehaviour();
    }

    public void setupPosition() {
        Hero hero = (Hero) Game.getHero().get();
        PositionComponent heroPos = (PositionComponent) hero.getComponent(PositionComponent.class).get();
        new PositionComponent(this,heroPos.getPosition());
    }

    public void setupAnimation() {
        idle = AnimationBuilder.buildAnimation("ghost/npc");
        AnimationComponent animationComponent = new AnimationComponent(this,idle);
    }

    public void setupBehaviour() {
        if(rand.nextInt(101)>=50) followHero();
        else moveRandom();
    }

    public void followHero() {
        FollowHeroAI followHeroAI = new FollowHeroAI();
        new VelocityComponent(this,0.3f,0.3F,idle,idle);
        new AIComponent(this,
            null,
            followHeroAI,
            followHeroAI);
        new Tombstone();
    }

    public void moveRandom() {
        RandomWalk randomWalk = new RandomWalk();
        new VelocityComponent(this,0.04f,0.04F,idle,idle);
        new AIComponent(this,
            null,
            randomWalk,
            randomWalk);
    }
}
