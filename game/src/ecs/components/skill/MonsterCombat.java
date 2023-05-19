package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;

/** Creation of a melee attack with the animations and damage of melee combat */
public class MonsterCombat extends CombatAttackSkills {
    public MonsterCombat(int damage, String combatLeft, String combatRight) {
        super(combatLeft, combatRight, new Damage(damage, DamageType.PHYSICAL, null));
    }
}
