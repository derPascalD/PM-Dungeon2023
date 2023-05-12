package ecs.entities.NPCs;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import java.util.Random;
import starter.Game;

public class Tombstone extends Entity implements IInteraction {

    private int healthAmount;
    private Random rand = new Random();

    public Tombstone() {
        super();
        Animation idle = AnimationBuilder.buildAnimation("ghost/tombstone");
        AnimationComponent animationComponent = new AnimationComponent(this, idle);
        new InteractionComponent(this, 0.4F, false, this);
        new PositionComponent(this);
        setupHealthAmount();
    }

    /** Setting up the health amount for the giveEffect methode */
    public void setupHealthAmount() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent healthComponent =
                (HealthComponent) hero.getComponent(HealthComponent.class).get();
        double HPpercent = (double) healthComponent.getCurrentHealthpoints() / 100;
        healthAmount = rand.nextInt(20) + 1;
        healthAmount = (int) (HPpercent * healthAmount);
    }

    /** Either gives the Hero the amount of health in healthAmount or takes the amount away */
    public void giveEffect() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent healthComponent =
                (HealthComponent) hero.getComponent(HealthComponent.class).get();
        int currentHP = healthComponent.getCurrentHealthpoints();
        System.out.println(currentHP);
        // 70% that the Hero gets HP, 30% that the Hero loses HP
        if (rand.nextInt(101) >= 30) {
            healthComponent.setCurrentHealthpoints(currentHP + healthAmount);
        } else {
            healthComponent.setCurrentHealthpoints(currentHP - healthAmount);
        }
        System.out.println(healthComponent.getCurrentHealthpoints());
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
