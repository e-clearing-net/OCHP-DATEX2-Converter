package de.now.mapper.ochp;

import de.now.enums.ochp.ImageClass;
import de.now.enums.ochp.RelatedResourceClass;
import de.now.factory.OchpFactory;
import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.common.MultilingualStringValue;
import eu.datex2.schema._3.facilities.ContactInformation;
import eu.datex2.schema._3.facilities.Organisation;
import eu.datex2.schema._3.facilities.OrganisationSpecification;
import eu.datex2.schema._3.facilities.OrganisationUnit;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.EvseImageUrlType;
import eu.ochp._1_4.RelatedResourceType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContactDataMapperTest {

    @Test
    void mapOrganisation() {
        // given
        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId("DEBMW*SOMTHING");
        // when
        final Organisation organisation = ContactDataMapper.INSTANCE.toOrganisation(chargePointInfo);
        // then
        assertThat(organisation).isNotNull();
        assertThat(organisation.getClass()).isEqualTo(OrganisationSpecification.class);
    }

    @Test
    void mapOrganisationSpecification() {
        // given
        final String inStationInfoUri = "https://stationinfo";
        final RelatedResourceType relatedResourceTypeInfo = OchpFactory.createRelatedResourceType(RelatedResourceClass.STATION_INFO, inStationInfoUri);

        final String inFeedbackUri = "https://feedback";
        final RelatedResourceType relatedResourceTypeFeedback = OchpFactory.createRelatedResourceType(RelatedResourceClass.FEEDBACK_FORM, inFeedbackUri);

        final String inLogoUri = "https://logo";
        final EvseImageUrlType evseImageUrlType = OchpFactory.createEvseImageUrlType(inLogoUri, ImageClass.OPERATOR_LOGO, "dummy");

        final String inEvseId = "DE*BMW*SOMTHING";

        final ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setEvseId(inEvseId);
        chargePointInfo.setTelephoneNumber("+49 123 456 789");
        chargePointInfo.getRelatedResource().addAll(List.of(relatedResourceTypeInfo, relatedResourceTypeFeedback));
        chargePointInfo.getImages().add(evseImageUrlType);
        // when
        final OrganisationSpecification organisationSpecification = ContactDataMapper.INSTANCE.toOrganisationSpecification(chargePointInfo);
        // then

        final String id = organisationSpecification.getId();
        assertThat(id).isNotNull().isEqualTo(NameTimestampVersionMapper.INSTANCE.cleanUpAsterisk(inEvseId));

        final String version = organisationSpecification.getVersion();
        assertThat(version).isNotNull();

        final String linkToGeneralInformation = organisationSpecification.getLinkToGeneralInformation();
        assertThat(linkToGeneralInformation).isNotNull().isEqualTo(inStationInfoUri);

        final String linkToWebform = organisationSpecification.getLinkToWebform();
        assertThat(linkToWebform).isNotNull().isEqualTo(inFeedbackUri);

        final String linkToLogo = organisationSpecification.getLinkToLogo();
        assertThat(linkToLogo).isNotNull().isEqualTo(inLogoUri);

        final MultilingualString name = organisationSpecification.getName();
        assertThat(name).isNotNull();

        final List<MultilingualStringValue> values = name.getValues().getValue();
        assertThat(values).hasSize(1);

        final MultilingualStringValue multilingualStringValue = values.get(0);
        assertThat(multilingualStringValue).isNotNull();

        final String value = multilingualStringValue.getValue();
        assertThat(value).isNotNull().isEqualTo("DEBMW");

        final String lang = multilingualStringValue.getLang();
        assertThat(lang).isNotNull().isEqualTo("en");

        final List<OrganisationUnit> organisationUnits = organisationSpecification.getOrganisationUnit();
        assertThat(organisationUnits).hasSize(1);

        final OrganisationUnit organisationUnit = organisationUnits.get(0);
        assertThat(organisationUnit).isNotNull();
    }

    @Test
    void mapOrganisationUnit() {
        // given
        final String inPhoneNumber = "+49 123 456 789";

        // when
        final OrganisationUnit organisationUnit = ContactDataMapper.INSTANCE.toOrganisationUnit(inPhoneNumber);
        // then
        assertThat(organisationUnit).isNotNull();

        final List<ContactInformation> contactInformation = organisationUnit.getContactInformation();
        assertThat(contactInformation).hasSize(1);

        final ContactInformation contactInfo = contactInformation.get(0);
        assertThat(contactInfo).isNotNull();
    }

    @Test
    void mapContactInformation() {
        // given
        final String inPhoneNumber = "+49 123 456 789";

        // when
        final ContactInformation contactInfo = ContactDataMapper.INSTANCE.toContactInformation(inPhoneNumber);

        // then
        assertThat(contactInfo).isNotNull();

        final String telephoneNumber = contactInfo.getTelephoneNumber();
        assertThat(telephoneNumber).isNotNull().isEqualTo(inPhoneNumber);
    }
}
