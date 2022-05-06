package com.omahasteaks.page.recipe.center;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.enums.RecipeTab;
import com.omahasteaks.page.RecipeCenterPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;

public class RecipeCenterPage_Desktop extends GeneralPage_Desktop implements RecipeCenterPage {

    // ================================================================================
    // Locators
    // ================================================================================

    // ================================================================================
    // Methods
    // ================================================================================
    public boolean isRecipeTabDisplayed(RecipeTab recipeTab) {
	Element eleRecipeMenuTab = new Element(interfaces.get("eleRecipeMenuTab"), recipeTab.getValue());
	return eleRecipeMenuTab.isDisplayed();
    }
}
