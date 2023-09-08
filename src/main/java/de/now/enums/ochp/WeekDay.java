package de.now.enums.ochp;

/**
 * Monday (1), ending with Sunday (7)
 */
public enum WeekDay {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    WeekDay(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    private static final WeekDay[] ALL_VALUES = WeekDay.values();
    // +1 for index zero 0
    private static final int CACHE_LENGTH = ALL_VALUES.length + 1;
    private static final WeekDay[] CACHE = new WeekDay[CACHE_LENGTH];

    static {
        for (WeekDay weekDay : ALL_VALUES) {
            CACHE[weekDay.getValue()] = weekDay;
        }
    }

    public static WeekDay fromValue(final int value) {
        if (0 < value && value < CACHE_LENGTH) {
            return CACHE[value];
        }

        return null;
    }
}
