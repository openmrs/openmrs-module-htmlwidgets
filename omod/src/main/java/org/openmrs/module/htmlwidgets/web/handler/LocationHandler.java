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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.openmrs.Location;
import org.openmrs.annotation.Handler;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.Widget;
import org.openmrs.web.taglib.FieldGenTag;

/**
 * FieldGenHandler for Enumerated Types
 */
@Handler(supports={Location.class}, order=50)
public class LocationHandler  extends OpenmrsMetadataHandler<Location> {
	
	/**
	 * @see OpenmrsMetadataHandler#getMetadataType()
	 */
	@Override
	public Class<Location> getMetadataType() {
		return Location.class;
	}
	
	/** 
	 * @see Widget#render(WidgetConfig)
	 */
	@Override
	public void render(WidgetConfig config, Writer w) throws IOException {

		w.write("<script language=\"javascript\" type=\"text/javascript\">$j = jQuery;</script>");
		FieldGenTag fieldGenTag = new FieldGenTag();
		fieldGenTag.setPageContext(config.getPageContext());
		fieldGenTag.setType("org.openmrs.Location");
		fieldGenTag.setFormFieldName(config.getAttributeValue("name"));
		fieldGenTag.setUrl("location.field");
		fieldGenTag.setVal(config.getDefaultValue());
		Map<String, Object> parameterMap = fieldGenTag.getParameterMap();
		if (parameterMap == null) {
			parameterMap = new HashMap<String, Object>();
		}
		parameterMap.put("optionHeader", config.getAttributeValue("optionHeader", "[blank]"));
		fieldGenTag.setParameterMap(parameterMap);
		
		try{
			fieldGenTag.doStartTag();
		}
		catch (JspException ex) {
			ex.printStackTrace();
		}
	}
}
