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

import org.apache.commons.lang.StringUtils;
import org.openmrs.annotation.Handler;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.CodedWidget;
import org.openmrs.module.htmlwidgets.web.html.Option;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FieldGenHandler for Enumerated Types
 */
@Handler(supports={Enum.class}, order=50)
public class EnumHandler extends CodedHandler {

	@Autowired
	MessageSourceService messageSourceService;

	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		Class<?> c = config.getType();
		Object[] enums = c.getEnumConstants();
		if (enums != null) {
			String optionValues = config.getAttributeValue("optionValues");
			if (StringUtils.isNotEmpty(optionValues)) {
				for (String optionValue : optionValues.split(",")) {
					for (Object enumValue : enums) {
						String s = enumValue.toString();
						if (s.equalsIgnoreCase(optionValue.trim())) {
							addOption(enumValue, config, widget);

						}
					}
				}
			}
			else {
				for (Object o : enums) {
					addOption(o, config, widget);
				}
			}
		}
	}

	protected void addOption(Object o, WidgetConfig config, CodedWidget widget) {
		String code = o.toString();
		String label = code;
		String messageCodePrefix = config.getAttributeValue("optionLabelCodePrefix");
		if (StringUtils.isNotEmpty(messageCodePrefix)) {
			label = messageSourceService.getMessage(messageCodePrefix + code);
		}
		widget.addOption(new Option(code, label, null, o), config);

	}

	/** 
	 * @see WidgetHandler#parse(String, Class<?>)
	 */
	@Override
	public Object parse(String input, Class<?> type) {
		if (StringUtils.isNotBlank(input)) {
			Object[] enums = type.getEnumConstants();
			if (enums != null) {
				for (Object o : enums) {
					if (input.equals(o.toString())) {
						return o;
					}
				}
			}
		}
		return null;
	}
}
