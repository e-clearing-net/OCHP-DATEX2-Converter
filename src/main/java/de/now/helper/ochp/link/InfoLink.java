package de.now.helper.ochp.link;

public class InfoLink {

    private String linkToGeneralInformation;
    private String linkToLogo;
    private String linkToWebForm;

    public String getLinkToGeneralInformation() {
        return linkToGeneralInformation;
    }

    public void setLinkToGeneralInformation(String linkToGeneralInformation) {
        this.linkToGeneralInformation = linkToGeneralInformation;
    }

    public String getLinkToLogo() {
        return linkToLogo;
    }

    public void setLinkToLogo(String linkToLogo) {
        this.linkToLogo = linkToLogo;
    }

    public String getLinkToWebForm() {
        return linkToWebForm;
    }

    public void setLinkToWebForm(String linkToWebForm) {
        this.linkToWebForm = linkToWebForm;
    }

    @Override
    public String toString() {
        return "InfoLink{" +
                "linkToGeneralInformation='" + linkToGeneralInformation + '\'' +
                ", linkToLogo='" + linkToLogo + '\'' +
                ", linkToWebForm='" + linkToWebForm + '\'' +
                '}';
    }
}
