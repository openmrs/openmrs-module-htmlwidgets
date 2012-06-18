package org.openmrs.module.htmlwidgets.web.controller;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.module.htmlwidgets.util.ReflectionUtil;
import org.openmrs.module.htmlwidgets.web.WidgetUtil;
import org.openmrs.module.htmlwidgets.web.demo.Demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    /**
     * Provides demo functionality controller for the widget framework
     */
    @RequestMapping("/module/htmlwidgets/demonstration")
    public void demonstration(ModelMap model, HttpServletRequest request, 
    						  @RequestParam(required=false, value="property") String property,
    						  @RequestParam(required=false, value="format") String format,
    						  @RequestParam(required=false, value="attributes") String attributes) {

    	model.addAttribute("property", property);
    	model.addAttribute("format", format);
    	model.addAttribute("attributes", attributes);
    	
    	Demo demo = new Demo();
    	if (StringUtils.hasText(property)) {
	    	Object parsedObject = WidgetUtil.getFromRequest(request, property, demo, property);
	    	ReflectionUtil.setPropertyValue(demo, property, parsedObject);
	    	model.addAttribute("submittedValue", parsedObject);
	    	if (parsedObject != null) {
	    		model.addAttribute("submittedValueType", parsedObject.getClass().getName());
	    	}
    	}
    	model.addAttribute("demo", demo);
    	
    	// Put available properties in the model
    	
    	Map<String, String> availableProperties = new LinkedHashMap<String, String>();
    	for (Field f : Demo.class.getDeclaredFields()) {
    		String displayname = f.getName();
    		if (displayname.contains("Primitive")) {
    			displayname = displayname.replace("Primitive", "");
    		}
    		else if (displayname.contains("Obj")) {
    			displayname = StringUtils.capitalize(displayname.replace("Obj", ""));
    		}
    		availableProperties.put(f.getName(), displayname);
    	}
    	model.addAttribute("availableProperties", availableProperties);
    }
}
