package ecs.components.skill;

import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.damage.Damage;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

import java.util.Optional;

public class NinjaBlade extends RangedAbilities {

    public static int countOfUse  = 0;
    /**
     * @param damagerange is the damage range of the NinjaBlade
     * @param bouncesOffWalls define if the star can bounce of walls or not
     * @param pathToTexturesOfProjectile is the path to the animation of the projectile
     * @param projectileSpeed is the speed of NinjaBlade
     * @param projectileDamage is the amount and type of damage caused by the NinjaBlade
     * @param projectileHitboxSize is the hitbox of the NinjaBlade
     * @param selectionFunction is the targetinterface which determine where the current Point of the target is
     * @param projectileRange is the range of  the NinjaBlade
     */
    public NinjaBlade(int damagerange, boolean bouncesOffWalls,
                      String pathToTexturesOfProjectile, float projectileSpeed,
                      Damage projectileDamage, Point projectileHitboxSize, ITargetSelection selectionFunction,
                      float projectileRange)
    {
        super(damagerange,bouncesOffWalls,pathToTexturesOfProjectile,projectileSpeed,projectileDamage,
            projectileHitboxSize, selectionFunction, projectileRange);
    }

    /**
     * adds a random offset to the target position to skew or correct aiming.
     * @return is the new Point with the new adjusted offsets for x and y.
     */
    public Point probabilityToHit(){
        // Adjust the aiming for NinjaBlade by some random offset
        float offsetX = (float) (Math.random() * 0.5f - 0.9f);
        float offsetY = (float) (Math.random() * 0.5f - 0.9f);

        return new Point( this.getSelectionFunction().selectTargetPoint().x + offsetX,
            this.getSelectionFunction().selectTargetPoint().y + offsetY);
    }

    /**
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {

        // sets the new AimPoint
        if (countOfUse <= 5)
        {
            this.setAimedOn(probabilityToHit());
        }
        else
        {
            this.setAimedOn(this.getSelectionFunction().selectTargetPoint());
        }
        super.execute(entity);
        countOfUse+=1;


        // checks if the on the current targetPosition is an entity to apply the knock-back on.
        for (Entity targetEntitiy : Game.getEntities()) {
            if (targetEntitiy.getComponent(PositionComponent.class).isPresent()) {
                PositionComponent pse = (PositionComponent) targetEntitiy.getComponent(PositionComponent.class).get();
                if (pse.getPosition() == this.getTargetPoint()) {

                    break;
                }
            }
        }


    }
}

