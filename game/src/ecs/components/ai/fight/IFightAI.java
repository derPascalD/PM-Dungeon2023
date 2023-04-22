package ecs.components.ai.fight;

import ecs.entities.Entity;

public interface IFightAI {

    /**
     * English:
     * Implements the combat behavior of an AI controlled entity
     *
     * @param entity associated entity
     */
    /**
     * German:
     * Implementiert das Kampfverhalten einer KI-gesteuerten Entität.
     *
     * @param entity associated entity
     */
    void fight(Entity entity);
}
