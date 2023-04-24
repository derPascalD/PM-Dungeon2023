package level.tools;

import java.util.List;
import java.util.Random;

/** Specifies how large a level should be. Exact definition is interpreted by the generator. */
public enum LevelSize {
    SMALL,
    MEDIUM,
    LARGE;

    // List with SMALL, MEDIUM and LARGE
    private static final List<LevelSize> VALUES = List.of(values());

    // SIZE from VALUES
    private static final int SIZE = VALUES.size();

    // RANDOM Numbers
    private static final Random RANDOM = new Random();

    /**
     * Returns SMALL MEDIUM or LARGE
     * @return A random enum-value
     */
    public static LevelSize randomSize() {
        System.out.println(List.of(values()));
        System.out.println(VALUES.size());
        System.out.println(RANDOM.nextInt(SIZE));
        System.out.println(VALUES.get(RANDOM.nextInt(SIZE)));
        return VALUES.get(RANDOM.nextInt(SIZE));
    }



}
