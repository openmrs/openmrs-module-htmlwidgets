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
package org.openmrs.module.htmlwidgets.web.controller;

import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientSearchController {
	
	/**
	 * Patient Search
	 */
	@RequestMapping("/module/htmlwidgets/patientSearch")
	public void patienSearch(ModelMap model, HttpServletRequest request, HttpServletResponse response,
	                         @RequestParam(required = true, value = "q") String query)
	    throws Exception {
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		for (Iterator<Patient> i = Context.getPatientService().getPatients(query).iterator(); i.hasNext();) {
			Patient p = i.next();
			out.print(p.getFamilyName() + ", " + p.getGivenName() + "|" + p.getPatientId() + (i.hasNext() ? "\n" : ""));
		}
	}
}