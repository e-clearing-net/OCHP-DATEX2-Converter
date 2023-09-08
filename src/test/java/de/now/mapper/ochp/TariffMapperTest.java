package de.now.mapper.ochp;

import de.now.enums.ochp.BillingItem;
import de.now.factory.OchpFactory;
import eu.datex2.schema._3.energyinfrastructure.ElectricEnergyMix;
import eu.datex2.schema._3.energyinfrastructure.EnergyPricingPolicy;
import eu.datex2.schema._3.energyinfrastructure.PricingPolicyEnum;
import eu.datex2.schema._3.energyinfrastructure.PricingPolicyEnum2;
import eu.datex2.schema._3.facilities.GeneralRateInformation;
import eu.datex2.schema._3.facilities.Rates;
import eu.ochp._1_4.TariffInfo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TariffMapperTest {

    @Test
    void mapElectricEnergyMix() {
        // given
        final String inCurrency = "EUR";

        final String inTariffId1 = "testTariff1";
        final float inItemPrice1 = 0.20F;
        final BillingItem inBillingItem1 = BillingItem.ENERGY;
        final float inStepSize1 = 0.001F;
        final TariffInfo tariffInfo1 = OchpFactory.createTariffInfo(inTariffId1, inCurrency, inItemPrice1, inBillingItem1, inStepSize1);

        final String inTariffId2 = "testTariff2";
        final float inItemPrice2 = 0.25F;
        final BillingItem inBillingItem2 = BillingItem.ENERGY;
        final float inStepSize2 = 0.001F;
        final TariffInfo tariffInfo2 = OchpFactory.createTariffInfo(
                inTariffId2, inCurrency, inItemPrice2, inBillingItem2, inStepSize2
        );

        final String inTariffId3 = "testTariff3";
        final float inItemPrice3 = 1F;
        final BillingItem inBillingItem3 = BillingItem.PARKING_TIME;
        final float inStepSize3 = 0.01F;
        final TariffInfo tariffInfo3 = OchpFactory.createTariffInfo(
                inTariffId3, inCurrency, inItemPrice3, inBillingItem3, inStepSize3
        );
        // when
        final List<ElectricEnergyMix> electricEnergyMixes = TariffMapper.INSTANCE.toElectricEnergyMix(
                List.of(tariffInfo1, tariffInfo2, tariffInfo3)
        );
        // then
        assertThat(electricEnergyMixes).isNotNull().hasSize(2);

        final ElectricEnergyMix electricEnergyMix = electricEnergyMixes.get(0);
        assertThat(electricEnergyMix).isNotNull();

        final BigInteger energyMixIndex = electricEnergyMix.getEnergyMixIndex();
        assertThat(energyMixIndex).isNotNull().isEqualTo(BigInteger.ZERO);

        final Rates rates = electricEnergyMix.getRates();
        assertThat(rates).isNotNull();
        assertThat(rates.getClass()).isEqualTo(GeneralRateInformation.class);

        final List<String> applicableCurrency = rates.getApplicableCurrency();
        assertThat(applicableCurrency).hasSize(1);

        final String currency = applicableCurrency.get(0);
        assertThat(currency).isNotNull().isEqualTo(inCurrency);

        final EnergyPricingPolicy energyPricingPolicy = rates.getEnergyPricingPolicy();
        assertThat(energyPricingPolicy).isNotNull();

        final BigDecimal minimumDeliveryFee = energyPricingPolicy.getMinimumDeliveryFee();
        assertThat(minimumDeliveryFee).isNotNull().isEqualTo(BigDecimal.valueOf(inItemPrice2));

        final List<PricingPolicyEnum> pricingPolicy = energyPricingPolicy.getPricingPolicy();
        assertThat(pricingPolicy).hasSize(1);

        final PricingPolicyEnum pricingPolicyEnum = pricingPolicy.get(0);
        assertThat(pricingPolicyEnum).isNotNull();

        final PricingPolicyEnum2 pricingPolicyEnum2 = pricingPolicyEnum.getValue();
        assertThat(pricingPolicyEnum2).isNotNull().isEqualTo(PricingPolicyEnum2.PRICE_PER_DELIVERY_UNIT);

        // Second Element

        final ElectricEnergyMix electricEnergyMixParkingTime = electricEnergyMixes.get(1);
        assertThat(electricEnergyMixParkingTime).isNotNull();

        final Rates ratesParkingTime = electricEnergyMixParkingTime.getRates();
        assertThat(ratesParkingTime).isNotNull();
        assertThat(ratesParkingTime.getClass()).isEqualTo(GeneralRateInformation.class);

        final EnergyPricingPolicy energyPricingPolicyParkingTime = ratesParkingTime.getEnergyPricingPolicy();
        assertThat(energyPricingPolicyParkingTime).isNotNull();

        final BigDecimal minimumDeliveryFeeParkingTime = energyPricingPolicyParkingTime.getMinimumDeliveryFee();
        assertThat(minimumDeliveryFeeParkingTime).isNotNull().isEqualTo(BigDecimal.valueOf(inItemPrice3));

        final PricingPolicyEnum2 pricingPolicyEnum2ParkingTime = energyPricingPolicyParkingTime.getPricingPolicy().get(0).getValue();
        assertThat(pricingPolicyEnum2ParkingTime).isNotNull().isEqualTo(PricingPolicyEnum2.OTHER);
    }
}