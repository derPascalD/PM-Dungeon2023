package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class CombatAI implements IFightAI {
    private final float attackRange;
    private final Skill fightSkill;
    private GraphPath<Tile> path;

    /**
     * English: Attacks the player if he is within the given range. Otherwise, it will move towards
     * the player.
     *
     * @param attackRange Range in which the attack skill should be executed
     * @param fightSkill Skill to be used when an attack is performed
     */
    /**
     * German: Greift den Spieler an, wenn er sich innerhalb der angegebenen Reichweite befindet.
     * Andernfalls bewegt er sich auf den Spieler.
     *
     * @param attackRange Bereich, in dem der Angriffsskill ausgeführt werden soll
     * @param fightSkill Fertigkeit, die bei einem Angriff verwendet werden soll
     */
    public CombatAI(float attackRange, Skill fightSkill) {
        this.attackRange = attackRange;
        this.fightSkill = fightSkill;
    }

    @Override
    public void fight(Entity entity) {
        if (AITools.playerInRange(entity, attackRange)) {
            // the faster pathing once a certain range is reached
            path = AITools.calculatePathToHero(entity);
            AITools.move(entity, path);
            fightSkill.execute(entity);
        }
    }
}
