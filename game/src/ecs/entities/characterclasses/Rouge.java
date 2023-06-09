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
        new DamageComponent(this, 3, 0);
        setupVelocity();
    }

    private void setupVelocity() {
        xSpeed = ( xSpeed / 100 ) * 120;
        ySpeed = ( ySpeed / 100 ) * 120;
        new VelocityComponent(this, xSpeed, ySpeed,moveLeft, moveRight);
    }


    private void setupSkills() {
        setupMeleeSkill();
    }
}
