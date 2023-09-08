package de.now.mapper.ochp;

import de.now.enums.ochp.BillingItem;
import de.now.helper.ochp.tariff.TariffMaxPrice;
import eu.datex2.schema._3.energyinfrastructure.ElectricEnergyMix;
import eu.datex2.schema._3.energyinfrastructure.EnergyPricingPolicy;
import eu.datex2.schema._3.energyinfrastructure.PricingPolicyEnum;
import eu.datex2.schema._3.energyinfrastructure.PricingPolicyEnum2;
import eu.datex2.schema._3.facilities.GeneralRateInformation;
import eu.ochp._1_4.IndividualTariffType;
import eu.ochp._1_4.PriceComponentType;
import eu.ochp._1_4.TariffElementType;
import eu.ochp._1_4.TariffInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TariffMapper {

    TariffMapper INSTANCE = Mappers.getMapper(TariffMapper.class);

    default List<ElectricEnergyMix> toElectricEnergyMix(final List<TariffInfo> tariffInfos) {

        final ElectricEnergyMix electricEnergyMixDummy = new ElectricEnergyMix();
        electricEnergyMixDummy.setEnergyMixIndex(BigInteger.ZERO);

        if (tariffInfos.isEmpty()) {
            return List.of(electricEnergyMixDummy);
        }

        final List<ElectricEnergyMix> result = new ArrayList<>(7);
        final String currency = tariffInfos.get(0).getIndividualTariff().get(0).getCurrency();
        final TariffMaxPrice tariffMaxPrice = getMaxPriceForEnergy(tariffInfos);

        final float maxPriceEnergy = tariffMaxPrice.getMaxPriceEnergy();
        if (maxPriceEnergy > 0) {
            result.add(createElectricEnergyMix(currency, maxPriceEnergy, PricingPolicyEnum2.PRICE_PER_DELIVERY_UNIT));
        }

        final float maxPriceUsageTime = tariffMaxPrice.getMaxPriceUsageTime();
        if (maxPriceUsageTime > 0) {
            result.add(createElectricEnergyMix(currency, maxPriceUsageTime, PricingPolicyEnum2.PRICE_PER_CHARGING_TIME));
        }

        final float maxPriceServiceFee = tariffMaxPrice.getMaxPriceServiceFee();
        if (maxPriceServiceFee > 0) {
            result.add(createElectricEnergyMix(currency, maxPriceServiceFee, PricingPolicyEnum2.FLAT_RATE));
        }

        final float maxPriceReservation = tariffMaxPrice.getMaxPriceReservation();
        if (maxPriceReservation > 0) {
            result.add(createElectricEnergyMix(currency, maxPriceReservation, PricingPolicyEnum2.OTHER));
        }

        final float maxPriceReservationTime = tariffMaxPrice.getMaxPriceReservationTime();
        if (maxPriceReservationTime > 0) {
            result.add(createElectricEnergyMix(currency, maxPriceReservationTime, PricingPolicyEnum2.OTHER));
        }

        final float maxPricePower = tariffMaxPrice.getMaxPricePower();
        if (maxPricePower > 0) {
            result.add(createElectricEnergyMix(currency, maxPricePower, PricingPolicyEnum2.OTHER));
        }

        final float maxPriceParkingTime = tariffMaxPrice.getMaxPriceParkingTime();
        if (maxPriceParkingTime > 0) {
            result.add(createElectricEnergyMix(currency, maxPriceParkingTime, PricingPolicyEnum2.OTHER));
        }

        if (result.isEmpty()) {
            result.add(electricEnergyMixDummy);
        }

        return result;
    }

    private ElectricEnergyMix createElectricEnergyMix(final String currency, final float maxPrice, final PricingPolicyEnum2 pricingPolicyEnum2) {
        final ElectricEnergyMix electricEnergyMix = new ElectricEnergyMix();
        electricEnergyMix.setEnergyMixIndex(BigInteger.ZERO);
        electricEnergyMix.setRates(getGeneralRateInformation(currency, maxPrice, pricingPolicyEnum2));
        return electricEnergyMix;
    }

    private PricingPolicyEnum getDeliveryUnitPricingPolicyEnum(final PricingPolicyEnum2 pricingPolicyEnum2) {
        final PricingPolicyEnum pricingPolicyEnum = new PricingPolicyEnum();
        pricingPolicyEnum.setValue(pricingPolicyEnum2);
        return pricingPolicyEnum;
    }

    private EnergyPricingPolicy getEnergyPricingPolicy(final float maxPriceForEnergy, final PricingPolicyEnum2 pricingPolicyEnum2) {
        final EnergyPricingPolicy energyPricingPolicy = new EnergyPricingPolicy();
        energyPricingPolicy.setMinimumDeliveryFee(BigDecimal.valueOf(maxPriceForEnergy));
        energyPricingPolicy.getPricingPolicy().add(getDeliveryUnitPricingPolicyEnum(pricingPolicyEnum2));
        return energyPricingPolicy;
    }

    private GeneralRateInformation getGeneralRateInformation(final String currency, final float maxPriceForEnergy, final PricingPolicyEnum2 pricingPolicyEnum2) {
        final GeneralRateInformation generalRateInformation = new GeneralRateInformation();
        generalRateInformation.getApplicableCurrency().add(currency);
        generalRateInformation.setEnergyPricingPolicy(getEnergyPricingPolicy(maxPriceForEnergy, pricingPolicyEnum2));
        return generalRateInformation;
    }

    private TariffMaxPrice getMaxPriceForEnergy(final List<TariffInfo> tariffInfos) {
        // looking for max price for all connectors and their tariffs
        float maxPriceEnergy = 0F;
        float maxPriceUsageTime = 0F;
        float maxPriceServiceFee = 0F;
        // others
        float maxPriceReservation = 0F;
        float maxPriceReservationTime = 0F;
        float maxPricePower = 0F;
        float maxPriceParkingTime = 0F;

        for (TariffInfo tariffInfo : tariffInfos) {
            final List<IndividualTariffType> individualTariffs = tariffInfo.getIndividualTariff();

            for (IndividualTariffType individualTariffType : individualTariffs) {
                final List<TariffElementType> tariffElements = individualTariffType.getTariffElement();

                for (TariffElementType tariffElementType : tariffElements) {
                    final PriceComponentType priceComponent = tariffElementType.getPriceComponent();

                    final BillingItem billingItem = BillingItem.fromValue(priceComponent.getBillingItem().getBillingItemType());

                    final float itemPrice = priceComponent.getItemPrice();

                    if (BillingItem.ENERGY.equals(billingItem)) {
                        if (maxPriceEnergy < itemPrice) {
                            maxPriceEnergy = itemPrice;
                        }
                    } else if (BillingItem.USAGE_TIME.equals(billingItem)) {
                        if (maxPriceUsageTime < itemPrice) {
                            maxPriceUsageTime = itemPrice;
                        }
                    } else if (BillingItem.SERVICE_FEE.equals(billingItem)) {
                        if (maxPriceServiceFee < itemPrice) {
                            maxPriceServiceFee = itemPrice;
                        }
                    } else if (BillingItem.RESERVATION.equals(billingItem)) {
                        if (maxPriceReservation < itemPrice) {
                            maxPriceReservation = itemPrice;
                        }
                    } else if (BillingItem.RESERVATION_TIME.equals(billingItem)) {
                        if (maxPriceReservationTime < itemPrice) {
                            maxPriceReservationTime = itemPrice;
                        }
                    } else if (BillingItem.POWER.equals(billingItem)) {
                        if (maxPricePower < itemPrice) {
                            maxPricePower = itemPrice;
                        }
                    } else if (BillingItem.PARKING_TIME.equals(billingItem)) {
                        if (maxPriceParkingTime < itemPrice) {
                            maxPriceParkingTime = itemPrice;
                        }
                    }
                }
            }
        }

        final TariffMaxPrice tariffMaxPrice = new TariffMaxPrice();
        tariffMaxPrice.setMaxPriceEnergy(maxPriceEnergy);
        tariffMaxPrice.setMaxPriceUsageTime(maxPriceUsageTime);
        tariffMaxPrice.setMaxPriceServiceFee(maxPriceServiceFee);
        tariffMaxPrice.setMaxPriceReservation(maxPriceReservation);
        tariffMaxPrice.setMaxPriceReservationTime(maxPriceReservationTime);
        tariffMaxPrice.setMaxPricePower(maxPricePower);
        tariffMaxPrice.setMaxPriceParkingTime(maxPriceParkingTime);

        return tariffMaxPrice;
    }
}
