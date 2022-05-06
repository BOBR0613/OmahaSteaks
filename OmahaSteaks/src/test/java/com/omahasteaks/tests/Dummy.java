package com.omahasteaks.tests;

import org.testng.annotations.Test;

import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class Dummy extends TestBase_2SC {
	

	@Test
	public void DummyTestCase() {
	  //  DriverUtils.navigate("https://www.omastk.com/blog/omaha-cut-ribeye/");
		Logger.info(Common.getResponseCodeOfLink("https://www.omastk.com/gifs/os/190610_oc_onestep_meals_327x450_meals for any occasion.jpg"));
	}
}
