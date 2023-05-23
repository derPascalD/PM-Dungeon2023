package ecs.components.skill.magic;

import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Monsters.Monster;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import starter.Game;
import tools.Point;

public class StunningStrikeSkill extends MagicSkills {

    // the radius in which the entities can be stunnded.
    private final int stunningRadius = 2;

    /**
     * @param skillDuration defines how long a monster entity is stunned.
     */
    public StunningStrikeSkill(int skillDuration) {
        super(1, skillDuration);
    }

    /**
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        // checking entity is null
        if (entity == null) {
            return;
        }

        // getting the positionComponent and current Position of the Hero
        PositionComponent positionComponent =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () ->
                                                new IllegalStateException(
                                                        "Entity does not have a HealthComponent"));
        Point currentPos = positionComponent.getPosition();

        // getting the healthComponent and current health of the Hero
        HealthComponent healthComponent =
                (HealthComponent)
                        entity.getComponent(HealthComponent.class)
                                .orElseThrow(
                                        () ->
                                                new IllegalStateException(
                                                        "Entity does not have a HealthComponent"));

        healthComponent.receiveHit(new Damage(skillHealthCosts, DamageType.PHYSICAL, entity));
        System.out.println("INFORMATION:" + "Damage received due to the use of spells");

        // getting all monster entities
        List<Entity> allMonsterEnities =
                new ArrayList<>(
                        Game.getEntities().stream().filter(e -> e instanceof Monster).toList());

        // adding all monster entities which are in stunning range to a list
        ArrayList<Entity> entitiesInRange = new ArrayList<>();
        for (Entity e : allMonsterEnities) {
            PositionComponent positionComponentEntity =
                    (PositionComponent) e.getComponent(PositionComponent.class).get();
            double distance =
                    Point.calculateDistance(positionComponentEntity.getPosition(), currentPos);
            if (distance <= stunningRadius) {
                entitiesInRange.add(e);
            }
        }

        // getting AIComponents based of "entitiesInRange"
        List<AIComponent> savedAIComponentsFromMonsters =
                entitiesInRange.stream()
                        .map(e -> (AIComponent) e.getComponent(AIComponent.class).get())
                        .toList();
        // removing the aicomponent for each monster
        for (Entity e : entitiesInRange) {
            e.removeComponent(AIComponent.class);
            System.out.println(e.getClass().getSimpleName() + " got stunned.");
        }
        startTimer(entitiesInRange, savedAIComponentsFromMonsters);
    }

    /**
     * starts the timer
     *
     * @param entitiesInRange monster entities which are in stunning range
     * @param savedAIComponentsFromMonsters AIComponents of "entitiesInRange"
     */
    private void startTimer(
            ArrayList<Entity> entitiesInRange, List<AIComponent> savedAIComponentsFromMonsters) {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    /** set up the previous aicomponent for each monster after the delay. */
                    public void run() {

                        for (int i = 0; i < entitiesInRange.size(); i++) {
                            entitiesInRange
                                    .get(i)
                                    .addComponent(savedAIComponentsFromMonsters.get(i));
                        }

                        System.out.println("The monsters are not stunned anymore");
                    }
                },
                (long) this.skillDuration * 1000);
    }
}
