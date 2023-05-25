package ecs.components;
import ecs.entities.Entity;
import ecs.entities.Hero;
import logging.CustomLogLevel;
import starter.Game;
import tools.Constants;

import java.util.logging.Logger;


/**
 * Die Das HealingComponent sorgt dafür, das Entities nach eienr bestimmten Zeit wieder ihc leben aufgefüllt bekomen
 */
public class HealingComponent extends Component {
    private final Logger healingLogger = Logger.getLogger(this.getClass().getName());
    int frames = 5 * Constants.FRAME_RATE;
    boolean start = false;

    int actualHP;

    /**
     * Create a new component and add it to the associated entity
     *
     * @param entity associated entity
     */
    public HealingComponent(Entity entity) {
        super(entity);
    }


    public void healingUpdate(){
        Game.getEntities().stream()
            .filter(e -> e instanceof Hero)
            .map(this::reset)
            .filter(e -> ((Hero) e).getHealthComponent().getCurrentHealthpoints() < 100)
            .forEach(this::startHealing);
    }

    private void startHealing(Entity entity) {
        HealthComponent hc =
            (HealthComponent)
                entity.getComponent(HealthComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("HealthComponent"));
        actualHP = hc.getCurrentHealthpoints();



        frames = Math.max(0, --frames);
        if (frames == 0 && !start) {

            healingLogger.log(
                CustomLogLevel.INFO,
                "Healing Active: '"
                    + entity.getClass().getSimpleName()
                    + "'");

            frames = Constants.FRAME_RATE;
            start = true;
        } else if (start) healing(entity);

    }

    private void healing(Entity entity) {
        HealthComponent hc =
            (HealthComponent)
                entity.getComponent(HealthComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("HealthComponent"));

        frames = Math.max(0, --frames);
        if (frames == 0) {
            hc.setCurrentHealthpoints(hc.getCurrentHealthpoints() + 1);
            frames = Constants.FRAME_RATE;

        }
    }
    private Entity reset(Entity entity){
        HealthComponent hc =
            (HealthComponent)
                entity.getComponent(HealthComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("HealthComponent"));

        if(hc.getCurrentHealthpoints() == 100){
            frames = 5 *Constants.FRAME_RATE;
            start = false;
        }else if(actualHP > hc.getCurrentHealthpoints()){
            frames = 5 *Constants.FRAME_RATE;
            start = false;
        }

        return entity;
    }
}
