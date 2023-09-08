package de.now.mapper.ochp;

import de.now.enums.ochp.ImageClass;
import de.now.enums.ochp.RelatedResourceClass;
import de.now.helper.ochp.link.InfoLink;
import eu.datex2.schema._3.common.MultilingualString;
import eu.datex2.schema._3.facilities.ContactInformation;
import eu.datex2.schema._3.facilities.Organisation;
import eu.datex2.schema._3.facilities.OrganisationSpecification;
import eu.datex2.schema._3.facilities.OrganisationUnit;
import eu.ochp._1_4.ChargePointInfo;
import eu.ochp._1_4.EvseImageUrlType;
import eu.ochp._1_4.RelatedResourceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Mapper
public interface ContactDataMapper {

    ContactDataMapper INSTANCE = Mappers.getMapper(ContactDataMapper.class);

    @Mapping(source = ".", target = "telephoneNumber")
    ContactInformation toContactInformation(final String telephoneNumber);

    @ObjectFactory
    default Organisation toOrganisation(final ChargePointInfo chargePointInfo) {
        return toOrganisationSpecification(chargePointInfo);
    }

    default OrganisationSpecification toOrganisationSpecification(final ChargePointInfo chargePointInfo) {

        final NameTimestampVersionMapper nameTimestampVersionMapper = NameTimestampVersionMapper.INSTANCE;

        final OrganisationSpecification organisationSpecification = new OrganisationSpecification();
        organisationSpecification.setId(nameTimestampVersionMapper.cleanUpAsterisk(chargePointInfo.getEvseId())); // id required
        organisationSpecification.setVersion(toVersion()); // version required

        final MultilingualString name = nameTimestampVersionMapper.toOperatorName(chargePointInfo);
        organisationSpecification.setName(name);

        final OrganisationUnit organisationUnit = toOrganisationUnit(chargePointInfo.getTelephoneNumber());
        organisationSpecification.getOrganisationUnit().add(organisationUnit);

        final InfoLink infoLink = new InfoLink();
        getLogo(chargePointInfo.getImages(), infoLink);
        getWebLinks(chargePointInfo.getRelatedResource(), infoLink);

        organisationSpecification.setLinkToGeneralInformation(infoLink.getLinkToGeneralInformation());
        organisationSpecification.setLinkToLogo(infoLink.getLinkToLogo());
        organisationSpecification.setLinkToWebform(infoLink.getLinkToWebForm());

        return organisationSpecification;
    }

    default OrganisationUnit toOrganisationUnit(final String telephoneNumber) {
        final OrganisationUnit organisationUnit = new OrganisationUnit();
        if (telephoneNumber == null) {
            // will return object without data - needed in OrganisationSpecification (required list?)
            return organisationUnit;
        }

        organisationUnit.getContactInformation().add(toContactInformation(telephoneNumber));

        return organisationUnit;
    }

    default String toVersion() {
        return LocalDate.now(ZoneOffset.UTC).toString();
    }

    private void getWebLinks(final List<RelatedResourceType> relatedResourceTypes, final InfoLink infoLink) {
        if (relatedResourceTypes.isEmpty()) {
            return;
        }

        RelatedResourceType feedbackForm = null;
        RelatedResourceType ownerHomepage = null;
        RelatedResourceType stationInfo = null;

        for (RelatedResourceType relatedResourceType : relatedResourceTypes) {
            final RelatedResourceClass relatedResourceClass = RelatedResourceClass.fromValue(relatedResourceType.getClazz());

            if (RelatedResourceClass.FEEDBACK_FORM.equals(relatedResourceClass)) {
                feedbackForm = relatedResourceType;
            } else if (RelatedResourceClass.OWNER_HOMEPAGE.equals(relatedResourceClass)) {
                ownerHomepage = relatedResourceType;
            } else if (RelatedResourceClass.STATION_INFO.equals(relatedResourceClass)) {
                stationInfo = relatedResourceType;
            }
        }

        if (feedbackForm != null) {
            infoLink.setLinkToWebForm(feedbackForm.getUri());
        }

        final RelatedResourceType tempInfo = stationInfo != null ? stationInfo : ownerHomepage;
        if (tempInfo != null) {
            infoLink.setLinkToGeneralInformation(tempInfo.getUri());
        }
    }

    private void getLogo(final List<EvseImageUrlType> images, final InfoLink infoLink) {
        if (images.isEmpty()) {
            return;
        }

        EvseImageUrlType networkLogo = null;
        EvseImageUrlType operatorLogo = null;
        EvseImageUrlType ownerLogo = null;
        EvseImageUrlType otherLogo = null;

        for (EvseImageUrlType evseImageUrlType : images) {
            final ImageClass imageClass = ImageClass.fromValue(evseImageUrlType.getClazz());

            if (ImageClass.NETWORK_LOGO.equals(imageClass)) {
                networkLogo = evseImageUrlType;
            } else if (ImageClass.OPERATOR_LOGO.equals(imageClass)) {
                operatorLogo = evseImageUrlType;
            } else if (ImageClass.OWNER_LOGO.equals(imageClass)) {
                ownerLogo = evseImageUrlType;
            } else if (ImageClass.OTHER_LOGO.equals(imageClass)) {
                otherLogo = evseImageUrlType;
            }
        }

        if (networkLogo != null) {
            infoLink.setLinkToLogo(networkLogo.getUri());
            return;
        }

        if (operatorLogo != null) {
            infoLink.setLinkToLogo(operatorLogo.getUri());
            return;
        }

        if (ownerLogo != null) {
            infoLink.setLinkToLogo(ownerLogo.getUri());
            return;
        }

        if (otherLogo != null) {
            infoLink.setLinkToLogo(otherLogo.getUri());
        }

    }
}
