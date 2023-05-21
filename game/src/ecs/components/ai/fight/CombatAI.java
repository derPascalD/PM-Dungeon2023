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
     * Attacks the player if he is within the specified range.
     *
     * @param attackRange Range in which the attack skill should be executed
     * @param fightSkill Skill to be used when an attack is performed
     */
    public CombatAI(float attackRange, Skill fightSkill) {
        this.attackRange = attackRange;
        this.fightSkill = fightSkill;
    }

    /**
     * Once the Hero is in the area, the NPC attacks and performs the melee sent along.
     *
     * @param entity associated entity
     */
    @Override
    public void fight(Entity entity) {
        if (AITools.playerInRange(entity, attackRange)) {
            path = AITools.calculatePathToHero(entity);
            AITools.move(entity, path);
            fightSkill.execute(entity);
        }
    }
}
