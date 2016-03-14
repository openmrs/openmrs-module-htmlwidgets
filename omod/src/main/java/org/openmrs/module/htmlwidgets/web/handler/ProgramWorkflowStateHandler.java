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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.html.Attribute;
import org.openmrs.module.htmlwidgets.web.html.CodedWidget;
import org.openmrs.module.htmlwidgets.web.html.Option;
import org.openmrs.module.htmlwidgets.web.html.OptionGroup;

/**
 * FieldGenHandler for Enumerated Types
 */
@Handler(supports={ProgramWorkflowState.class}, order=50)
public class ProgramWorkflowStateHandler extends OpenmrsMetadataHandler<ProgramWorkflowState> {
	
	/**
	 * @see OpenmrsMetadataHandler#getMetadataType()
	 */
	@Override
	public Class<ProgramWorkflowState> getMetadataType() {
		return ProgramWorkflowState.class;
	}
	
	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		
		boolean includeRetired = includeRetired(config);
		
		// Retrieve all Programs, or if limited by an attribute, only the configured Programs
		List<Program> programs = new ArrayList<Program>();
		Attribute program = config.getAttribute("Program");
		if (program != null) {
			for (String programName : program.getValue().split(",")) {
				Program programToAdd = Context.getProgramWorkflowService().getProgramByName(programName);
				if (programToAdd != null) {
					programs.add(programToAdd);
				}
				else {
					log.warn("Unable to find Program with name '" + programName + "'");
				}
			}
		}
		if (programs.isEmpty()) {
			programs = Context.getProgramWorkflowService().getAllPrograms(includeRetired);
		}
		
		// Retrieve all workflows, or if limited by an attribute, only the configured Workflows
		List<ProgramWorkflow> workflows = new ArrayList<ProgramWorkflow>();
		Attribute workflow = config.getAttribute("Workflow");
		for (Program p : programs) {
			if(workflow != null) {	
				for(String workflowName : workflow.getValue().split(",")) {
					ProgramWorkflow pw = p.getWorkflowByName(workflowName);
					if (pw != null) {
						workflows.add(pw);
					}
					else {
						log.warn("Unable to find ProgramWorkflow with name '" + workflowName + "'");
					}
				}
			}
			else {
				workflows.addAll(p.getAllWorkflows());
			}
		}
		
		// Sort the ProgramWorkflows alphabetically
		Collections.sort(workflows, new ProgramWorkflowComparator());

		// Return the appropriate states
		for (ProgramWorkflow w : workflows) {
			if (!w.isRetired() || includeRetired) {
				String pn = (w.getProgram().getName());
				String wn = (w.getName() == null ? w.getConcept().getDisplayString() : w.getName());
				List<ProgramWorkflowState> states = new ArrayList<ProgramWorkflowState>(w.getStates(includeRetired));
				Collections.sort(states, new ProgramWorkflowStateComparator());
				for (ProgramWorkflowState s : states) {
					OptionGroup group = new OptionGroup(pn + "-" + wn, null);
					String sn = (s.getName() == null ? s.getConcept().getDisplayString() : s.getName());
					widget.addOption(new Option(s.getId().toString(), sn, null, s, group), config);
				}
			}
		}
	}
	
	/** 
	 * @see WidgetHandler#parse(String, Class<?>)
	 */
	@Override
	@SuppressWarnings("deprecation")
	public Object parse(String input, Class<?> type) {
		if (StringUtils.isNotBlank(input)) {
			Integer id = Integer.parseInt(input);
			for (ProgramWorkflowState s : getStates()) {
				if (s.getProgramWorkflowStateId().equals(id)) {
					return s;
				}
			}
		}
		return null;
	}
	
	private List<ProgramWorkflowState> getStates() {
		List<ProgramWorkflowState> ret = new ArrayList<ProgramWorkflowState>();
		for (Program p : Context.getProgramWorkflowService().getAllPrograms()) {
			for (ProgramWorkflow w : p.getAllWorkflows()) {
				for (ProgramWorkflowState s : w.getStates()) {
					ret.add(s);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Comparator that sorts ProgramWorkflows by name, alphabetically
	 */
	private class ProgramWorkflowComparator implements Comparator<ProgramWorkflow> {
		public int compare(ProgramWorkflow p1, ProgramWorkflow p2) {
			if (p1 != null && p2 != null) {
				String sn1 = (p1.getName() == null ? p1.getConcept().getDisplayString() : p1.getName()).toUpperCase();
				String sn2 = (p2.getName() == null ? p2.getConcept().getDisplayString() : p2.getName()).toUpperCase();
				return sn1.compareTo(sn2);
			}
			else if (p1 == null && p2 != null) {
				return -1;
			}
			else if (p1 != null && p2 == null) {
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * Comparator that sorts ProgramWorkflowStates by name, alphabetically
	 */
	private class ProgramWorkflowStateComparator implements Comparator<ProgramWorkflowState> {
		public int compare(ProgramWorkflowState p1, ProgramWorkflowState p2) {
			if (p1 != null && p2 != null) {
				String sn1 = (p1.getName() == null ? p1.getConcept().getDisplayString() : p1.getName()).toUpperCase();
				String sn2 = (p2.getName() == null ? p2.getConcept().getDisplayString() : p2.getName()).toUpperCase();
				return sn1.compareTo(sn2);
			}
			else if (p1 == null && p2 != null) {
				return -1;
			}
			else if (p1 != null && p2 == null) {
				return 1;
			}
			return 0;
		}
	}
}
