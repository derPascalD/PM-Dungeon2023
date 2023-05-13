package ecs.components.skill;

public abstract class RangedAbilities implements ISkillFunction {

        private int damage;
        private int range;
        private float speed;
        private boolean bouncesOffWalls;

        public RangedAbilities(int damage, int range, float speed,boolean bouncesOffWalls) {
            this.damage = damage;
            this.range = range;
            this.speed = speed;
            this.bouncesOffWalls = bouncesOffWalls;
        }


        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }

        public float getSpeed() {
            return speed;
        }

        public boolean doesBounceOffWalls() {
            return bouncesOffWalls;
        }



}
