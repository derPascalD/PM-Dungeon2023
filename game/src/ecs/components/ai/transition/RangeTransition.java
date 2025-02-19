package ecs.components.ai.transition;

import ecs.components.ai.AITools;
import ecs.entities.Entity;

public class RangeTransition implements ITransition {

    private final float range;

    /**
     * English: Switches to combat mode when the player is within range of the entity.
     *
     * @param range Range of the entity.
     */
    /**
     * German: Schaltet in den Kampfmodus, wenn sich der Spieler in Reichweite der Entität befindet.
     *
     * @param range Bereich der Entität.
     */
    public RangeTransition(float range) {
        this.range = range;
    }

    @Override
    public boolean isInFightMode(Entity entity) {
        return AITools.playerInRange(entity, range);
    }
}
