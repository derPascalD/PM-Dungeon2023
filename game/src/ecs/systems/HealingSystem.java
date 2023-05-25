package ecs.systems;
import ecs.components.HealingComponent;
import ecs.components.ai.AIComponent;
import starter.Game;



public class HealingSystem extends ECS_System {



    @Override
    public void update() {

        Game.getEntities().stream()
            .flatMap(e -> e.getComponent(HealingComponent.class).stream())
            .forEach(he -> ((HealingComponent) he).healingUpdate());

    }

}
