package ecs.components.skill;

import ecs.damage.Damage;
import ecs.entities.Entity;
import tools.Point;

public class GrenadeLauncher extends RangedAbilities{


    public GrenadeLauncher(int damagerange, boolean bouncesOffWalls, String pathToTexturesOfProjectile,
                           float projectileSpeed, Damage projectileDamage, Point projectileHitboxSize,
                           ITargetSelection selectionFunction, float projectileRange) {

        super(damagerange, bouncesOffWalls, pathToTexturesOfProjectile, projectileSpeed,
            projectileDamage, projectileHitboxSize, selectionFunction, projectileRange);
    }



    @Override
    public void execute(Entity entity) {

        //TODO
        super.execute(entity);
    }
}

