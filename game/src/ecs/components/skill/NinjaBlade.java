package ecs.components.skill;

import ecs.components.xp.XPComponent;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;
import tools.Point;

public class NinjaBlade extends RangedAbilities {
    /*
    the range in which the offset can be calculated.
     */
    long currentLevel;
    private float from = 0.1f;
    private float to = 3.0f;
    /**
     * @param skilllearnedLevel is the level sine the skill is learned
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
            int skilllearnedLevel,
            boolean bouncesOffWalls,
            String pathToTexturesOfProjectile,
            float projectileSpeed,
            Damage projectileDamage,
            Point projectileHitboxSize,
            ITargetSelection selectionFunction,
            float projectileRange) {
        super(
                skilllearnedLevel,
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

        // getting the xpComponent from the hero
        Hero hero = (Hero) Game.getHero().get();
        XPComponent xpComponent = hero.getXpComponent();
        long preLevel = currentLevel;
         currentLevel = xpComponent.getCurrentLevel();

        // setting the new AimPoint
        if (currentLevel < skilllearnedLevel && preLevel == currentLevel) {
            to -= 0.1f;
            // case: if the NinjaBlade Skill is completely learned
        } else if (currentLevel >= skilllearnedLevel){
            this.setAimedOn(this.getSelectionFunction().selectTargetPoint());
        }
        super.execute(entity);
        this.setAimedOn(probabilityToHit());
    }
}
