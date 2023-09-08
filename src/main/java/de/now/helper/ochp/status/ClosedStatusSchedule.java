package de.now.helper.ochp.status;

import de.now.enums.ochp.ChargePointStatus;
import eu.ochp._1_4.ChargePointScheduleType;

public class ClosedStatusSchedule extends BaseStatusSchedule {

    private final ChargePointStatus requiredStatus;

    private long startDateInEpochSeconds;

    public ClosedStatusSchedule(ChargePointStatus requiredStatus) {
        this.requiredStatus = requiredStatus;
    }

    public void accept(final long scheduleStartDateEpochSeconds, final ChargePointScheduleType chargePointScheduleType) {
        if (nowInEpochSeconds > scheduleStartDateEpochSeconds) {
            return;
        }

        final ChargePointStatus chargePointStatus = ChargePointStatus.fromValue(chargePointScheduleType.getStatus().getChargePointStatusType());

        if (!requiredStatus.equals(chargePointStatus)) {
            return;
        }

        final long tempDifference = scheduleStartDateEpochSeconds - nowInEpochSeconds;

        if (tempDifference < difference) {
            difference = tempDifference;
            value = chargePointScheduleType;
            startDateInEpochSeconds = scheduleStartDateEpochSeconds;
        }
    }

    public long getStartDateInEpochSeconds() {
        return startDateInEpochSeconds;
    }

    @Override
    public String toString() {
        return "ClosedStatusSchedule{" +
                "nowInSeconds=" + nowInEpochSeconds +
                ", difference=" + difference +
                ", requiredStatus=" + requiredStatus +
                ", value=" + value +
                ", startDateInSeconds=" + startDateInEpochSeconds +
                '}';
    }
}
