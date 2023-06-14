package ecs.entities.characterclasses;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.DamageComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Hero;
import ecs.items.ImplementedItems.Healthpot;
import ecs.items.ItemType;
import graphic.Animation;

/** Represents the Archer Class */
public class Archer extends Hero {

    /** Creates Components, starting Items and the skills for the Archer */
    public Archer() {
        setupComponents();
        setupStartItems();
        setupSkills();
    }

    private void setupComponents() {
        new HealthComponent(this, 30, this, hitAnimation(), hitAnimation());
        new DamageComponent(this, 1, 3);
        setupVelocity();
        setupAnimation();
    }

    private void setupVelocity() {
        Animation moveLeft = AnimationBuilder.buildAnimation("archer/runCombatLeft");
        Animation moveRight = AnimationBuilder.buildAnimation("archer/runCombatRight");
        new VelocityComponent(this, 0.25f, 0.25f, moveLeft, moveRight);
    }

    private void setupAnimation() {
        Animation idleLeft = AnimationBuilder.buildAnimation("archer/idleCombatLeft");
        Animation idleRight = AnimationBuilder.buildAnimation("archer/idleCombatRight");
        new AnimationComponent(this,idleLeft,idleRight);
    }

    private void setupStartItems() {
        this.inventory.addItem(
                new Healthpot(
                        ItemType.Healing,
                        AnimationBuilder.buildAnimation("items.healthpot"),
                        AnimationBuilder.buildAnimation("items.healthpot"),
                        "Healthpot",
                        "Heals the Player on Use"));
    }

    private void setupSkills() {
        setupNinjaBlade();
        setupGrenadeLauncherSkill();
        setupMeleeSkill();
    }
}
