package com.omahasteaks.utils.config;

import org.testng.IModuleFactory;
import org.testng.ITestContext;
import org.testng.xml.XmlTest;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.omahasteaks.page.billing.address.BillingAddressPage_Desktop;
import com.omahasteaks.page.BillingAddressPage;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.CustomPackagePage;
import com.omahasteaks.page.DepartmentsPage;
import com.omahasteaks.page.FreeShippingPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.LastMinuteGiftPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.PromotionModal;
import com.omahasteaks.page.RecipeCenterPage;
import com.omahasteaks.page.RewardGeneralPage;
import com.omahasteaks.page.RewardOrderReviewPage;
import com.omahasteaks.page.RewardShippingInfoPage;
import com.omahasteaks.page.RewardShippingOptionPage;
import com.omahasteaks.page.RewardShoppingCart;
import com.omahasteaks.page.SalePage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.page.SitemapPage;
import com.omahasteaks.page.StoresPage;
import com.omahasteaks.page.UltimatePackagesPage;
import com.omahasteaks.page.WinFreeSteaksPage; 
import com.omahasteaks.page.category.CategoryPage_Desktop;
import com.omahasteaks.page.category.CategoryPage_Mobile; 
import com.omahasteaks.page.collection.center.CollectionCenterPage_Desktop;
import com.omahasteaks.page.collection.center.CollectionCenterPage_Mobile;
import com.omahasteaks.page.confirmation.ConfirmationPage2SC_Desktop;
import com.omahasteaks.page.confirmation.ConfirmationPage2SC_Mobile;
import com.omahasteaks.page.custom.pkg.CustomPackagePage_Desktop;
import com.omahasteaks.page.custom.pkg.CustomPackagePage_Mobile;
import com.omahasteaks.page.departments.DepartmentsPage_Desktop;
import com.omahasteaks.page.departments.DepartmentsPage_Mobile;
import com.omahasteaks.page.free.shipping.FreeShippingPage_Desktop;
import com.omahasteaks.page.free.shipping.FreeShippingPage_Mobile;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.page.general.GeneralPage_Mobile;
import com.omahasteaks.page.home.HomePage_Desktop;
import com.omahasteaks.page.home.HomePage_Mobile;
import com.omahasteaks.page.last.minute.gift.LastMinuteGiftPage_Desktop;
import com.omahasteaks.page.last.minute.gift.LastMinuteGiftPage_Mobile;
import com.omahasteaks.page.my.account.MyAccountPage_Desktop;
import com.omahasteaks.page.my.account.MyAccountPage_Mobile;
import com.omahasteaks.page.payment.and.review.PaymentAndReviewPage2SC_Desktop;
import com.omahasteaks.page.payment.and.review.PaymentAndReviewPage2SC_Mobile;
import com.omahasteaks.page.product.ProductPage_Desktop;
import com.omahasteaks.page.product.ProductPage_Mobile;
import com.omahasteaks.page.promotion.modal.PromotionModal_Desktop;
import com.omahasteaks.page.promotion.modal.PromotionModal_Mobile;
import com.omahasteaks.page.recipe.center.RecipeCenterPage_Desktop;
import com.omahasteaks.page.recipe.center.RecipeCenterPage_Mobile;
import com.omahasteaks.page.rewards.collection.general.RewardGeneralPage_Desktop;
import com.omahasteaks.page.rewards.collection.general.RewardGeneralPage_Mobile;
import com.omahasteaks.page.rewards.collection.order.review.RewardOrderReviewPage_Desktop;
import com.omahasteaks.page.rewards.collection.order.review.RewardOrderReviewPage_Mobile;
import com.omahasteaks.page.rewards.collection.shipping.information.RewardShippingInfoPage_Desktop;
import com.omahasteaks.page.rewards.collection.shipping.information.RewardShippingInfoPage_Mobile;
import com.omahasteaks.page.rewards.collection.shipping.option.RewardShippingOptionPage_Desktop;
import com.omahasteaks.page.rewards.collection.shipping.option.RewardShippingOptionPage_Mobile;
import com.omahasteaks.page.rewards.collection.shopping.cart.RewardShoppingCart_Desktop;
import com.omahasteaks.page.rewards.collection.shopping.cart.RewardShoppingCart_Mobile;
import com.omahasteaks.page.sale.SalePage_Desktop;
import com.omahasteaks.page.sale.SalePage_Mobile;
import com.omahasteaks.page.search.result.SearchResultPage_Desktop;
import com.omahasteaks.page.search.result.SearchResultPage_Mobile;
import com.omahasteaks.page.shipping.address.ShippingAddressPage2SC_Desktop;
import com.omahasteaks.page.shipping.address.ShippingAddressPage2SC_Mobile;
import com.omahasteaks.page.shopping.cart.ShoppingCartPage_Desktop;
import com.omahasteaks.page.shopping.cart.ShoppingCartPage_Mobile;
import com.omahasteaks.page.signin.SignInPage_Desktop;
import com.omahasteaks.page.signin.SignInPage_Mobile;
import com.omahasteaks.page.sitemap.SitemapPage_Desktop;
import com.omahasteaks.page.sitemap.SitemapPage_Mobile;
import com.omahasteaks.page.stores.StoresPage_Desktop;
import com.omahasteaks.page.stores.StoresPage_Mobile;
import com.omahasteaks.page.ultimate.pkg.UltimatePackagesPage_Desktop;
import com.omahasteaks.page.ultimate.pkg.UltimatePackagesPage_Mobile;
import com.omahasteaks.page.win.free.steaks.WinFreeSteaksPage_Desktop;
import com.omahasteaks.page.win.free.steaks.WinFreeSteaksPage_Mobile;
import com.omahasteaks.utils.common.Constants;

public class GuiceInjection implements IModuleFactory {
    @Override
    public Module createModule(ITestContext context, Class<?> testClass) {
	XmlTest xml = context.getCurrentXmlTest();
	String mode = xml.getParameter("mode");
	Constants.SITE = context.getCurrentXmlTest().getParameter("site");
	Constants.SITE = Constants.SITE==null?"omk":Constants.SITE;
	return new InjectionModule(mode);
    }
}

@SuppressWarnings("rawtypes")
class InjectionModule extends AbstractModule {
    enum Mode {
	DESKTOP, MOBILE
    }

    Mode mode;
    Class[] listPageInterface = {  RewardShoppingCart.class, RewardShippingOptionPage.class, RewardShippingInfoPage.class, RewardOrderReviewPage.class, RewardGeneralPage.class, SitemapPage.class, PromotionModal.class, MyAccountPage.class, ConfirmationPage2SC.class, PaymentAndReviewPage2SC.class, ShippingAddressPage2SC.class, GeneralPage.class, ShoppingCartPage.class, SignInPage.class, SearchResultPage.class, ProductPage.class, CustomPackagePage.class, CategoryPage.class, FreeShippingPage.class, UltimatePackagesPage.class, HomePage.class, DepartmentsPage.class, SalePage.class, LastMinuteGiftPage.class, WinFreeSteaksPage.class, StoresPage.class, RecipeCenterPage.class, CollectionCenterPage.class };
    Class[] listPageDesktop = { RewardShoppingCart_Desktop.class, RewardShippingOptionPage_Desktop.class, RewardShippingInfoPage_Desktop.class, RewardOrderReviewPage_Desktop.class, RewardGeneralPage_Desktop.class, SitemapPage_Desktop.class, PromotionModal_Desktop.class, MyAccountPage_Desktop.class, ConfirmationPage2SC_Desktop.class, PaymentAndReviewPage2SC_Desktop.class, ShippingAddressPage2SC_Desktop.class, GeneralPage_Desktop.class, ShoppingCartPage_Desktop.class, SignInPage_Desktop.class, SearchResultPage_Desktop.class, ProductPage_Desktop.class, CustomPackagePage_Desktop.class, CategoryPage_Desktop.class, FreeShippingPage_Desktop.class, UltimatePackagesPage_Desktop.class, HomePage_Desktop.class, DepartmentsPage_Desktop.class, SalePage_Desktop.class, LastMinuteGiftPage_Desktop.class, WinFreeSteaksPage_Desktop.class, StoresPage_Desktop.class, RecipeCenterPage_Desktop.class, CollectionCenterPage_Desktop.class };
    Class[] listPageMobile = { RewardShoppingCart_Mobile.class, RewardShippingOptionPage_Mobile.class, RewardShippingInfoPage_Mobile.class, RewardOrderReviewPage_Mobile.class, RewardGeneralPage_Mobile.class, SitemapPage_Mobile.class, PromotionModal_Mobile.class, MyAccountPage_Mobile.class, ConfirmationPage2SC_Mobile.class, PaymentAndReviewPage2SC_Mobile.class, ShippingAddressPage2SC_Mobile.class, GeneralPage_Mobile.class, ShoppingCartPage_Mobile.class, SignInPage_Mobile.class, SearchResultPage_Mobile.class, ProductPage_Mobile.class, CustomPackagePage_Mobile.class, CategoryPage_Mobile.class, FreeShippingPage_Mobile.class, UltimatePackagesPage_Mobile.class, HomePage_Mobile.class, DepartmentsPage_Mobile.class, SalePage_Mobile.class, LastMinuteGiftPage_Mobile.class, WinFreeSteaksPage_Mobile.class, StoresPage_Mobile.class, RecipeCenterPage_Mobile.class, CollectionCenterPage_Mobile.class };

    InjectionModule(String mode) {
	if (mode == null || mode.trim().length() == 0)
	    mode = "DESKTOP";
	this.mode = Mode.valueOf(mode.toUpperCase());
    }

    @Override
    protected void configure() {
	switch (mode) {
	case MOBILE:
	    BindClasses(listPageInterface, listPageMobile);
	    break;
	default:
	    BindClasses(listPageInterface, listPageDesktop);
	    break;
	}
    }

    @SuppressWarnings("unchecked")
    private void BindClasses(Class[] lstInterface, Class[] lstImplement) {
	for (int i = 0; i < lstInterface.length; i++)
	    bind(lstInterface[i]).to(lstImplement[i]);
    }
}
