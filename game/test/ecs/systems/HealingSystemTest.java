package ecs.systems;

import controller.SystemController;
import ecs.components.HealingComponent;
import ecs.components.HealthComponent;
import ecs.entities.Entity;
import org.junit.Assert;
import org.junit.Test;
import starter.Game;

public class HealingSystemTest {

    private HealingComponent healingComponent;
    private HealthComponent hc;

    @Test
    public void testDoesItStart() {
        // given
        Game.systems = new SystemController();
        Entity entity = new Entity();
        Game.getEntities().addAll(Game.getEntitiesToAdd());
        hc = new HealthComponent(entity, 50, null, null, null);
        healingComponent = new HealingComponent(entity, 5, 1, 1);
        hc.setMaximalHealthpoints(100);
        hc.setCurrentHealthpoints(50);
        healingComponent.setFrames(0);
        healingComponent.setStart(false);

        // when
        HealingSystem system = new HealingSystem();
        system.update();

        // then
        Assert.assertTrue(healingComponent.isStart());
    }

    @Test
    public void testNineSecondsHeal() {
        // given
        int timeToStart = 5;
        int toNextHp = 1;
        int hpPerHeal = 5;
        int secondsToWait = 9;
        int framesToWait = secondsToWait * 30;
        int expectedHp = 70;

        Game.systems = new SystemController();
        Entity entity = new Entity();
        Game.getEntities().addAll(Game.getEntitiesToAdd());
        hc = new HealthComponent(entity, 100, null, null, null);
        healingComponent = new HealingComponent(entity, timeToStart, hpPerHeal, toNextHp);
        HealingSystem system = new HealingSystem();
        Game.systems.add(system);

        // when
        hc.setCurrentHealthpoints(50);
        for (int i = 0; i < framesToWait; i++) {
            Game.systems.update();
        }

        // then
        Assert.assertEquals(expectedHp, hc.getCurrentHealthpoints());
    }

    @Test
    public void testSevenSecondsHeal() {
        // given
        int timeToStart = 5;
        int toNextHp = 1;
        int hpPerHeal = 5;
        int secondsToWait = 7;
        int framesToWait = secondsToWait * 30;
        int expectedHp = 60;

        Game.systems = new SystemController();
        Entity entity = new Entity();
        Game.getEntities().addAll(Game.getEntitiesToAdd());
        hc = new HealthComponent(entity, 100, null, null, null);
        healingComponent = new HealingComponent(entity, timeToStart, hpPerHeal, toNextHp);
        HealingSystem system = new HealingSystem();
        Game.systems.add(system);

        // when
        hc.setCurrentHealthpoints(50);
        for (int i = 0; i < framesToWait; i++) {
            Game.systems.update();
        }

        // then
        Assert.assertEquals(expectedHp, hc.getCurrentHealthpoints());
    }

    @Test
    public void testFiSecondsHeal() {
        // given
        int timeToStart = 5;
        int toNextHp = 1;
        int hpPerHeal = 5;
        int secondsToWait = 5;
        int framesToWait = secondsToWait * 30;
        int expectedHp = 50;

        Game.systems = new SystemController();
        Entity entity = new Entity();
        Game.getEntities().addAll(Game.getEntitiesToAdd());
        hc = new HealthComponent(entity, 100, null, null, null);
        healingComponent = new HealingComponent(entity, timeToStart, hpPerHeal, toNextHp);
        HealingSystem system = new HealingSystem();
        Game.systems.add(system);

        // when
        hc.setCurrentHealthpoints(50);
        for (int i = 0; i < framesToWait; i++) {
            Game.systems.update();
        }

        // then
        Assert.assertEquals(expectedHp, hc.getCurrentHealthpoints());
    }

    @Test
    public void testTwentySecondsHeal() {
        // given
        int timeToStart = 5;
        int toNextHp = 1;
        int hpPerHeal = 5;
        int secondsToWait = 20;
        int framesToWait = secondsToWait * 30;
        int expectedHp = 100;

        Game.systems = new SystemController();
        Entity entity = new Entity();
        Game.getEntities().addAll(Game.getEntitiesToAdd());
        hc = new HealthComponent(entity, 100, null, null, null);
        healingComponent = new HealingComponent(entity, timeToStart, hpPerHeal, toNextHp);
        HealingSystem system = new HealingSystem();
        Game.systems.add(system);

        // when
        hc.setCurrentHealthpoints(50);
        for (int i = 0; i < framesToWait; i++) {
            Game.systems.update();
        }

        // then
        Assert.assertEquals(expectedHp, hc.getCurrentHealthpoints());
    }

    @Test
    public void testZeroSecondsHeal() {
        // given
        int timeToStart = 5;
        int toNextHp = 1;
        int hpPerHeal = 5;
        int secondsToWait = 0;
        int framesToWait = secondsToWait * 30;
        int expectedHp = 50;

        Game.systems = new SystemController();
        Entity entity = new Entity();
        Game.getEntities().addAll(Game.getEntitiesToAdd());
        hc = new HealthComponent(entity, 100, null, null, null);
        healingComponent = new HealingComponent(entity, timeToStart, hpPerHeal, toNextHp);
        HealingSystem system = new HealingSystem();
        Game.systems.add(system);

        // when
        hc.setCurrentHealthpoints(50);
        for (int i = 0; i < framesToWait; i++) {
            Game.systems.update();
        }

        // then
        Assert.assertEquals(expectedHp, hc.getCurrentHealthpoints());
    }
}
