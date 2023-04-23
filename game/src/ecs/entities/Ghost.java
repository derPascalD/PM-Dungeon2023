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
    // random number generator variable
    private Random rand = new Random();
    Animation idle;

    public Ghost() {
        super();
        setupAnimation();
        setupPosition();
        setupBehaviour();
    }

    /**
     * Setting up the Position of the Ghost, always spawns where the Hero is
     */
    public void setupPosition() {
        Hero hero = (Hero) Game.getHero().get();
        PositionComponent heroPos = (PositionComponent) hero.getComponent(PositionComponent.class).get();
        new PositionComponent(this,heroPos.getPosition());
    }

    /**
     * Getting the corresponding Picture and setting up the Animation of the Ghost
     */
    public void setupAnimation() {
        idle = AnimationBuilder.buildAnimation("ghost/npc");
        AnimationComponent animationComponent = new AnimationComponent(this,idle);
    }

    /**
     * Setting up the Behaviour of the Ghost, he either follows the hero or walks randomly in the level
     */
    public void setupBehaviour() {
        if(rand.nextInt(101)>=50) followHero();
        else moveRandom();
    }

    /**
     * Setting up the Velocity and AI Behaviour so the Ghost follows the Hero. Also creates a Tombstone
     */
    public void followHero() {
        FollowHeroAI followHeroAI = new FollowHeroAI();
        new VelocityComponent(this,0.3f,0.3F,idle,idle);
        new AIComponent(this,
            null,
            followHeroAI,
            followHeroAI);
        new Tombstone();
    }
    /**
     * Ghost will move randomly in the Level
     */
    public void moveRandom() {
        RandomWalk randomWalk = new RandomWalk();
        new VelocityComponent(this,0.04f,0.04F,idle,idle);
        new AIComponent(this,
            null,
            randomWalk,
            randomWalk);
    }
}
