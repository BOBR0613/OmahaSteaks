package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.reflect.TypeToken;
import com.omahasteaks.utils.common.Constants;

public class ListCategories extends Database {
    private List<Category> lstCategory;
    private String jsonFile = "src/test/resources/data/%s/category_data.json";

    public ListCategories() {	
	lstCategory = new ArrayList<Category>();
    }

    @SuppressWarnings("serial")
    public void getListCategories() {
	lstCategory = getListData(new TypeToken<List<Category>>() {
	}.getType(), String.format(jsonFile,Constants.SITE));
    }

    public Category getRandomCategory() {
	getListCategories();
	Random rd = new Random();
	Category cate = lstCategory.get(rd.nextInt(lstCategory.size()));
	return cate;
    }

    public Category getRandomCategoryByDeparment(String departmentName) {
	getListCategories();
	Random rd = new Random();
	List<Category> lstResult = new ArrayList<Category>();
	for (Category category : lstCategory) {
	    if (category.getCategoryPath().split("/")[0].equals(departmentName)) {
		lstResult.add(category);
	    }
	}
	Category cate = lstResult.get(rd.nextInt(lstResult.size()));
	return cate;
    }

    public void initEmpty() {
	lstCategory = new ArrayList<Category>();
    }
}
