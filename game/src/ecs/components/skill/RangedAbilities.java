package ecs.components.skill;

import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.damage.Damage;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Point;

import static starter.Game.currentLevel;

public abstract class RangedAbilities extends RangeProjectileSkill {

        protected int damagerange;
        protected boolean bouncesOffWalls;

        public RangedAbilities( int damagerange,boolean bouncesOffWalls, String pathToTexturesOfProjectile,
                               float projectileSpeed, Damage projectileDamage, Point projectileHitboxSize,
                               ITargetSelection selectionFunction, float projectileRange)
        {
            super(pathToTexturesOfProjectile, projectileSpeed, projectileDamage, projectileHitboxSize, selectionFunction, projectileRange);
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
