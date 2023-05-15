package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public abstract class CombatAttackSkills implements ISkillFunction {

    private final String pathCombatRight;
    private final String pathCombatLeft;
    private final Damage combatDamage;
    private Point checkSetPoint;

    public CombatAttackSkills(String pathCombatLeft, String pathCombatRight, Damage combatDamage) {
        this.pathCombatRight = pathCombatRight;
        this.pathCombatLeft = pathCombatLeft;
        this.combatDamage = combatDamage;
    }

    @Override
    public void execute(Entity entity) {
        Animation currentAnimation;
        Entity weapon = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));

        AnimationComponent ac =
                (AnimationComponent)
                        entity.getComponent(AnimationComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("AnimationComponent"));
        float leftRight;
        Point weaponStartPoint;
        if (ac.getCurrentAnimation() == ac.getIdleLeft()) {
            weaponStartPoint = new Point(epc.getPosition().x - 0.5F, epc.getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathCombatLeft);
            leftRight = -1;
        } else if (ac.getCurrentAnimation() == ac.getIdleRight()) {
            weaponStartPoint = new Point(epc.getPosition().x + 0.2F, epc.getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathCombatRight);
            leftRight = 1;
        } else {
            return;
        }
        new PositionComponent(weapon, weaponStartPoint);
        new AnimationComponent(weapon, currentAnimation);
        new VelocityComponent(weapon, (0.08F * leftRight), 0, currentAnimation, currentAnimation);
        // Position from the Weapon
        new ProjectileComponent(
                weapon,
                weaponStartPoint,
                new Point(weaponStartPoint.x + (0.5F) * leftRight, weaponStartPoint.y));

        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(combatDamage);
                                        });
                    }

                    if (b != entity && ac.getCurrentAnimation() == ac.getIdleRight()) {
                        b.getComponent(PositionComponent.class)
                                .ifPresent(
                                        pc -> {
                                            checkSetPoint =
                                                    new Point(
                                                            ((PositionComponent) pc).getPosition().x
                                                                    + 1F,
                                                            ((PositionComponent) pc)
                                                                    .getPosition()
                                                                    .y);
                                            if (Game.currentLevel
                                                    .getTileAt(checkSetPoint.toCoordinate())
                                                    .isAccessible()) {
                                                ((PositionComponent) pc)
                                                        .setPosition(
                                                                new Point(
                                                                        ((PositionComponent) pc)
                                                                                        .getPosition()
                                                                                        .x
                                                                                + 1F,
                                                                        ((PositionComponent) pc)
                                                                                .getPosition()
                                                                                .y));
                                            }
                                        });
                    } else if (b != entity && ac.getCurrentAnimation() == ac.getIdleLeft()) {
                        b.getComponent(PositionComponent.class)
                                .ifPresent(
                                        pc -> {
                                            checkSetPoint =
                                                    new Point(
                                                            ((PositionComponent) pc).getPosition().x
                                                                    - 1F,
                                                            ((PositionComponent) pc)
                                                                    .getPosition()
                                                                    .y);
                                            if (Game.currentLevel
                                                    .getTileAt(checkSetPoint.toCoordinate())
                                                    .isAccessible()) {
                                                ((PositionComponent) pc)
                                                        .setPosition(
                                                                new Point(
                                                                        ((PositionComponent) pc)
                                                                                        .getPosition()
                                                                                        .x
                                                                                - 1F,
                                                                        ((PositionComponent) pc)
                                                                                .getPosition()
                                                                                .y));
                                            }
                                        });
                    }
                };

        new HitboxComponent(weapon, collide, null);
    }
}
