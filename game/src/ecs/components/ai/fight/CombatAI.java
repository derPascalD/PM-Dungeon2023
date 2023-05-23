package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public record CombatAI(float attackRange, Skill fightSkill) implements IFightAI {

    /**
     * Once the Hero is in the area, the NPC attacks and performs the melee sent along.
     *
     * @param entity associated entity
     */
    @Override
    public void fight(Entity entity) {
        if (AITools.playerInRange(entity, attackRange)) {
            GraphPath<Tile> path = AITools.calculatePathToHero(entity);
            AITools.move(entity, path);
            fightSkill.execute(entity);
        }
    }
}
