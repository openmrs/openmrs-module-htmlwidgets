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
package org.openmrs.module.htmlwidgets.web.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlwidgets.service.HtmlWidgetsService;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.CodedWidget;
import org.openmrs.module.htmlwidgets.web.html.Option;
import org.openmrs.module.htmlwidgets.web.html.OptionGroup;

/**
 * FieldGenHandler for Metadata Types
 */
public abstract class OpenmrsMetadataHandler<T extends OpenmrsMetadata> extends CodedHandler {
	
	public abstract Class<T> getMetadataType();
	
	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		List<? extends OpenmrsMetadata> l = Context.getService(HtmlWidgetsService.class).getAllMetadataByType(getMetadataType(), includeRetired(config));
		List<Option> retiredMetadata = new ArrayList<Option>();
		for (OpenmrsMetadata m : l) {
			Option o = new Option(m.getId().toString(), m.getName(), null, m);
			if (m.isRetired()) {
				retiredMetadata.add(o);
			}
			else {
				widget.addOption(o, config);
			}
		}
		if (!retiredMetadata.isEmpty()) {
			OptionGroup group = new OptionGroup("Retired", "general.retired");
			for (Option o : retiredMetadata) {
				o.setGroup(group);
				widget.addOption(o, config);
			}
		}
	}
	
	/** 
	 * @see WidgetHandler#parse(String, Class<?>)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object parse(String input, Class<?> type) {
		if (StringUtils.isNotBlank(input)) {
			return Context.getService(HtmlWidgetsService.class).getObject((Class<T>)type, Integer.parseInt(input));
		}
		return null;
	}
	
	/**
	 * @return whether or not the configuration indicates that retired values should be returned
	 */
	public boolean includeRetired(WidgetConfig config) {
		return config.getAttributeValue("includeRetired", "true").equals("true");
	}
}
