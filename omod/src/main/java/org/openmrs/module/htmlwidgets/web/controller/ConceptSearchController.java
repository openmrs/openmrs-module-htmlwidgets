package org.openmrs.module.htmlwidgets.web.controller;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptSearchResult;
import org.openmrs.api.context.Context;
import org.openmrs.propertyeditor.ConceptClassEditor;
import org.openmrs.propertyeditor.ConceptEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConceptSearchController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(ConceptClass.class, new ConceptClassEditor());
		binder.registerCustomEditor(Concept.class, new ConceptEditor());
	}
	
    /**
     * Concept Search
     */
    @RequestMapping("/module/htmlwidgets/conceptSearch")
    public void conceptSearch(ModelMap model, HttpServletRequest request, HttpServletResponse response, 
                              @RequestParam(required=true, value="q") String query,
                              @RequestParam(required=false, value="includeClasses") List<ConceptClass> includeClasses,
                              @RequestParam(required=false, value="questionConceptId") Concept questionConcept) throws Exception {
    
    	response.setContentType("text/plain");
    	response.setCharacterEncoding("UTF-8");
    	PrintWriter out = response.getWriter();

    	List<Locale> l = Context.getAdministrationService().getSearchLocales();

    	List<ConceptSearchResult> results = Context.getConceptService().getConcepts(query, l, false, includeClasses, null, null, null, questionConcept, null, null);
    	for (Iterator<ConceptSearchResult> i = results.iterator(); i.hasNext();) {
    		ConceptSearchResult res = i.next();
    		String ds = res.getConcept().getDisplayString();
    		if (res.getConceptName().isPreferred() || res.getConceptName().getName().equalsIgnoreCase(ds)) {
    			out.print(res.getConceptName().getName());
    		}
    		else {
    			out.print( res.getConcept().getDisplayString() + " (" + res.getConceptName().getName() + ")");
    		}
    		out.print("|" + res.getConcept().getId() + (i.hasNext() ? "\n" : ""));
    	}
    }
}
