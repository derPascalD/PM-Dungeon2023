package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monsters.Monster;
import graphic.Animation;
import starter.Game;
import tools.Point;

public abstract class CombatAttackSkills implements ISkillFunction {

    private String pathCombatRight;
    private String pathCombatLeft;
    private float combatRange;
    private Point hitBoxSize;
    private Damage combatDamage;
    private Point checkSetPoint;
    private Point weaponStartPoint;

    public CombatAttackSkills(
            String pathCombatLeft,
            String pathCombatRight,
            Point hitBoxSize,
            Damage combatDamage,
            float combatRange) {
        this.pathCombatRight = pathCombatRight;
        this.pathCombatLeft = pathCombatLeft;
        this.hitBoxSize = hitBoxSize;
        this.combatDamage = combatDamage;
        this.combatRange = combatRange;
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

        if (ac.getCurrentAnimation() == ac.getIdleLeft()) {
            weaponStartPoint = new Point(epc.getPosition().x - 1, epc.getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathCombatLeft);
        } else if (ac.getCurrentAnimation() == ac.getIdleRight()) {
            weaponStartPoint = new Point(epc.getPosition().x + 0.2F, epc.getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathCombatRight);
        } else {
            return;
        }
        new PositionComponent(weapon, weaponStartPoint);
        new AnimationComponent(weapon, currentAnimation);
        new VelocityComponent(weapon, 0.08F, 0, currentAnimation, currentAnimation);

        // Position from the Weapon

        new ProjectileComponent(
                weapon,
                weaponStartPoint,
                new Point(weaponStartPoint.x - combatRange, weaponStartPoint.y));

        ICollide collide =
                (a, b, from) -> {
                    if (b != entity && b instanceof Hero) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(combatDamage);
                                            System.out.println("ONeB ist "+entity.getClass().getSimpleName());
                                            System.out.println(((HealthComponent) hc).getCurrentHealthpoints());

                                        });
                    }
                    if (b != entity && b instanceof Monster) {
                        b.getComponent(HealthComponent.class)
                            .ifPresent(
                                hc -> {
                                    ((HealthComponent) hc).receiveHit(combatDamage);
                                    System.out.println("TwoB ist "+entity.getClass().getSimpleName());
                                });
                    }

                    if (b != entity && ac.getCurrentAnimation() == ac.getIdleRight()) {
                        b.getComponent(PositionComponent.class)
                                .ifPresent(
                                        pc -> {
                                            checkSetPoint =
                                                    new Point(
                                                            ((PositionComponent) pc).getPosition().x
                                                                    + 0.5F,
                                                            ((PositionComponent) pc)
                                                                    .getPosition()
                                                                    .y);
                                            if (Game.currentLevel
                                                            .getTileAt(
                                                                    checkSetPoint.toCoordinate())
                                                            .isAccessible()
                                                    && (b instanceof Monster
                                                            || b instanceof Hero)) {
                                                ((PositionComponent) pc)
                                                        .setPosition(
                                                                new Point(
                                                                        ((PositionComponent) pc)
                                                                                        .getPosition()
                                                                                        .x
                                                                                + 0.5F,
                                                                        ((PositionComponent) pc)
                                                                                .getPosition()
                                                                                .y));
                                            } else {
                                            }
                                        });
                    } else if (b != entity
                            && ac.getCurrentAnimation() == ac.getIdleLeft()
                            && (b instanceof Monster || b instanceof Hero)) {
                        b.getComponent(PositionComponent.class)
                                .ifPresent(
                                        pc -> {
                                            checkSetPoint =
                                                    new Point(
                                                            ((PositionComponent) pc).getPosition().x
                                                                    - 0.5F,
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
                                                                                - 0.5F,
                                                                        ((PositionComponent) pc)
                                                                                .getPosition()
                                                                                .y));
                                            } else {
                                            }
                                        });
                    }
                };

        new HitboxComponent(weapon, new Point(0.25f, 0.25f), hitBoxSize, collide, null);
    }
}
