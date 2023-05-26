package ecs.components.ai.transition;

import ecs.entities.Entity;

/** Determines when an ai switches between idle and fight */
/** Bestimmt, wann eine KI zwischen Leerlauf und Kampf wechselt */
public interface ITransition {

    /**
     * English: Function that determines whether an entity should be in combat mode
     *
     * @param entity associated entity
     * @return if the entity should fight
     */
    /**
     * German: Funktion, die feststellt, ob eine Entität im Kampfmodus sein sollte.
     *
     * @param entity assoziierte Entität
     * @return ob die Entität kämpfen soll
     */
    boolean isInFightMode(Entity entity);
}
