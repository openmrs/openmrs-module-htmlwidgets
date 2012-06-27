/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.htmlwidgets.service;

import java.util.List;
import java.util.Map;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * HtmlWidgetsService API
 */
public interface HtmlWidgetsService extends OpenmrsService {

	/**
	 * @return all OpenmrsMetadata of the passed type
	 * @should return only unretired
	 */
	@Transactional(readOnly = true)
	public <T extends OpenmrsMetadata> List<T> getAllMetadataByType(Class<T> type, boolean includeRetired);
	
	/**
	 * @return all OpenmrsObjects of the passed type
	 */
	@Transactional(readOnly = true)
	public <T extends OpenmrsObject> List<T> getAllObjectsByType(Class<T> type);
	
	/**
	 * @return the object with the passed id
	 */
	@Transactional(readOnly = true)
	public <T extends OpenmrsObject> T getObject(Class<T> type, Integer id);

	/**
	 * @return a Map of user ids to names, ordered by name
	 */
	@Transactional(readOnly = true)
	public Map<Integer, String> getUserNamesById(String query, List<String> roleNames);
	
	/**
	 * @return a Map of person ids to names, ordered by name
	 */
	@Transactional(readOnly = true)
	public Map<Integer, String> getPersonNamesById(String query, List<String> roleNames);
}
