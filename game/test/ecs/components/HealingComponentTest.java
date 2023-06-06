package ecs.components;

import ecs.entities.Entity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HealingComponentTest {

    private HealingComponent healingComponent;
    private HealthComponent hc;
    private Entity entity;

    @Before
    public void setup() {
        entity = new Entity();
        hc = new HealthComponent(entity, 50, null, null, null);
        healingComponent = new HealingComponent(entity, 5, 1, 1);
    }

    @Test
    public void testStartHealing_MoreThenOrEqualMax() {
        entity.addComponent(healingComponent);
        hc.setMaximalHealthpoints(100);
        hc.setCurrentHealthpoints(100);
        healingComponent.setFrames(0);
        healingComponent.setStart(false);
        healingComponent.execute();
        Assert.assertFalse(healingComponent.isStart());
    }

    @Test
    public void testStartHealing_LessThenMax() {
        entity.addComponent(healingComponent);
        hc.setMaximalHealthpoints(100);
        hc.setCurrentHealthpoints(90);
        healingComponent.setFrames(0);
        healingComponent.setStart(false);
        healingComponent.execute();
        Assert.assertTrue(healingComponent.isStart());
    }

    @Test
    public void testHealing_CurrentHealthIsMaxHealth() {
        int frames1 = 30 * 10;
        hc.setCurrentHealthpoints(45);

        for (int i = 0; i < frames1; i++) {
            healingComponent.execute();
        }
        Assert.assertEquals(hc.getCurrentHealthpoints(), hc.getMaximalHealthpoints());
    }

    @Test
    public void testHealing_CurrentHealthIsNotMaxHealth() {
        int frames1 = 30 * 9;
        hc.setCurrentHealthpoints(45);

        for (int i = 0; i < frames1; i++) {
            healingComponent.execute();
        }
        Assert.assertNotEquals(hc.getCurrentHealthpoints(), hc.getMaximalHealthpoints());
    }
}
