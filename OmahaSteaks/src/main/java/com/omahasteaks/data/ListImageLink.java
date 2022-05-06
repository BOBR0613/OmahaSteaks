package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.omahasteaks.data.enums.Sections;
import com.omahasteaks.data.objects.ImageLink;

public class ListImageLink  extends Database {

	private List<ImageLink> lstImgLink = new ArrayList<ImageLink>();
	private List<ImageLink> lstImgLinkInHeader;
	private List<ImageLink> lstImgLinkInFooter;
	private static String jsonFile = "src/test/resources/data/ImageLink.json";

	public ListImageLink() {
		
		getListHrefAndSrcOfImage();
	}

	@SuppressWarnings("serial")
	public void getListHrefAndSrcOfImage() {

		JsonObject imageLink = getData(new TypeToken<JsonObject>() {
		}.getType(), jsonFile);

		lstImgLinkInHeader = getListFromJsonObj(imageLink.get(Sections.HEADER.sectionName()),
				new TypeToken<List<ImageLink>>() {
				}.getType());

		lstImgLinkInFooter = getListFromJsonObj(imageLink.get(Sections.FOOTER.sectionName()),
				new TypeToken<List<ImageLink>>() {
				}.getType());
	}

	public String converObjectToJSON(String section) {

		String jsonString = "";
		Gson gson = new Gson();
		jsonString = "\"" + section + "\":" + gson.toJson(this.getListImageLink());
		return jsonString = StringEscapeUtils.unescapeJava(jsonString);
	}

	public List<ImageLink> getListImageLink() {
		return lstImgLink;
	}

	public void initEmpty() {
		lstImgLink.clear();
	}

	public void remove(ImageLink imageLink) {
		lstImgLink.remove(imageLink);
	}

	public void add(ImageLink imageLink) {
		lstImgLink.add(imageLink);
	}

	public void addAll(ListImageLink lstAdd) {
		lstImgLink.addAll(lstAdd.getListImageLink());
	}

	public boolean contains(ImageLink imageLink) {
		return lstImgLink.contains(imageLink);
	}

	public List<ImageLink> getImageLink(Sections section) {

		switch (section) {
		case HEADER:
			return lstImgLinkInHeader;

		case DEPARTMENT_MENU:
			return null;

		case CENTER_STAGE:
			return null;

		case PROGRESSIVE_OFFERS:
			return null;

		case FOOTER:
			return lstImgLinkInFooter;

		case ALL_SECTIONS:
			return null;

		default:
			return null;
		}
	}
}
