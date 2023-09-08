package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointStatus;
import de.now.enums.ochp.EvseStatusMajor;
import de.now.enums.ochp.EvseStatusMinor;
import de.now.factory.OchpFactory;
import de.now.helper.DateTimeHelper;
import eu.datex2.schema._3.energyinfrastructure.ElectricChargingPointStatus;
import eu.datex2.schema._3.energyinfrastructure.RefillPointStatus;
import eu.datex2.schema._3.energyinfrastructure.RefillPointStatusEnum;
import eu.datex2.schema._3.energyinfrastructure.RefillPointStatusEnum2;
import eu.datex2.schema._3.facilities.FacilityObjectVersionedReference;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.ChargePointScheduleType;
import eu.ochp._1_4.DateTimeType;
import eu.ochp._1_4.EvseStatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StatusMapperTest {

    @Test
    void mapElectricChargingPointStatus() {
        // given
        final String inEvseId = "DE*BMW*E1";
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId(inEvseId);
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.CLOSED));
        // when
        final ElectricChargingPointStatus electricChargingPointStatus = StatusMapper.INSTANCE.toElectricChargingPointStatus(chargePointInfo, null);
        // then
        assertThat(electricChargingPointStatus).isNotNull();

        final FacilityObjectVersionedReference reference = electricChargingPointStatus.getReference();
        assertThat(reference).isNotNull();

        final String id = reference.getId();
        assertThat(id).isNotNull().isEqualTo(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(inEvseId));

        final RefillPointStatusEnum refillPointStatusEnum = electricChargingPointStatus.getStatus();
        assertThat(refillPointStatusEnum).isNotNull();

        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusEnum.getValue();
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.REMOVED);
    }

    @Test
    void mapRefillPointStatusChargePointInfoWithLiveStatus() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.OPERATIVE));

        final EvseStatusType evseStatusType = OchpFactory.createEvseStatusType(
                "DE*BMW*E2", EvseStatusMajor.AVAILABLE, EvseStatusMinor.RESERVED, null
        );
        // when
        final RefillPointStatus refillPointStatus = StatusMapper.INSTANCE.toRefillPointStatus(chargePointInfo, evseStatusType);
        // then
        assertThat(refillPointStatus).isNotNull();

        final RefillPointStatusEnum refillPointStatusEnum = refillPointStatus.getStatus();
        assertThat(refillPointStatusEnum).isNotNull();

        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusEnum.getValue();
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.RESERVED);
    }

    @Test
    void mapRefillPointStatusChargePointInfoWithoutLiveStatus() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.OPERATIVE));

        final EvseStatusType evseStatusType = OchpFactory.createEvseStatusType(
                "DEBMW*E2", EvseStatusMajor.AVAILABLE, null, null
        );
        // when
        final RefillPointStatus refillPointStatus = StatusMapper.INSTANCE.toRefillPointStatus(chargePointInfo, evseStatusType);
        // then
        assertThat(refillPointStatus).isNotNull();

        final RefillPointStatusEnum refillPointStatusEnum = refillPointStatus.getStatus();
        assertThat(refillPointStatusEnum).isNotNull();

        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusEnum.getValue();
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.AVAILABLE);
    }

    @Test
    void mapRefillPointStatus() {
        // given
        final OffsetDateTime offsetDateTimeStart = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(2); // in the past!
        final DateTimeType startDateTime = OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(offsetDateTimeStart));

        final OffsetDateTime offsetDateTimeEnd = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(3);
        final DateTimeType endDateTime = OchpFactory.createDateTimeType(DateTimeHelper.formatToIsoUTC(offsetDateTimeEnd));

        final ChargePointStatus overwriteStatus = ChargePointStatus.INOPERATIVE;
        final ChargePointScheduleType statusSchedule = OchpFactory.createChargePointScheduleType(
                startDateTime, endDateTime, OchpFactory.createChargePointStatusType(overwriteStatus)
        );

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.OPERATIVE));
        chargePointInfo.getStatusSchedule().add(statusSchedule);
        // when
        final RefillPointStatus refillPointStatus = StatusMapper.INSTANCE.toRefillPointStatus(chargePointInfo);
        // then
        assertThat(refillPointStatus).isNotNull();

        final RefillPointStatusEnum refillPointStatusEnum = refillPointStatus.getStatus();
        assertThat(refillPointStatusEnum).isNotNull();

        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusEnum.getValue();
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.INOPERATIVE);
    }

    @Test
    void mapRefillPointStatusEnum() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.OPERATIVE));
        // when
        final RefillPointStatusEnum refillPointStatusEnum = StatusMapper.INSTANCE.toRefillPointStatusEnum(chargePointInfo);
        // then
        assertThat(refillPointStatusEnum).isNotNull();

        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusEnum.getValue();
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.UNKNOWN);
    }

    @Test
    void mapRefillPointStatusEnum2FromChargePointInfo() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setStatus(OchpFactory.createChargePointStatusType(ChargePointStatus.CLOSED));
        // when
        final RefillPointStatusEnum2 refillPointStatusEnum2 = StatusMapper.INSTANCE.toRefillPointStatusEnum2(chargePointInfo);
        // then
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.REMOVED);
    }

    @Test
    void mapRefillPointStatusUnknown() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        // when
        final RefillPointStatus refillPointStatus = StatusMapper.INSTANCE.toRefillPointStatus(chargePointInfo);
        // then
        assertThat(refillPointStatus).isNotNull();

        final RefillPointStatusEnum refillPointStatusEnum = refillPointStatus.getStatus();
        assertThat(refillPointStatusEnum).isNotNull();

        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusEnum.getValue();
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(RefillPointStatusEnum2.UNKNOWN);
    }

    private static Stream<Arguments> getTestValues() {
        return Stream.of(
                Arguments.of(ChargePointStatus.OPERATIVE, RefillPointStatusEnum2.UNKNOWN),
                Arguments.of(ChargePointStatus.CLOSED, RefillPointStatusEnum2.REMOVED),
                Arguments.of(ChargePointStatus.INOPERATIVE, RefillPointStatusEnum2.INOPERATIVE),
                Arguments.of(ChargePointStatus.PLANNED, RefillPointStatusEnum2.PLANNED),
                Arguments.of(ChargePointStatus.UNKNOWN, RefillPointStatusEnum2.UNKNOWN)
        );
    }

    @ParameterizedTest(name = "ChargePointStatus {0}, expected {1}")
    @MethodSource("getTestValues")
    void mapRefillPointStatusEnum2(final ChargePointStatus chargePointStatus, RefillPointStatusEnum2 expected) {
        // when
        final RefillPointStatusEnum2 refillPointStatusEnum2 = StatusMapper.INSTANCE.toRefillPointStatusEnum2(chargePointStatus);
        // then
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(expected);
    }

    /////////////////////////////////////////////
    // Live-Status (EvseStatusType) mapping

    private static Stream<Arguments> getTestValuesMajor() {
        return Stream.of(
                Arguments.of(EvseStatusMajor.AVAILABLE, RefillPointStatusEnum2.AVAILABLE),
                Arguments.of(EvseStatusMajor.NOT_AVAILABLE, RefillPointStatusEnum2.CHARGING),
                Arguments.of(EvseStatusMajor.UNKNOWN, RefillPointStatusEnum2.UNKNOWN)
        );
    }

    @ParameterizedTest(name = "EvseStatusMajor {0}, expected {1}")
    @MethodSource("getTestValuesMajor")
    void mapRefillPointStatusEnum2ForStatusMajor(final EvseStatusMajor evseStatusMajor, final RefillPointStatusEnum2 expected) {
        // when
        final RefillPointStatusEnum2 refillPointStatusEnum2 = StatusMapper.INSTANCE.toRefillPointStatusEnum2(evseStatusMajor);
        // then
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(expected);
    }

    private static Stream<Arguments> getTestValuesMinor() {
        return Stream.of(
                Arguments.of(EvseStatusMinor.AVAILABLE, RefillPointStatusEnum2.AVAILABLE),
                Arguments.of(EvseStatusMinor.BLOCKED, RefillPointStatusEnum2.BLOCKED),
                Arguments.of(EvseStatusMinor.CHARGING, RefillPointStatusEnum2.CHARGING),
                Arguments.of(EvseStatusMinor.OUT_OF_ORDER, RefillPointStatusEnum2.OUT_OF_ORDER),
                Arguments.of(EvseStatusMinor.RESERVED, RefillPointStatusEnum2.RESERVED)

        );
    }

    @ParameterizedTest(name = "EvseStatusMinor {0}, expected {1}")
    @MethodSource("getTestValuesMinor")
    void mapRefillPointStatusEnum2ForStatusMinor(final EvseStatusMinor evseStatusMinor, final RefillPointStatusEnum2 expected) {
        // when
        final RefillPointStatusEnum2 refillPointStatusEnum2 = StatusMapper.INSTANCE.toRefillPointStatusEnum2(evseStatusMinor);
        // then
        assertThat(refillPointStatusEnum2).isNotNull().isEqualTo(expected);
    }
}
