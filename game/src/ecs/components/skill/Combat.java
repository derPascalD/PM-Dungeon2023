package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class Combat extends CombatAttackSkill {
    public Combat(int damage, String combatLeft, String combatRight) {
        super(
                "animation/combat.png",
                "animation/combat.png",
                new Point(10, 10),
                new Damage(damage, DamageType.PHYSICAL, null),
                0F);
    }
}
