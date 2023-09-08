package de.now.mapper.ochp;

import de.now.enums.ochp.ChargePointStatus;
import de.now.enums.ochp.EvseStatusMajor;
import de.now.enums.ochp.EvseStatusMinor;
import de.now.helper.DateTimeHelper;
import de.now.helper.ochp.status.OverwriteStatusSchedule;
import eu.datex2.schema._3.energyinfrastructure.ElectricChargingPointStatus;
import eu.datex2.schema._3.energyinfrastructure.RefillPointStatus;
import eu.datex2.schema._3.energyinfrastructure.RefillPointStatusEnum;
import eu.datex2.schema._3.energyinfrastructure.RefillPointStatusEnum2;
import eu.datex2.schema._3.facilities.FacilityObjectVersionedReference;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.ChargePointScheduleType;
import eu.ochp._1_4.ChargePointStatusType;
import eu.ochp._1_4.EvseStatusType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StatusMapper {

    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    @Mapping(source = ".", target = "status", qualifiedByName = "toRefillPointStatusEnum")
    RefillPointStatus toRefillPointStatus(final ChargePointInfo chargePointInfo);

    @Mapping(source = ".", target = "value")
    @Named("toRefillPointStatusEnum")
    RefillPointStatusEnum toRefillPointStatusEnum(final ChargePointInfo chargePointInfo);

    @ValueMapping(source = "OPERATIVE", target = "UNKNOWN")
    @ValueMapping(source = "CLOSED", target = "REMOVED")
    RefillPointStatusEnum2 toRefillPointStatusEnum2(final ChargePointStatus chargePointStatus);

    @ValueMapping(source = "NOT_AVAILABLE", target = "CHARGING")
    RefillPointStatusEnum2 toRefillPointStatusEnum2(final EvseStatusMajor evseStatusMajor);

    RefillPointStatusEnum2 toRefillPointStatusEnum2(final EvseStatusMinor evseStatusMinor);

    default ElectricChargingPointStatus toElectricChargingPointStatus(final ChargePointInfo chargePointInfo, final EvseStatusType evseStatusType) {
        final ElectricChargingPointStatus energyInfrastructureSiteStatus = new ElectricChargingPointStatus();
        energyInfrastructureSiteStatus.setStatus(toRefillPointStatus(chargePointInfo, evseStatusType).getStatus());
        energyInfrastructureSiteStatus.setReference(toFacilityObjectVersionedReference(chargePointInfo));
        return energyInfrastructureSiteStatus;
    }

    default RefillPointStatus toRefillPointStatus(final ChargePointInfo chargePointInfo, final EvseStatusType evseStatusType) {
        final RefillPointStatus refillPointStatusStatic = toRefillPointStatus(chargePointInfo);
        final RefillPointStatusEnum2 refillPointStatusEnum2 = refillPointStatusStatic.getStatus().getValue();

        // Static Status INOPERATIVE, PLANNED and REMOVED returned directly - no LiveStatus check needed.
        boolean returnStaticStatus = RefillPointStatusEnum2.INOPERATIVE.equals(refillPointStatusEnum2)
                || RefillPointStatusEnum2.PLANNED.equals(refillPointStatusEnum2)
                || RefillPointStatusEnum2.REMOVED.equals(refillPointStatusEnum2);

        if (returnStaticStatus) {
            return refillPointStatusStatic;
        }

        // process Operative and Unknown - different result if EvseStatusType is set.
        return toRefillPointStatus(evseStatusType, refillPointStatusStatic);
    }

    default RefillPointStatusEnum2 toRefillPointStatusEnum2(final ChargePointInfo chargePointInfo) {

        final ChargePointStatusType status = chargePointInfo.getStatus();
        final List<ChargePointScheduleType> statusSchedule = chargePointInfo.getStatusSchedule();

        if (status == null && statusSchedule.isEmpty()) {
            return RefillPointStatusEnum2.UNKNOWN;
        }

        final OverwriteStatusSchedule overwriteStatusSchedule = new OverwriteStatusSchedule();
        for (ChargePointScheduleType chargePointScheduleType : statusSchedule) {
            overwriteStatusSchedule.accept(DateTimeHelper.parseToEpochSeconds(chargePointScheduleType.getStartDate()), chargePointScheduleType);
        }

        final ChargePointStatusType overwrittenStatus = overwriteStatusSchedule.hasValue() ? overwriteStatusSchedule.getValue().getStatus() : status;

        if (overwrittenStatus == null) {
            return RefillPointStatusEnum2.UNKNOWN;
        }

        final ChargePointStatus chargePointStatus = ChargePointStatus.fromValue(overwrittenStatus.getChargePointStatusType());

        return toRefillPointStatusEnum2(chargePointStatus);
    }

    private FacilityObjectVersionedReference toFacilityObjectVersionedReference(final ChargePointInfo chargePointInfo) {
        final FacilityObjectVersionedReference facilityObjectVersionedReference = new FacilityObjectVersionedReference();
        facilityObjectVersionedReference.setId(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(chargePointInfo.getEvseId()));
        facilityObjectVersionedReference.setTargetClass("fac:FacilityObject");
        // FacilityObjectVersionedReference version is set in the Push Service
        return facilityObjectVersionedReference;
    }

    private RefillPointStatus toRefillPointStatus(final EvseStatusType evseStatusType, final RefillPointStatus refillPointStatusStatic) {

        if (evseStatusType == null) {
            return refillPointStatusStatic;
        }

        // Handling Static Status - Operative and Unknown
        // Different results if EvseStatusMajor and EvseStatusMinor are set
        final EvseStatusMinor evseStatusMinor = EvseStatusMinor.fromValue(evseStatusType.getMinor());

        final RefillPointStatusEnum2 liveStatus;

        if (evseStatusMinor != null) {
            liveStatus = toRefillPointStatusEnum2(evseStatusMinor);
        } else {
            liveStatus = toRefillPointStatusEnum2(EvseStatusMajor.fromValue(evseStatusType.getMajor()));
        }

        refillPointStatusStatic.getStatus().setValue(liveStatus);

        return refillPointStatusStatic;
    }
}
