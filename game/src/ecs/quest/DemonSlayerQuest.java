package ecs.quest;

import ecs.entities.Hero;
import ecs.entities.Monsters.Demon;
import ecs.entities.Monsters.Monster;
import starter.Game;

public class DemonSlayerQuest extends Quest {

    public DemonSlayerQuest(String name, String description) {
        super(name, description);
    }

    @Override
    public String getProgress() {
        return "Yout currently have slain" + countDemonsSlayn() + "/10 Demons";
    }

    @Override
    public boolean isComplete() {
        if(countDemonsSlayn() >= 10) return true;
        return  false;
    }

    @Override
    public void onComplete() {
        // Item generieren und ins Hero Inventar legen
    }

    private long countDemonsSlayn() {
        Hero hero = ( Hero ) Game.getHero().get();
        return hero.getKilledMonsters().stream()
            .filter(entity -> entity instanceof Demon)
            .count();
    }
}
