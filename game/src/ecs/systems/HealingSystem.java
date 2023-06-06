package ecs.systems;

import ecs.components.HealingComponent;
import ecs.components.HealthComponent;
import starter.Game;

public class HealingSystem extends ECS_System {

    @Override
    public void update() {
        Game.getEntities().stream()
                .filter(hc -> hc.getComponent(HealthComponent.class).isPresent())
                .flatMap(hs -> hs.getComponent(HealingComponent.class).stream())
                .forEach(hC -> ((HealingComponent) hC).execute());
    }
}
