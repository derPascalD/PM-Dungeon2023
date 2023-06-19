package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.HealthComponent;
import ecs.components.ai.AITools;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import ecs.entities.Monsters.Skeleton;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;

public record BossAI(float attackRange, Skill fightSkill, Skill fightSkillTwo) implements IFightAI {

    private static int frames = Constants.FRAME_RATE * 2;

    /**
     * Once the Hero is in the area, the NPC attacks and performs the melee sent along.
     *
     * @param entity associated entity
     */
    @Override
    public void fight(Entity entity) {

        HealthComponent hc = (HealthComponent) entity.getComponent(HealthComponent.class).get();
        GraphPath<Tile> path = AITools.calculatePathToHero(entity);
        AITools.move(entity, path);
        if (hc.getCurrentHealthpoints() > hc.getMaximalHealthpoints() / 2) {
            fightSkillTwo.execute(entity);

        } else {
            fightSkill.execute(entity);
            if (AITools.playerInRange(entity, attackRange)) {
                frames = Math.max(0, --frames);
                if (frames == 0) {
                    Game.addEntity(new Skeleton(Game.getLevelDepth()));
                    frames = Constants.FRAME_RATE * 2;
                }
            }
        }
    }
}
