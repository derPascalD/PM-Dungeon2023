package ecs.components;

import ecs.entities.Entity;

public class DamageComponent extends Component {

    private int meleeDamage;
    private int rangeDamage;

    /**
     * Create a DamageComponent and add it to the associated entity Sets the attackDamage to 1 as
     * default
     *
     * @param entity associated entity
     */
    public DamageComponent(Entity entity) {
        super(entity);
        meleeDamage = 1;
        rangeDamage = 1;
    }

    /**
     * Create a DamageComponent and add it to the associated entity
     *
     * @param entity associated entity
     * @param meleeDamage damage for the entity
     */
    public DamageComponent(Entity entity, int meleeDamage, int rangeDamage) {
        super(entity);
        this.meleeDamage = meleeDamage;
        this.rangeDamage = rangeDamage;
    }

    /**
     *
     * @return
     */
    public int getMeleeDamage() {
        return meleeDamage;
    }

    /**
     *
     * @param meleeDamage
     */
    public void setMeleeDamage(int meleeDamage) {
        this.meleeDamage = meleeDamage;
    }

    /**
     *
     * @return
     */
    public int getRangeDamage() {
        return rangeDamage;
    }

    /**
     *
     * @param rangeDamage
     */
    public void setRangeDamage(int rangeDamage) {
        this.rangeDamage = rangeDamage;
    }
}
