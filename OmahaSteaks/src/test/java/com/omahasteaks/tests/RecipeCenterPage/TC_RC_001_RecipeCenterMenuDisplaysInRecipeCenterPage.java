package com.omahasteaks.tests.RecipeCenterPage;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.enums.RecipeTab;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.RecipeCenterPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_RC_001_RecipeCenterMenuDisplaysInRecipeCenterPage extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    HomePage homePage;

    @Inject
    RecipeCenterPage recipeCenterPage;

    @Test
    public void TC_RC_001_Recipe_Center_Menu_Displays_In_Recipe_Center_Page() {
	initTestCaseData();

	goToRecipeCenterPage();

	verifyRecipeCenterPageDisplays();

	verifyRecipeMenuDisplaysInRecipeCenterPage(RecipeTab.STEAK_RECIPES);

	verifyRecipeMenuDisplaysInRecipeCenterPage(RecipeTab.MEAL_IDEAS);

	verifyRecipeMenuDisplaysInRecipeCenterPage(RecipeTab.BY_INGREDIENTS);

	verifyRecipeMenuDisplaysInRecipeCenterPage(RecipeTab.BY_LIFESTYLE);

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyRecipeMenuDisplaysInRecipeCenterPage(RecipeTab recipeMenu) {
	Logger.verify("Verify " + recipeMenu + " is displayed in Recipe Center page");
	Common.modalDialog.closeSavorDialog();
	Common.waitForDOMChange();
	assertTrue(recipeCenterPage.isRecipeTabDisplayed(recipeMenu), recipeMenu + " does not display in Recipe Center page");
    }

    public void verifyRecipeCenterPageDisplays() {
	Logger.verify("Verify \"Recipe Center\" page displays when clicking Recipe Center link in Cooking section");
	assertTrue(Common.getCurrentUrl().contains(Constants.URL_RECIPE_CENTER), "\"Recipe Center\" page does not display when clicking Recipe Center link in Cooking section");
    }

    public void goToRecipeCenterPage() {
	Logger.info("In footer, click Recipe Center link in Cooking section");
	homePage.clickLinkInFooterBySection(Constants.COOKING, LinksFooter.RECIPE_CENTER);
    }

    public void initTestCaseData() {
	Logger.tc("TC_RC_001 Recipe Center menu displays in Recipe Center page");
	Logger.to("TO_RC_01 Recipe Center page displays when clicking Recipe Center link in Cooking section");
	Logger.to("TO_RC_02 Steak Rcipes, Meal Ideas, By Ingredients, By Lifestyle are displayed in Recipe Center page");
    }
}
