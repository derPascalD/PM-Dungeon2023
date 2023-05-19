package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class RangeProjecteSkill implements ISkillFunction {

    private String pathToTexturesOfProjectile;
    private float projectileSpeed;

    private float projectileRange;
    private Damage projectileDamage;
    private Point projectileHitboxSize;

    private Point targetPoint;

    private Point aimedOn;

    private ITargetSelection selectionFunction;

    public RangeProjecteSkill(
        String pathToTexturesOfProjectile,
        float projectileSpeed,
        Damage projectileDamage,
        Point projectileHitboxSize,
        ITargetSelection selectionFunction,
        float projectileRange) {
        this.pathToTexturesOfProjectile = pathToTexturesOfProjectile;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;
        this.projectileRange = projectileRange;
        this.projectileHitboxSize = projectileHitboxSize;
        this.selectionFunction = selectionFunction;
    }

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

        targetPoint =
            SkillTools.calculateLastPositionInRange(
                epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
            SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        VelocityComponent vc =
            new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);
        ICollide collide =
            (a, b, from) -> {
                if (b != entity) {
                    b.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                ((HealthComponent) hc).receiveHit(projectileDamage);
                                Game.removeEntity(projectile);
                            });
                }
            };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }

    public String getPathToTexturesOfProjectile() {
        return pathToTexturesOfProjectile;
    }

    public void setPathToTexturesOfProjectile(String pathToTexturesOfProjectile) {
        this.pathToTexturesOfProjectile = pathToTexturesOfProjectile;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public float getProjectileRange() {
        return projectileRange;
    }

    public void setProjectileRange(float projectileRange) {
        this.projectileRange = projectileRange;
    }

    public Damage getProjectileDamage() {
        return projectileDamage;
    }

    public void setProjectileDamage(Damage projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    public Point getProjectileHitboxSize() {
        return projectileHitboxSize;
    }

    public void setProjectileHitboxSize(Point projectileHitboxSize) {
        this.projectileHitboxSize = projectileHitboxSize;
    }

    public ITargetSelection getSelectionFunction() {
        return selectionFunction;
    }

    public void setSelectionFunction(ITargetSelection selectionFunction) {
        this.selectionFunction = selectionFunction;
    }

    public Point getAimedOn() {
        return aimedOn;
    }

    public void setAimedOn(Point aimedOn) {
        this.aimedOn = aimedOn;
    }

    public void setTargetPoint(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }
}
