package ecs.entities.characterclasses;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.DamageComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Hero;
import graphic.Animation;

/** Represents the Rouge Class */
public class Rouge extends Hero {
    /** Creates Components and the skills for the Rouge */
    public Rouge() {
        setupComponents();
        setupSkills();
    }

    private void setupComponents() {
        new HealthComponent(this, 15, this, hitAnimation(), hitAnimation());
        new DamageComponent(this, 4, 0);
        setupVelocity();
        setupAnimation();
    }

    private void setupVelocity() {
        Animation moveLeft = AnimationBuilder.buildAnimation("rouge/idleCombatLeft");
        Animation moveRight = AnimationBuilder.buildAnimation("rouge/idleCombatRight");
        new VelocityComponent(this, 0.4f, 0.4f, moveLeft, moveRight);
    }

    private void setupAnimation() {
        Animation idleLeft = AnimationBuilder.buildAnimation("rouge/idleCombatLeft");
        Animation idleRight = AnimationBuilder.buildAnimation("rouge/idleCombatRight");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupSkills() {
        setupMeleeSkill();
    }
}
