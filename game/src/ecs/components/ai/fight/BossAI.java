package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AITools;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import ecs.entities.Monsters.Boss;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;

public record BossAI(
    float attackRange,
    Skill fightSkill,
    Skill fightSkillTwo,
    HealthComponent hc,
    VelocityComponent vC)
    implements IFightAI {

    private static int frames = Constants.FRAME_RATE * 2;

    /**
     * Once the Hero is in the area, the NPC attacks and performs the melee sent along.
     *
     * @param entity associated entity
     */
    @Override
    public void fight(Entity entity) {

        if (AITools.playerInRange(entity, attackRange)) {
            frames = Math.max(0, --frames);
            if (frames == 0) {
                Game.addEntity(new Boss(Game.getLevelDepth()));
                frames = Constants.FRAME_RATE * 2;
            }

        } else {
            GraphPath<Tile> path = AITools.calculatePathToHero(entity);
            AITools.move(entity, path);
            if (hc.getCurrentHealthpoints() > 50) {
                fightSkillTwo.execute(entity);
            } else {
                fightSkill.execute(entity);
                secondPhase();
            }
        }
    }

    /**
     * Sets the Boss Monster in the secondphase, activates the second skill and increes the x and y
     * speed
     */
    private void secondPhase() {
        vC.setXVelocity(0.3f);
        vC.setYVelocity(0.3f);
    }
}

