package ecs.components.xp;

public interface ILevelUp {

    /**
     * English: Implements the LevelUp behavior of a XPComponent having entity
     *
     * @param nexLevel is the new level of the entity
     */
    /**
     * German: Implementiert das LevelUp-Verhalten einer XPComponent mit Entität
     *
     * @param nexLevel ist die neue Ebene der Entität
     */
    void onLevelUp(long nexLevel);
}
