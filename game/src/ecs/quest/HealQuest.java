package ecs.quest;

import ecs.components.HealthComponent;
import ecs.entities.Hero;
import ecs.items.ImplementedItems.Healthpot;
import starter.Game;

public class HealQuest extends Quest {

    private int questNumber = 10;

    public HealQuest(String name, String description) {
        super(name, description);
        progressText = "0/" + questNumber + " Healthpots used";
    }

    @Override
    public String getProgress() {
        return progressText;
    }

    @Override
    public void updateProgress() {
        progressText = Healthpot.getUseCount() + "/" + questNumber + " Healthpots used";
    }

    @Override
    public boolean isComplete() {
        if(Healthpot.getUseCount()>=questNumber) return true;
        return false;
    }

    @Override
    public void onComplete() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent heroHealth =
            (HealthComponent) hero.getComponent(HealthComponent.class).get();
        heroHealth.setMaximalHealthpoints(heroHealth.getMaximalHealthpoints()+10);
    }
}
