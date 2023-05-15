package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class Combat extends CombatAttackSkills {
    public Combat(int damage, String combatLeft, String combatRight) {
        super(
            combatLeft,
            combatRight,
            new Damage(damage, DamageType.PHYSICAL, null));
    }
}
