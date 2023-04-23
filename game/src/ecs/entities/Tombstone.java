package ecs.entities;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import graphic.Animation;
import starter.Game;

import java.util.Random;

public class Tombstone extends Entity implements IInteraction {

    private int healthEffect;
    private Random rand = new Random();

    public Tombstone() {
        super();
        Animation idle = AnimationBuilder.buildAnimation("ghost/tombstone");
        AnimationComponent animationComponent = new AnimationComponent(this,idle);
        new InteractionComponent(this,0.4F,false,this);
        new PositionComponent(this);
        healthEffect = rand.nextInt(16);
    }

    public void giveEffect() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent healthComponent = (HealthComponent) hero.getComponent(HealthComponent.class).get();
        int currentHP = healthComponent.getCurrentHealthpoints();
        System.out.println(currentHP);
        if(rand.nextInt(101)>=70) {
            healthComponent.setCurrentHealthpoints(currentHP+healthEffect);
        } else {
            healthComponent.setCurrentHealthpoints(currentHP-healthEffect);
        }
        System.out.println(currentHP);
    }

    @Override
    public void onInteraction(Entity entity) {
        giveEffect();
        System.out.println("Tombstone Interaction");
    }
}
