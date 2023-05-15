package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class Sword extends CombatAttackSkills {
    public Sword(int damage, String combatLeft, String combatRight) {
        super(
                combatLeft,
                combatRight,
                new Damage(damage, DamageType.PHYSICAL, null));

    }
}
