package ecs.components;

import ecs.entities.Entity;
import graphic.hud.HealingBar;
import java.util.logging.Logger;
import logging.CustomLogLevel;
import starter.Game;
import tools.Constants;

/**
 * The HealingComponent ensures that entities return to life after a certain period of time
 * get refilled.
 */
public class HealingComponent extends Component {
    private final Logger HEALINGLOGGER = Logger.getLogger(this.getClass().getName());
    private final int TIMETOSTARTHEALING;
    private final int HPPROHEAL;
    private final int DURATIONNEXTHP;
    private float frames;
    private boolean start = false;
    private int actualHP;
    private HealthComponent healthC;

    /**
     * Create a new component and add it to the associated entity
     *
     * @param entity             which uses this component
     * @param timeToStartHealing Time when the healing starts
     * @param hpProHeal          How many life points you get per healing process
     * @param durationToNextHp   The time whenever a healing process begins.
     */
    public HealingComponent(
        Entity entity, int timeToStartHealing, int hpProHeal, int durationToNextHp) {
        super(entity);
        this.TIMETOSTARTHEALING = timeToStartHealing;
        this.DURATIONNEXTHP = durationToNextHp * 2;
        this.frames = this.TIMETOSTARTHEALING * Constants.FRAME_RATE;
        this.HPPROHEAL = hpProHeal;
    }

    public HealingComponent(Entity entity) {
        super(entity);
        this.TIMETOSTARTHEALING = 1;
        this.DURATIONNEXTHP = 1;
        this.frames = Constants.FRAME_RATE;
        this.HPPROHEAL = 1;
    }

    public HealingComponent(Entity entity, int timeTOStartHealing) {
        super(entity);
        this.TIMETOSTARTHEALING = timeTOStartHealing;
        this.DURATIONNEXTHP = 1;
        this.frames = Constants.FRAME_RATE;
        this.HPPROHEAL = 1;
    }

    /**
     * constantly checks how the life of the entity is evolving and starts the "reset()" method and
     * the "startHealing()" method.
     */
    public void execute() {
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

            HEALINGLOGGER.log(
                CustomLogLevel.INFO,
                "Healing Active: '"
                    + entity.getClass().getSimpleName()
                    + "' Actual Lifepoints: "
                    + healthC.getCurrentHealthpoints());

            frames = DURATIONNEXTHP * Constants.FRAME_RATE;
            start = true;
        } else if (start) healing();
    }

    /* Here the entity gets life points added after a certain time. The time depends on the variable durationNextHp. */
    private void healing() {
        if (Game.healingBar != null) {
            HealingBar.updateHealingBar(entity, true, healthC.getCurrentHealthpoints());
        }
        frames = Math.max(0, --frames);
        if (frames == 0) {
            healthC.setCurrentHealthpoints(healthC.getCurrentHealthpoints() + HPPROHEAL);
            frames = DURATIONNEXTHP * Constants.FRAME_RATE;
        }
    }

    /*
    If the Entity has full life again, the healing process is stopped.
    If the Entity is damaged again, the healing process is also stopped and
    the time until the Entity can heal again starts from the beginning.
    */
    private void reset() {
        if (healthC.getCurrentHealthpoints() == healthC.getMaximalHealthpoints() && start) {
            if (Game.healingBar != null) {
                HealingBar.updateHealingBar(entity, false, healthC.getCurrentHealthpoints());
            }
            frames = TIMETOSTARTHEALING * Constants.FRAME_RATE;
            start = false;
            HEALINGLOGGER.log(
                CustomLogLevel.INFO,
                "Healing completed: '"
                    + entity.getClass().getSimpleName()
                    + "' New Lifepoints: "
                    + healthC.getCurrentHealthpoints());
        } else if (actualHP > healthC.getCurrentHealthpoints()) {
            if (Game.healingBar != null) {
                HealingBar.updateHealingBar(entity, false, healthC.getCurrentHealthpoints());
            }
            frames = TIMETOSTARTHEALING * Constants.FRAME_RATE;
            start = false;
        }
    }

    /**
     * Return start boolean
     *
     * @return start
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Set the Start boolean
     *
     * @param start
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * Set the Frames
     *
     * @param frames
     */
    public void setFrames(float frames) {
        this.frames = frames;
    }
}
