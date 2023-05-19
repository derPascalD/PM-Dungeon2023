package ecs.components.skill;

import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.damage.Damage;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Point;

import static starter.Game.currentLevel;

public abstract class RangedAbilities extends RangeProjecteSkill {

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

    /**
     * Applies a knock-back to the target entity based on the entity's distance and direction from the target.
     *
     * @param target is the target entity
     * @param entity is the entity which uses the skill
     * @param knockbackDistance is the distance of the knock-back
     */
    public void applyKnockback(Entity target, Entity entity, float knockbackDistance) {
        PositionComponent targetPositionComponent =
            (PositionComponent) target.getComponent(PositionComponent.class)
                .orElseThrow(
                    () -> new MissingComponentException("PositionComponent for target"));
        PositionComponent entityPositionComponent =
            (PositionComponent) entity.getComponent(PositionComponent.class)
                .orElseThrow(
                    () -> new MissingComponentException("PositionComponent for entity"));

        Point direction = Point.getUnitDirectionalVector(targetPositionComponent.getPosition(), entityPositionComponent.getPosition());

        Point newPosition = new Point(

            targetPositionComponent.getPosition().x + direction.x * knockbackDistance,
            targetPositionComponent.getPosition().y + direction.y * knockbackDistance
        );

        Tile newTile = currentLevel.getTileAt(newPosition.toCoordinate());
        if (newTile.isAccessible()) {
            targetPositionComponent.setPosition(newPosition);
        }

    }
}
