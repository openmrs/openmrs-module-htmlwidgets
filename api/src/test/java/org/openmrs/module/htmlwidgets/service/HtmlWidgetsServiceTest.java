package org.openmrs.module.htmlwidgets.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.test.jupiter.BaseModuleContextSensitiveTest;

public class HtmlWidgetsServiceTest extends BaseModuleContextSensitiveTest {
	
	/**
	 * @see HtmlWidgetsService#getAllMetadataByType(Class,boolean)
	 * @verifies return only unretired
	 */
	@Test
	public void getAllMetadataByType_shouldReturnOnlyUnretired() throws Exception {
		List<Location> locations = Context.getService(HtmlWidgetsService.class).getAllMetadataByType(Location.class, false);
		Assertions.assertNotNull(locations);
		Assertions.assertTrue(locations.size() != 0, "not empty");
		for (Location location : locations) {
	        Assertions.assertFalse(location.getRetired(), location.getName() + " is retired");
        }
	}

	/**
	 * @see HtmlWidgetsService#getUserNamesById(String,List)
	 * @verifies return only users with the given roles
	 */
	@Test
	public void getUserNamesById_shouldReturnOnlyUsersWithTheGivenRoles() throws Exception {
		Map<Integer, String> names = Context.getService(HtmlWidgetsService.class).getUserNamesById(null, List.of("Provider"));
		Assertions.assertEquals(Set.of(501, 502), names.keySet());
	}
}