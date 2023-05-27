package ecs.components;

import ecs.entities.Entity;
import java.util.logging.Logger;
import logging.CustomLogLevel;
import tools.Constants;

/**
 * Die Das HealingComponent sorgt dafür, das Entities nach eienr bestimmten Zeit wieder ihc leben
 * aufgefüllt bekomen
 */
public class HealingComponent extends Component {
    private final Logger healingLogger = Logger.getLogger(this.getClass().getName());
    private final int healingStart;
    private final int hpPerSecond;
    private final int durationNextHp;
    private float frames;
    private boolean start = false;
    private int actualHP;
    private HealthComponent healthC;

    /**
     * Create a new component and add it to the associated entity
     *
     * @param entity which uses this component
     * @param timeToStartHealing Time when the healing starts
     * @param hpProHeal How many life points you get per healing process
     * @param durationToNextHp The time whenever a healing process begins.
     */
    public HealingComponent(
            Entity entity, int timeToStartHealing, int hpProHeal, int durationToNextHp) {
        super(entity);
        this.healingStart = timeToStartHealing;
        this.durationNextHp = durationToNextHp * 2;
        this.frames = this.healingStart * Constants.FRAME_RATE;
        this.hpPerSecond = hpProHeal;
    }

    public HealingComponent(Entity entity) {
        super(entity);
        this.healingStart = 1;
        this.durationNextHp = 1;
        this.frames = Constants.FRAME_RATE;
        this.hpPerSecond = 1;
    }

    public HealingComponent(Entity entity, int timeTOStartHealing) {
        super(entity);
        this.healingStart = timeTOStartHealing;
        this.durationNextHp = 1;
        this.frames = Constants.FRAME_RATE;
        this.hpPerSecond = 1;
    }

    /**
     * constantly checks how the life of the entity is evolving and starts the "reset()" method and
     * the "startHealing()" method.
     */
    public void healingUpdate() {
        healthC =
                (HealthComponent)
                        entity.getComponent(HealthComponent.class)
                                .orElseThrow(
                                        () ->
                                                new IllegalStateException(
                                                        "Entity does not have a HealthComponent"));
        reset();
        if (healthC.getMaximalHealthpoints() > healthC.getCurrentHealthpoints()) {
            startHealing();
        }
    }

    /*
    Starts the healing process of the entity after a certain time
    */
    private void startHealing() {
        actualHP = healthC.getCurrentHealthpoints();

        frames = Math.max(0, --frames);
        if (frames == 0 && !start) {

            healingLogger.log(
                    CustomLogLevel.INFO,
                    "Healing Active: '"
                            + entity.getClass().getSimpleName()
                            + "' Actual Lifepoints: "
                            + healthC.getCurrentHealthpoints());

            frames = durationNextHp * Constants.FRAME_RATE;
            start = true;
        } else if (start) healing();
    }

    /* Here the entity gets life points added after a certain time. The time depends on the varibale durationNextHp. */
    private void healing() {
        frames = Math.max(0, --frames);
        if (frames == 0) {
            healthC.setCurrentHealthpoints(healthC.getCurrentHealthpoints() + hpPerSecond);
            frames = durationNextHp * Constants.FRAME_RATE;
        }
    }

    /*
    If the Entity has full life again, the healing process is stopped.
    If the Entity is damaged again, the healing process is also stopped and
    the time until the Entity can heal again starts from the beginning.
    */
    private void reset() {
        if (healthC.getCurrentHealthpoints() == healthC.getMaximalHealthpoints() && start) {
            frames = healingStart * Constants.FRAME_RATE;
            start = false;
            healingLogger.log(
                CustomLogLevel.INFO,
                "Healing completed: '"
                    + entity.getClass().getSimpleName()
                    + "' New Lifepoints: "
                    + healthC.getCurrentHealthpoints());
        } else if (actualHP > healthC.getCurrentHealthpoints()) {
            frames = healingStart * Constants.FRAME_RATE;
            start = false;
        }

    }
}
