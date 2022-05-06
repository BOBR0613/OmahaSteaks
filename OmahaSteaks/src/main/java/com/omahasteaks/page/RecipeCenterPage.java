package com.omahasteaks.page;

import com.omahasteaks.data.enums.RecipeTab;

public interface RecipeCenterPage extends GeneralPage {
    /**
     * check Recipe tab display in recipe menu
     */
    boolean isRecipeTabDisplayed(RecipeTab recipeTab);
}
