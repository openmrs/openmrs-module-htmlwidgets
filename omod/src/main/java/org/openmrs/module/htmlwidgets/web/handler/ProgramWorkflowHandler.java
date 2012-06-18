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

import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.CodedWidget;
import org.openmrs.module.htmlwidgets.web.html.Option;
import org.openmrs.module.htmlwidgets.web.html.OptionGroup;

/**
 * FieldGenHandler for Enumerated Types
 */
@Handler(supports={ProgramWorkflow.class}, order=50)
public class ProgramWorkflowHandler extends OpenmrsMetadataHandler<ProgramWorkflow> {
	
	/**
	 * @see OpenmrsMetadataHandler#getMetadataType()
	 */
	@Override
	public Class<ProgramWorkflow> getMetadataType() {
		return ProgramWorkflow.class;
	}

	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		boolean includeRetired = includeRetired(config);
		for (Program p : Context.getProgramWorkflowService().getAllPrograms(includeRetired)) {
			for (ProgramWorkflow w : p.getAllWorkflows()) {
				if (!w.isRetired() || includeRetired) {
					OptionGroup group = new OptionGroup(p.getName(), null);
					String optionName = (w.getName() == null ? w.getConcept().getDisplayString() : w.getName());
					widget.addOption(new Option(w.getId().toString(), optionName, null, w, group), config);
				}
			}
		}
	}
}
