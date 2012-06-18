package org.openmrs.module.htmlwidgets.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Person;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonSearchController {
    
    /**
     * User Search
     */
    @RequestMapping("/module/htmlwidgets/personSearch")
    public void personSearch(ModelMap model, HttpServletRequest request, HttpServletResponse response, 
    				@RequestParam(required=false, value="roles") String roles,
		    		@RequestParam(required=true, value="q") String query) throws Exception {
    	
    	response.setContentType("text/plain");
    	response.setCharacterEncoding("UTF-8");
    	PrintWriter out = response.getWriter();
    	
		if (StringUtils.isNotEmpty(roles)) {
			List<Role> roleList = new ArrayList<Role>();
			for (String roleName : roles.split(",")) {
				roleList.add(Context.getUserService().getRole(roleName));
			}
			for (Iterator<User> i = Context.getUserService().getUsers(query, roleList, false).iterator(); i.hasNext();) {
				User u = i.next();
				out.print(u.getFamilyName() + ", " + u.getGivenName() + "|" + u.getPerson().getPersonId() + (i.hasNext() ? "\n" : ""));
			}
		}
		else {
			for (Iterator<Person> i = Context.getPersonService().getPeople(query, null).iterator(); i.hasNext();) {
				Person p = i.next();
				out.print(p.getFamilyName() + ", " + p.getGivenName() + "|" + p.getPersonId() + (i.hasNext() ? "\n" : ""));
			}			
		}
    }
}
