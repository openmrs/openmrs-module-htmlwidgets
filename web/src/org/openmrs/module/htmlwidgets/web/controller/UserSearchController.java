package org.openmrs.module.htmlwidgets.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlwidgets.service.HtmlWidgetsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserSearchController {
    
    /**
     * User Search
     */
    @RequestMapping("/module/htmlwidgets/userSearch")
    public void userSearch(ModelMap model, HttpServletRequest request, HttpServletResponse response, 
    				@RequestParam(required=false, value="roles") String roles,
		    		@RequestParam(required=true, value="q") String query) throws Exception {
    	
    	response.setContentType("text/plain");
    	response.setCharacterEncoding("UTF-8");
    	PrintWriter out = response.getWriter();
    	
		List<String> roleList = null;
		if (StringUtils.isNotEmpty(roles)) {
			roleList = new ArrayList<String>();
			for (String roleName : roles.split(",")) {
				roleList.add(roleName);
			}
			
		}
		StringBuilder ret = new StringBuilder();
		for (Map.Entry<Integer, String> entry : Context.getService(HtmlWidgetsService.class).getUserNamesById(query, roleList).entrySet()) {
			ret.append((ret.length() > 0 ? "\n" : "") + entry.getValue() + "|" + entry.getKey());
		}
		out.print(ret.toString());
    }
}
