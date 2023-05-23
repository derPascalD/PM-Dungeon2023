package ecs.components.skill;

import static starter.Game.currentLevel;

import com.badlogic.gdx.math.Vector2;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

import java.util.Vector;

public class GrenadeLauncher extends RangedAbilities {

    private final int AmountOfBounces = 4;

    public GrenadeLauncher(
            int damagerange,
            boolean bouncesOffWalls,
            String pathToTexturesOfProjectile,
            float projectileSpeed,
            Damage projectileDamage,
            Point projectileHitboxSize,
            ITargetSelection selectionFunction,
            float projectileRange) {

        super(
                damagerange,
                bouncesOffWalls,
                pathToTexturesOfProjectile,
                projectileSpeed,
                projectileDamage,
                projectileHitboxSize,
                selectionFunction,
                projectileRange);
    }

    /**
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {

        Entity projectile = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);

        aimedOn = selectionFunction.selectTargetPoint();
        targetPoint =
                SkillTools.calculateLastPositionInRange(
                        epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
                SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        VelocityComponent vc =
                new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);

        PositionComponent ppc = (PositionComponent) projectile.getComponent(PositionComponent.class).get();
        Point direction = Point.getUnitDirectionalVector(targetPoint, ppc.getPosition());



        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            Game.removeEntity(projectile);
                                            doKnockback(b, a, 3.25f);
                                        });
                    }
                };

        new HitboxComponent(
                projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }

    private void changeToLauncherAnimation(Entity entity) {
        if (entity.getClass() != Hero.class) {
            return;
        }
        AnimationComponent ac =
                (AnimationComponent) entity.getComponent(AnimationComponent.class).get();
        VelocityComponent vc =
                (VelocityComponent) entity.getComponent(VelocityComponent.class).get();

        ac.setIdleLeft(AnimationBuilder.buildAnimation("knight/launcher_idleLeft"));
        ac.setIdleRight(AnimationBuilder.buildAnimation("knight/launcher_idleRight"));
        vc.setMoveRightAnimation(AnimationBuilder.buildAnimation("knight/launcher_runRight"));
        vc.setMoveLeftAnimation(AnimationBuilder.buildAnimation("knight/launcher_runLeft"));
    }

    public void resetGrenadeLauncherAnimation(Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof Hero hero) {
            if (hero.getComponent(AnimationComponent.class).isEmpty()) {
                return;
            }
            if (hero.getComponent(VelocityComponent.class).isEmpty()) {
                return;
            }
            AnimationComponent ac =
                    (AnimationComponent) entity.getComponent(AnimationComponent.class).get();
            VelocityComponent vc =
                    (VelocityComponent) entity.getComponent(VelocityComponent.class).get();

            ac.setIdleLeft(AnimationBuilder.buildAnimation(hero.getPathToIdleLeft()));
            ac.setIdleRight(AnimationBuilder.buildAnimation(hero.getPathToIdleRight()));
            vc.setMoveRightAnimation(AnimationBuilder.buildAnimation(hero.getPathToRunRight()));
            vc.setMoveLeftAnimation(AnimationBuilder.buildAnimation(hero.getPathToRunLeft()));
        }
    }

    private  Point reflectDirection(Point vector) {
        return new Point(-vector.x, -vector.y);
    }
}
