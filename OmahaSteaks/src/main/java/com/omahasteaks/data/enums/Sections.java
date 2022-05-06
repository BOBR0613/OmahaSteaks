package com.omahasteaks.data.enums;

public enum Sections {
    HEADER("header", "eleHeader"), DEPARTMENT_MENU("departmentMenu", "eleDepartmentMenu"), CENTER_STAGE("centerStage", "eleCenterStage"), PROGRESSIVE_OFFERS("progressiveOffrers", "eleProgressiveOffers"), FOOTER("footer", "eleFooter"), ALL_SECTIONS("allSections", "eleAllSections");

    String sectionName;
    String eleSection;

    public String sectionName() {
	return sectionName;
    }

    public String eleSection() {
	return eleSection;
    }

    Sections(String sectionName, String eleSection) {
	this.sectionName = sectionName;
	this.eleSection = eleSection;
    }

}
