package ecs.components.skill;

import ecs.damage.Damage;
import ecs.entities.Entity;
import tools.Point;

public class NinjaBlade extends RangedAbilities {

    /*
    Counts how often the hero used the NinjaBlade
     */
    public static int countOfUse = 0;
    private float from = 0.6f;
    private float to = 1.4f;
    /**
     * @param damagerange is the damage range of the NinjaBlade
     * @param bouncesOffWalls define if the star can bounce of walls or not
     * @param pathToTexturesOfProjectile is the path to the animation of the projectile
     * @param projectileSpeed is the speed of NinjaBlade
     * @param projectileDamage is the amount and type of damage caused by the NinjaBlade
     * @param projectileHitboxSize is the hitbox of the NinjaBlade
     * @param selectionFunction is the targetinterface which determine where the current Point of
     *     the target is
     * @param projectileRange is the range of the NinjaBlade
     */
    public NinjaBlade(
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
     * adds a random offset to the target position to skew or correct aiming.
     *
     * @return is the new Point with the new adjusted offsets for x and y.
     */
    public Point probabilityToHit() {
        // Adjust the aiming for NinjaBlade by some random offset
        float offsetX = (float) (Math.random() * from - to);
        float offsetY = (float) (Math.random() * from - to);

        return new Point(
                this.getSelectionFunction().selectTargetPoint().x + offsetX,
                this.getSelectionFunction().selectTargetPoint().y + offsetY);
    }

    /**
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {

        // sets the new AimPoint
        if (countOfUse <= 10) {
            to -= 0.1f;
            this.setAimedOn(probabilityToHit());
        } else {
            this.setAimedOn(this.getSelectionFunction().selectTargetPoint());
        }
        super.execute(entity);
        countOfUse += 1;
    }
}
