package ecs.components.skill;

import ecs.entities.Entity;

public class ThrowingStar extends RangedAbilities {
    public ThrowingStar(int damage, int range, float speed, boolean bouncesOffWalls) {
        super(damage, range, speed, bouncesOffWalls);
    }

    @Override
    public void execute(Entity entity) {

    }
}
