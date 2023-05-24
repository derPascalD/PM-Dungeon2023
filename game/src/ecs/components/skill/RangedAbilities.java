package ecs.components.skill;

import ecs.damage.Damage;
import tools.Point;

public abstract class RangedAbilities extends RangeProjectileSkill {

    protected long skilllearnedLevel;

    protected boolean bouncesOffWalls;

    public RangedAbilities(
            long skilllearnedLevel,
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
        this.bouncesOffWalls = bouncesOffWalls;
        this.skilllearnedLevel = skilllearnedLevel;
    }

    public long getSkilllearnedLevel() {
        return skilllearnedLevel;
    }

    public void setSkilllearnedLevel(long skilllearnedLevel) {
        this.skilllearnedLevel = skilllearnedLevel;
    }

    public boolean isBouncesOffWalls() {
        return bouncesOffWalls;
    }

    public void setBouncesOffWalls(boolean bouncesOffWalls) {
        this.bouncesOffWalls = bouncesOffWalls;
    }
}
