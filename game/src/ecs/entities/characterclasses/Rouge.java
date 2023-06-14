package ecs.entities.characterclasses;

import ecs.components.DamageComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Hero;

public class Rouge extends Hero {

    public Rouge() {
        setupComponents();
        setupSkills();
    }

    private void setupComponents() {
        new HealthComponent(this, 15, this, hitAnimation(), hitAnimation());
        new DamageComponent(this, 4, 0);
        setupVelocity();
    }

    private void setupVelocity() {
        new VelocityComponent(this, 0.4f, 0.4f ,moveLeft, moveRight);
    }


    private void setupSkills() {
        setupMeleeSkill();
    }
}
