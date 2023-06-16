package ecs.components.ai.idle;


import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.HealthComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;

public class BossIdleWalk implements IIdleAI {
    private float radius;
    private GraphPath<Tile> path;
    private float breakTime;
    private int currentBreak = 0;
    private HealthComponent hp;
    private int lifePoints;

    /**
     * Finds a point in the radius and then moves there. When the point has been reached, a new
     * point in the radius is searched for from the center.
     *
     * @param radius Radius in which a target point is to be searched for
     * @param breakTimeInSeconds how long to wait (in seconds) before searching a new goal
     * @param hp makes the HealthComponent of the give Entity accessible
     */
    public BossIdleWalk(float radius, float breakTimeInSeconds, HealthComponent hp) {
        this.radius = radius;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
        this.hp = hp;
        lifePoints = hp.getCurrentHealthpoints();
    }

    @Override
    public void idle(Entity entity) {

        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            if (lifePoints != hp.getCurrentHealthpoints()) {
                lifePoints = hp.getCurrentHealthpoints();
                breakTime += 0.2f;
            } else if (currentBreak >= breakTime) {
                currentBreak = 0;
                path = AITools.calculatePath(entity, Game.getHero().get());
                idle(entity);
            }

            currentBreak++;

        } else AITools.move(entity, path);
    }

    /**
     * Getter
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Setter
     * @param radius new radius to set
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

}
