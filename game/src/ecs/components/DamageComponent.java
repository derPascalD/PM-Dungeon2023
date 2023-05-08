package ecs.components;

import ecs.entities.Entity;

public class DamageComponent extends Component {

    private int attackDamage;

    /**
     * Create a DamageComponent and add it to the associated entity
     *
     * @param entity associated entity
     */
    public DamageComponent(Entity entity) {
        super(entity);
        attackDamage = 1;
    }

    /**
     * Create a DamageComponent and add it to the associated entity
     *
     * @param entity associated entity
     * @param attackDamage damage for the entity
     */
    public DamageComponent(Entity entity, int attackDamage) {
        super(entity);
        this.attackDamage = attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }
}
