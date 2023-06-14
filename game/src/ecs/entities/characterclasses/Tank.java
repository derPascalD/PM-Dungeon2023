package ecs.entities.characterclasses;

import dslToGame.AnimationBuilder;
import ecs.components.DamageComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Hero;
import ecs.items.ImplementedItems.Healthpot;
import ecs.items.ItemType;

/** Represents the Tank Class */
public class Tank extends Hero {
    /** Creates Components, starting Items and the skills for the Tank */
    public Tank() {
        setupComponents();
        setupStartItems();
        setupSkills();
    }

    private void setupComponents() {
        new HealthComponent(this, 100, this, hitAnimation(), hitAnimation());
        new DamageComponent(this, 1, 0);
        setupVelocity();
    }

    private void setupVelocity() {
        new VelocityComponent(this, 0.15f, 0.15f, moveLeft, moveRight);
    }

    private void setupStartItems() {
        for (int i = 0; i < 5; i++) {
            this.inventory.addItem(
                    new Healthpot(
                            ItemType.Healing,
                            AnimationBuilder.buildAnimation("items.healthpot"),
                            AnimationBuilder.buildAnimation("items.healthpot"),
                            "Healthpot",
                            "Heals the Player on Use"));
        }
    }

    private void setupSkills() {
        setupMeleeSkill();
    }
}
