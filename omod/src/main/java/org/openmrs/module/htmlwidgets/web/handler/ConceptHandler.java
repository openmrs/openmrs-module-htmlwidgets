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

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptClass;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.CodedWidget;
import org.openmrs.module.htmlwidgets.web.html.Option;

/**
 * Widget Handler for Concepts
 */
@Handler(supports={Concept.class}, order=50)
public class ConceptHandler extends CodedHandler {
	
	/**
	 * @see CodedHandler#setDefaults(WidgetConfig)
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected void setDefaults(WidgetConfig config) {
		if (StringUtils.isEmpty(config.getFormat())) {
			config.setFormat("ajax");
		}
		
		StringBuilder url = new StringBuilder("/module/htmlwidgets/conceptSearch.form");
		
		// Logic to limit the returned Concepts, based on passed options
		String[] extraOptions = {"includeClasses", "questionConceptId"};
		String prefix = "?";
		
		for (String option : extraOptions) {
			String optionValue = config.getAttributeValue(option);
			if (StringUtils.isNotBlank(optionValue)) {
				for (String optVal : optionValue.split(",")) {
					optVal = URLEncoder.encode(optVal);
					url.append(prefix + option + "=" + optVal);
					prefix = "&";
				}
			}
		}
		config.setDefaultAttribute("ajaxUrl", url.toString());

		Object defaultValue = config.getDefaultValue();
		if (defaultValue != null && StringUtils.isNotEmpty(defaultValue.toString()) && !(defaultValue instanceof Concept)) {
			// If the defaultValue passed in is not null and not a Concept, try converting it to one
			Concept convertedDefaultValue = null;
			try {
				// First by id
				convertedDefaultValue = Context.getConceptService().getConcept(Integer.parseInt(defaultValue.toString()));
			}
			catch (Exception e) {
				// And if that fails, then by uuid
				convertedDefaultValue = Context.getConceptService().getConceptByUuid(defaultValue.toString());
			}
			if (convertedDefaultValue != null) {
				defaultValue = convertedDefaultValue;
			}
			else {
				throw new IllegalArgumentException("Default value of " + defaultValue + " is not able to be converted to a Concept");
			}
		}
	}

	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		if ("ajax".equals(config.getFormat())) {
			if (config.getDefaultValue() != null && StringUtils.isNotEmpty(config.getDefaultValue().toString())) {
				Concept c = (Concept) config.getDefaultValue();
				widget.addOption(new Option(c.getId().toString(), c.getDisplayString(), null, c), config);
			}
		}
		else {
			String includeClasses = config.getAttributeValue("includeClasses");
			if (StringUtils.isNotBlank(includeClasses)) {
				for (String ccId : includeClasses.split(",")) {
					ConceptClass cc = Context.getConceptService().getConceptClass(Integer.parseInt(ccId));
					for (Concept c : Context.getConceptService().getConceptsByClass(cc)) {
						widget.addOption(new Option(c.getId().toString(), c.getDisplayString(), null, c), config);
					}
				}
			}
			String questionConceptId = config.getAttributeValue("questionConceptId");
			if (StringUtils.isNotBlank(questionConceptId)) {
				Concept c = Context.getConceptService().getConcept(Integer.parseInt(questionConceptId));
				for (ConceptAnswer answer : c.getAnswers()) {
					Concept a = answer.getAnswerConcept();
					widget.addOption(new Option(a.getId().toString(), a.getDisplayString(), null, a), config);
				}
			}
		}
		widget.sortOptions();
	}
	
	/** 
	 * @see WidgetHandler#parse(String, Class<?>)
	 */
	@Override
	public Object parse(String input, Class<?> type) {
		if (StringUtils.isNotBlank(input)) {
			return Context.getConceptService().getConcept(Integer.parseInt(input));
		}
		return null;
	}
}
