package com.omahasteaks.data.enums;

public enum RecipeTab {
    STEAK_RECIPES("Steak Recipes"), MEAL_IDEAS("Meal Ideas"), BY_INGREDIENTS("By Ingredients"), BY_LIFESTYLE("By Lifestyle");
    private String value;

    private RecipeTab(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
