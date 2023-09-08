package de.now.helper.ochp.status;

import eu.ochp._1_4.ChargePointScheduleType;

public class HelperStatus {

    private HelperStatus() {
    }

    public static ChargePointScheduleType selectClosestInFutureStatus(
            final ClosedStatusSchedule futureClosed,
            final ClosedStatusSchedule futureInoperative
    ) {
        // searching for the closest in future status
        final boolean futureClosedExists = futureClosed.hasValue();
        final boolean futureInoperativeExists = futureInoperative.hasValue();

        if (!futureClosedExists && !futureInoperativeExists) {
            return null;
        }

        if (!futureClosedExists) {
            return futureInoperative.getValue();
        }

        if (!futureInoperativeExists) {
            return futureClosed.getValue();
        }

        // both statuses exist - get the closest status (with the smaller timestamp value)
        if (futureInoperative.getStartDateInEpochSeconds() < futureClosed.getStartDateInEpochSeconds()) {
            return futureInoperative.getValue();
        }

        return futureClosed.getValue();
    }
}
