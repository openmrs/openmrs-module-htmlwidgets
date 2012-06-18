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
import org.openmrs.Person;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.CodedWidget;
import org.openmrs.module.htmlwidgets.web.html.Option;

/**
 * WidgetHandler for Persons
 */
@Handler(supports={Person.class}, order=50)
public class PersonHandler extends CodedHandler {
	
	/**
	 * @see CodedHandler#setDefaults(WidgetConfig)
	 */
	@Override
	protected void setDefaults(WidgetConfig config) {
		if (StringUtils.isEmpty(config.getFormat())) {
			config.setFormat("ajax");
		}
		String roleParam = "";
		String roleCsv = config.getAttributeValue("roles");
		if (StringUtils.isNotEmpty(roleCsv)) {
			roleParam = "?roles="+roleCsv;
		}
		config.setDefaultAttribute("ajaxUrl", "/module/htmlwidgets/personSearch.form"+roleParam);
	}

	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		
		if (StringUtils.isNotEmpty(config.getFormat()) && !"ajax".equals(config.getFormat())) {
			
			String roleCsv = config.getAttributeValue("roles");
			List<Person> persons = null;
			if (StringUtils.isNotEmpty(roleCsv)) {
				persons = new ArrayList<Person>();
				for (String roleName : roleCsv.split(",")) {
					Role role = Context.getUserService().getRole(roleName);
					for (User u : Context.getUserService().getUsersByRole(role)) {
						persons.add(u.getPerson());
					}
				}
			}
			else {
				persons = Context.getPersonService().getPeople("", null);
			}

			for (Person p : persons) {
				widget.addOption(new Option(p.getId().toString(), getPersonDisplay(p, config), null, p), config);
			}
		}
		else if (config.getDefaultValue() != null && StringUtils.isNotEmpty(config.getDefaultValue().toString())) {
			Person p = (Person) config.getDefaultValue();
			widget.addOption(new Option(p.getId().toString(), getPersonDisplay(p, config), null, p), config);
		}
	}
	
	/** 
	 * @see WidgetHandler#parse(String, Class<?>)
	 */
	@Override
	public Object parse(String input, Class<?> type) {
		if (StringUtils.isNotBlank(input)) {
			return Context.getPersonService().getPerson(Integer.parseInt(input));
		}
		return null;
	}
	
	/**
	 * Returns how to display the person name given the configuration
	 * @param config
	 * @return
	 */
	protected String getPersonDisplay(Person p, WidgetConfig config) {
		return p.getFamilyName() + ", " + p.getGivenName();
	}
}
