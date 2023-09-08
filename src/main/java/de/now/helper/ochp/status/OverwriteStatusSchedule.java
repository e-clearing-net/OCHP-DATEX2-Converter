package de.now.helper.ochp.status;

import de.now.helper.DateTimeHelper;
import eu.ochp._1_4.ChargePointScheduleType;
import eu.ochp._1_4.DateTimeType;

public class OverwriteStatusSchedule extends BaseStatusSchedule {

    public void accept(final long scheduleStartDateEpochSeconds, final ChargePointScheduleType chargePointScheduleType) {
        if (nowInEpochSeconds < scheduleStartDateEpochSeconds) {
            return;
        }

        final long scheduledEnd = getScheduledEndAsEpochSeconds(chargePointScheduleType.getEndDate());

        if (scheduledEnd <= nowInEpochSeconds) {
            return;
        }

        final long tempDifference = nowInEpochSeconds - scheduleStartDateEpochSeconds;
        if (tempDifference < difference) {
            difference = tempDifference;
            value = chargePointScheduleType;
        }
    }

    @Override
    public String toString() {
        return "OverwriteStatusSchedule{" +
                "nowInSeconds=" + nowInEpochSeconds +
                ", difference=" + difference +
                ", value=" + value +
                '}';
    }

    private long getScheduledEndAsEpochSeconds(final DateTimeType endDate) {
        if (endDate == null) {
            return nowInEpochSeconds;
        }

        return DateTimeHelper.parseToEpochSeconds(endDate);
    }
}
