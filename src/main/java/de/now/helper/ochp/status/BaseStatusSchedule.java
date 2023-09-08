package de.now.helper.ochp.status;

import de.now.helper.DateTimeHelper;
import eu.ochp._1_4.ChargePointScheduleType;

abstract class BaseStatusSchedule {

    protected final long nowInEpochSeconds = DateTimeHelper.getNowAtUtcAsEpochSeconds();

    protected long difference = 630_720_000L; // 20 years in seconds

    protected ChargePointScheduleType value;

    public ChargePointScheduleType getValue() {
        return value;
    }

    public boolean hasValue() {
        return value != null;
    }

    public abstract void accept(final long scheduleStartDateEpochSeconds, final ChargePointScheduleType chargePointScheduleType);
}
