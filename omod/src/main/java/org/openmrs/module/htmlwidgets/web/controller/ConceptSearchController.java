package org.openmrs.module.htmlwidgets.web.controller;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptWord;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.propertyeditor.ConceptClassEditor;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.util.OpenmrsConstants;
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

    	List<Locale> l = new Vector<Locale>();
    	//l.add(Context.getLocale());
    	int listIndex=0;
    	User user = Context.getAuthenticatedUser();
    	if(user.getUserProperties() != null){
    		if(user.getUserProperties().containsKey(OpenmrsConstants.USER_PROPERTY_PROFICIENT_LOCALES)){
    			List<Locale>userLocalList = new Vector<Locale>();
    			userLocalList = new Vector (Arrays.asList(user.getUserProperty(OpenmrsConstants.USER_PROPERTY_PROFICIENT_LOCALES).split(" , ")));
		    	while(listIndex<userLocalList.size()){
		    	l.addAll(listIndex, userLocalList);
		    	listIndex++;
		    	}
    		}
    	}
    	
    	List<ConceptWord> words = Context.getConceptService().getConceptWords(query, l, false, includeClasses, null, null, null, questionConcept, null, null);
    	for (Iterator<ConceptWord> i = words.iterator(); i.hasNext();) {
    		ConceptWord w = i.next();
    		String ds = w.getConcept().getDisplayString();
    		if (w.getConceptName().isPreferred() || w.getConceptName().getName().equalsIgnoreCase(ds)) {
    			out.print(w.getConceptName().getName());
    		}
    		else {
    			out.print( w.getConcept().getDisplayString() + " (" + w.getConceptName().getName() + ")");
    		}
    		out.print("|" + w.getConcept().getId() + (i.hasNext() ? "\n" : ""));
    	}
    }
}
