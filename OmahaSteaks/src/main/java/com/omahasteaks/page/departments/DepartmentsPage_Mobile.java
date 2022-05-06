package com.omahasteaks.page.departments;

import com.logigear.control.common.imp.Element;

public class DepartmentsPage_Mobile extends DepartmentsPage_Desktop {

    @Override
    protected Element getLinkInHeader(String href) {
	return new Element(interfaces.get("eleLinkInHeader_mb"), href);
    }

    @Override
    protected Element getImgInHeaderBySrc(String src) {
	return new Element(interfaces.get("eleImgInHeaderBySrc_mb"), src);
    }

    @Override
    protected Element getImgInHeaderByDataOriginal(String dataOriginal) {
	return new Element(interfaces.get("eleImgInHeaderByDataOriginal_mb"), dataOriginal);
    }

    @Override
    protected Element getLinkFooter(String href) {
	return new Element(interfaces.get("eleLinkInFooter_mb"), href);
    }

    @Override
    protected Element getImgInFooterBySrc(String src) {
	return new Element(interfaces.get("eleImgInFooterBySrc_mb"), src);
    }

    @Override
    protected Element getImgInFooterByDataOriginal(String dataOriginal) {
	return new Element(interfaces.get("eleImgInFooterByDataOriginal_mb"), dataOriginal);
    }
}
