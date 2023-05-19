package ecs.components.collision;

import ecs.entities.Entity;
import level.elements.tile.Tile;

public interface ICollide {
    /**
     * English: Implements the Collision behavior of a Hitbox having entity
     *
     * @param a is the current Entity
     * @param b is the Entity with whom the Collision happened
     * @param from the direction from a to b
     */
    /**
     * German: Implementiert das Kollisionsverhalten einer Hitbox mit Entität
     *
     * @param a ist das aktuelle Entity
     * @param b ist das Entity, mit dem die Kollision stattfand
     * @param from die Richtung von a nach b
     */
    void onCollision(Entity a, Entity b, Tile.Direction from);


}
