package org.openmrs.module.htmlwidgets.service;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class HtmlWidgetsServiceTest extends BaseModuleContextSensitiveTest {
	
	/**
	 * @see HtmlWidgetsService#getAllMetadataByType(Class,boolean)
	 * @verifies return only unretired
	 */
	@Test
	public void getAllMetadataByType_shouldReturnOnlyUnretired() throws Exception {
		List<Location> locations = Context.getService(HtmlWidgetsService.class).getAllMetadataByType(Location.class, false);
		Assert.assertNotNull(locations);
		Assert.assertTrue("not empty", locations.size() != 0);
		for (Location location : locations) {
	        Assert.assertFalse(location.getName() + " is retired", location.getRetired());
        }
	}
}