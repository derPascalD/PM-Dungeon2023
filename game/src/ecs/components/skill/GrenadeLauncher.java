package ecs.components.skill;

import ecs.entities.Entity;

public class GrenadeLauncher extends RangedAbilities{
    public GrenadeLauncher(int damage, int range, float speed, boolean bouncesOffWalls) {
        super(damage, range, speed, bouncesOffWalls);
    }

    @Override
    public void execute(Entity entity) {

    }
}
