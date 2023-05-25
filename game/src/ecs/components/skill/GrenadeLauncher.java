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
import starter.Game;
import tools.Point;

public class GrenadeLauncher extends RangedAbilities {

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

        changeToLauncherAnimation(entity);
        Entity projectile = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        PositionComponent pc = new PositionComponent(projectile, epc.getPosition());

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

        if (entity instanceof Hero hero) {
            DamageComponent dC = (DamageComponent) hero.getComponent(DamageComponent.class).get();
            projectileDamage = new Damage(dC.getRangeDamage(), projectileDamage.damageType(), null);
        }

        collision(entity, projectile);
        throwBack(targetPoint, vc, epc, entity);
    }

    /**
     * @param point targetpoint
     * @param vC is the velocityComponent of the procetile
     * @param epc is the position component of the entity which uses the skill
     * @param entity is the entity which uses the skill
     */
    public void throwBack(Point point, VelocityComponent vC, PositionComponent epc, Entity entity) {
        if (currentLevel != null && !currentLevel.getTileAt(point.toCoordinate()).isAccessible()) {
            float xOffset = 1, yOffset = 1, xback = 1, yback = 1;
            if (vC.getXVelocity() > 0) {
                if (vC.getYVelocity() > 0) {
                    yOffset = -1;
                    xOffset = -1;
                    yback = -1;
                } else {
                    xOffset = -1;
                }
            } else if (vC.getXVelocity() < 0) {
                if (vC.getYVelocity() > 0) {
                    yOffset = -1;
                    yback = -1;
                    xback = -1;
                } else {
                    xback = -1;
                }
            }
            float temp = 0;
            Point tempP;
            while (true) {
                tempP = new Point(point.x + (temp * xOffset), point.y + (temp * yOffset));
                if (currentLevel != null
                        && currentLevel.getTileAt(tempP.toCoordinate()).isAccessible()) {
                    break;
                }
                temp += 0.001f;
            }
            RecreatingReflectedPrjectileComponent(epc, entity, xback, yback, tempP);
        }
    }

    private void RecreatingReflectedPrjectileComponent(
            PositionComponent epc, Entity entity, float xback, float yback, Point tempP) {
        Entity projectileBack = new Entity();
        new PositionComponent(projectileBack, tempP);
        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectileBack, animation);
        new VelocityComponent(projectileBack, xback * 0.1F, yback * 0.1F, animation, animation);
        new ProjectileComponent(projectileBack, tempP, epc.getPosition());

        collision(entity, projectileBack);
    }

    private void collision(Entity entity, Entity projectileBack) {
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            if ((((HealthComponent) hc).getCurrentHealthpoints() - projectileDamage.damageAmount()) <= 0
                                                && entity instanceof Hero hero) {
                                                hero.addKilledMonsters(b);
                                            }
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            Game.removeEntity(projectileBack);
                                            doKnockback(b, a, 2.25f);
                                        });
                    }
                };
        new HitboxComponent(
                projectileBack, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }

    /*
    actual not in usage for later use eventually.
     */
    private Point reflectDirection(Point vector, Point newPoint) {
        Vector2 vector2 = new Vector2(vector.x, vector.y);
        float dotproduct = vector2.dot(newPoint.x, newPoint.y);

        float reflectedX = vector.x - 2 * dotproduct * newPoint.x;
        float reflectedY = vector.y - 2 * dotproduct * newPoint.y;
        return new Point(reflectedX, reflectedY);
    }

    /*
     * changes the hero animation
     * @param entity is the entity which uses the skill
     */
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
}
