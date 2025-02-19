package configuration;

import com.badlogic.gdx.Input;
import configuration.values.ConfigIntValue;

@ConfigMap(path = {"keyboard"})
public class KeyboardConfig {

    public static final ConfigKey<Integer> MOVEMENT_UP =
            new ConfigKey<>(new String[] {"movement", "up"}, new ConfigIntValue(Input.Keys.W));
    public static final ConfigKey<Integer> MOVEMENT_DOWN =
            new ConfigKey<>(new String[] {"movement", "down"}, new ConfigIntValue(Input.Keys.S));
    public static final ConfigKey<Integer> MOVEMENT_LEFT =
            new ConfigKey<>(new String[] {"movement", "left"}, new ConfigIntValue(Input.Keys.A));
    public static final ConfigKey<Integer> MOVEMENT_RIGHT =
            new ConfigKey<>(new String[] {"movement", "right"}, new ConfigIntValue(Input.Keys.D));
    public static final ConfigKey<Integer> INVENTORY_OPEN =
            new ConfigKey<>(new String[] {"inventory", "open"}, new ConfigIntValue(Input.Keys.I));
    public static final ConfigKey<Integer> INTERACT_WORLD =
            new ConfigKey<>(new String[] {"interact", "world"}, new ConfigIntValue(Input.Keys.E));
    public static final ConfigKey<Integer> FIRST_SKILL =
            new ConfigKey<>(new String[] {"skill", "first"}, new ConfigIntValue(Input.Keys.NUM_1));
    public static final ConfigKey<Integer> SECOND_SKILL =
            new ConfigKey<>(new String[] {"skill", "second"}, new ConfigIntValue(Input.Keys.NUM_2));
    public static final ConfigKey<Integer> THIRD_SKILL =
            new ConfigKey<>(new String[] {"skill", "third"}, new ConfigIntValue(Input.Keys.NUM_3));
    public static final ConfigKey<Integer> EQUIP_WEAPON =
            new ConfigKey<>(new String[] {"skill", "weapon"}, new ConfigIntValue(Input.Keys.NUM_6));
    public static final ConfigKey<Integer> GRENADE_LAUNCH =
            new ConfigKey<>(new String[] {"skill", "fourth"}, new ConfigIntValue(Input.Keys.NUM_4));
    public static final ConfigKey<Integer> NINJA_BLADE =
            new ConfigKey<>(new String[] {"skill", "fifth"}, new ConfigIntValue(Input.Keys.NUM_5));
    public static final ConfigKey<Integer> HEAL_POTION =
            new ConfigKey<>(new String[] {"item", "heal"}, new ConfigIntValue(Input.Keys.Q));
    public static final ConfigKey<Integer> COMBAT_ATTACK =
            new ConfigKey<>(new String[] {"melee", "attack"}, new ConfigIntValue(Input.Keys.R));
    public static final ConfigKey<Integer> TOGGLE_QUESTS =
            new ConfigKey<>(new String[] {"quest", "toggle"}, new ConfigIntValue(Input.Keys.L));
    public static final ConfigKey<Integer> ACCEPT_QUEST =
            new ConfigKey<>(new String[] {"quest", "accept"}, new ConfigIntValue(Input.Keys.H));
    public static final ConfigKey<Integer> NEXT_QUESTS =
            new ConfigKey<>(new String[] {"quest", "next"}, new ConfigIntValue(Input.Keys.K));
}
