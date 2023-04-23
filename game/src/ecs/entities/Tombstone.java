package ecs.entities;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import graphic.Animation;
import starter.Game;

import java.util.Random;

public class Tombstone extends Entity implements IInteraction {

    private int healthAmount;
    private Random rand = new Random();

    public Tombstone() {
        super();
        Animation idle = AnimationBuilder.buildAnimation("ghost/tombstone");
        AnimationComponent animationComponent = new AnimationComponent(this,idle);
        new InteractionComponent(this,0.4F,false,this);
        new PositionComponent(this);
        setupHealthAmount();
    }

    /**
     *  Setting up the health amount for the giveEffect methode
     */
    public void setupHealthAmount() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent healthComponent = (HealthComponent) hero.getComponent(HealthComponent.class).get();
        int HPpercent = healthComponent.getCurrentHealthpoints()/100;
        healthAmount = rand.nextInt(5);
        healthAmount = HPpercent* healthAmount;
    }

    /**
     *  Either gives the Hero the amount of health in healthAmount or takes the amount away
     */
    public void giveEffect() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent healthComponent = (HealthComponent) hero.getComponent(HealthComponent.class).get();
        int currentHP = healthComponent.getCurrentHealthpoints();
        System.out.println(currentHP);
        if(rand.nextInt(101)>=70) {
            healthComponent.setCurrentHealthpoints(currentHP + healthAmount);
        } else {
            healthComponent.setCurrentHealthpoints(currentHP - healthAmount);
        }
        System.out.println(currentHP);
    }

    /**
     * Gives the healthEffect to the Hero upon interacting with the tombstone
     *
     * @param entity the entity that is interacted with
     */
    @Override
    public void onInteraction(Entity entity) {
        giveEffect();
        System.out.println("Tombstone Interaction");
    }
}
