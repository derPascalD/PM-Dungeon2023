package ecs.components.skill.magic;

import com.badlogic.gdx.maps.Map;
import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.ITargetSelection;
import ecs.components.skill.SkillComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monsters.Monster;
import starter.Game;
import tools.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StunningStrikeSkill extends MagicSkills {

    private final int stunningRadius = 2;



    ArrayList<Entity> entitiesInRadius = new ArrayList<>();
    private ArrayList<ITargetSelection> StunningSkillITargetSelectionList;

    public StunningStrikeSkill(int skillDuration) {
        super(1, skillDuration);
    }

    @Override
    public void execute(Entity entity) {

        if (entity == null){
            return;
        }

        PositionComponent positionComponent  = (PositionComponent) entity.getComponent(PositionComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a HealthComponent"));
        Point currentPos = positionComponent.getPosition();

        HealthComponent healthComponent = (HealthComponent) entity.getComponent(HealthComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a HealthComponent"));
        healthComponent.setCurrentHealthpoints(healthComponent.getCurrentHealthpoints()-skillHealthCosts);
        System.out.println("INFORMATION:" + "Damage received due to the use of spells");


        List<Entity> allMonsterEnities = new ArrayList<>(Game.getEntities().stream()
            .filter(e -> e instanceof Monster)
            .toList());


        ArrayList<Entity> entitiesInRange = new ArrayList<>();
        for (Entity e : allMonsterEnities) {
            PositionComponent positionComponentEntity  = (PositionComponent) e.getComponent(PositionComponent.class).get();
                double distance = Point.calculateDistance(positionComponentEntity.getPosition(), currentPos);
                if (distance <= stunningRadius) {
                    entitiesInRange.add(e);
                }
            }

        List<AIComponent> savedAIComponentsFromMonsters = entitiesInRange.stream()
            .map(e -> (AIComponent) e.getComponent(AIComponent.class).get())
            .toList();


        for(Entity e :entitiesInRange) {
            e.removeComponent(AIComponent.class) ;
            System.out.println(e.getClass().getSimpleName()+ " got stunned.");
        }


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < entitiesInRange.size(); i++) {
                    entitiesInRange.get(i).addComponent(savedAIComponentsFromMonsters.get(i));
                }

                System.out.println("The monsters are not stunned anymore");
            }
        }, (long) this.skillDuration*1000);


































    }




}
