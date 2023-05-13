package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class Sword extends CombatAttackSkill {
    public Sword(int damage) {
        super(
                "character/knight/attackLeft/",
                "character/knight/attackRight/",
                new Point(1, 1),
                new Damage(damage, DamageType.PHYSICAL, null),
                0.6F);
    }
}
