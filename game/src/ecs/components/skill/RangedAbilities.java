package ecs.components.skill;

import ecs.damage.Damage;
import tools.Point;

public abstract class RangedAbilities extends RangeProjectileSkill {

    protected int damagerange;
    protected boolean bouncesOffWalls;

    public RangedAbilities(
            int damagerange,
            boolean bouncesOffWalls,
            String pathToTexturesOfProjectile,
            float projectileSpeed,
            Damage projectileDamage,
            Point projectileHitboxSize,
            ITargetSelection selectionFunction,
            float projectileRange) {
        super(
                pathToTexturesOfProjectile,
                projectileSpeed,
                projectileDamage,
                projectileHitboxSize,
                selectionFunction,
                projectileRange);
        this.damagerange = damagerange;
        this.bouncesOffWalls = bouncesOffWalls;
    }

    public int getDamagerange() {
        return damagerange;
    }

    public boolean doesBounceOffWalls() {
        return bouncesOffWalls;
    }

    public void setDamagerange(int damagerange) {
        this.damagerange = damagerange;
    }
}
