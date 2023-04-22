package ecs.components.ai.idle;

import ecs.entities.Entity;

public interface IIdleAI {

    /**
     * English:
     * Implements the idle behavior of an AI controlled entity
     *
     * @param entity associated entity
     */
    /**
     * German:
     * Implementiert das Leerlaufverhalten einer KI-gesteuerten Entität
     *
     * @param entity assoziierte Entität
     */
    void idle(Entity entity);
}
