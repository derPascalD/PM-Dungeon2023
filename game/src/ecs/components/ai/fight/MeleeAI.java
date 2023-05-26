package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Constants;

public class MeleeAI implements IFightAI {
    private final float attackRange;
    private final int delay = Constants.FRAME_RATE;
    private int timeSinceLastUpdate = 0;
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
    public MeleeAI(float attackRange, Skill fightSkill) {
        this.attackRange = attackRange;
        this.fightSkill = fightSkill;
    }

    @Override
    public void fight(Entity entity) {
        if (AITools.playerInRange(entity, attackRange)) {
            fightSkill.execute(entity);
        } else {
            if (timeSinceLastUpdate >= delay) {
                path = AITools.calculatePathToHero(entity);
                timeSinceLastUpdate = -1;
            }
            timeSinceLastUpdate++;
            AITools.move(entity, path);
        }
    }
}
