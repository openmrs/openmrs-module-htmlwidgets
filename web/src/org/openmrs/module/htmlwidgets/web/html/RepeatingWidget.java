package org.openmrs.module.htmlwidgets.web.html;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.htmlwidgets.util.ReflectionUtil;
import org.openmrs.module.htmlwidgets.web.WidgetConfig;
import org.openmrs.module.htmlwidgets.web.handler.WidgetHandler;
import org.openmrs.util.HandlerUtil;

public class RepeatingWidget implements Widget {
	
	/** 
	 * @see Widget#render(WidgetConfig)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void render(WidgetConfig config, Writer w) throws IOException {
		
		HtmlUtil.renderResource(w, config.getRequest(), "/moduleResources/htmlwidgets/htmlwidgets.js");
		String id = config.getId();
		String name = config.getName();
		
		Class<?> type = null;
		Type[] genericTypes = null;
		if (config.getGenericTypes() != null && config.getGenericTypes().length == 1) {
			try {
				Type firstGenericType = config.getGenericTypes()[0];
				if (firstGenericType instanceof Class) {
					type = (Class<?>) firstGenericType;
				}
				else if (firstGenericType instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) firstGenericType;
					Type rawType = pt.getRawType();
					type = (Class<?>) rawType;
					genericTypes = pt.getActualTypeArguments();
				}
			}
			catch (Exception e) {
				// Do Nothing 
			}
		}
		if (type == null) {
			throw new IllegalArgumentException("Invalid CollectionHandler configuration: " + config);
		}
		
		// Ensure that we have an appropriate Handler
		WidgetHandler handler = HandlerUtil.getPreferredHandler(WidgetHandler.class, type);
		if (handler == null) {
			throw new RuntimeException("No Preferred Handler found for: " + type);
		}
		
		List<Object> valuesToRender = new ArrayList<Object>();
		if (config.getDefaultValue() != null && StringUtils.isNotEmpty(config.getDefaultValue().toString())) {
			if (config.getDefaultValue() instanceof Collection) {
				valuesToRender.addAll((Collection) config.getDefaultValue());
			}
			else if (config.getDefaultValue() instanceof Object[]) {
				valuesToRender = Arrays.asList((Object[]) config.getDefaultValue());
			}
			else {
				throw new RuntimeException("Default value for a repeating widget must be an Object[], Collection, or null");
			}
		}
		
		boolean useSuffix = "t".equals(config.getAttributeValue("suffix"));
		String suffixSeparator = config.getAttributeValue("suffixSeparator", ".");
		
		w.write("<script type=\"text/javascript\" charset=\"utf-8\">");
		w.write("	jQuery(document).ready(function() {");
		w.write("		jQuery(\"#AddButton"+id+"\").click(function(event){");
		w.write("			var count = parseInt(jQuery('#"+id+"Count').html()) + 1;");
		w.write("			jQuery('#"+id+"Count').html(count);");
		w.write("			var $newRow = cloneAndInsertBefore('template_"+id+"', this);");
		w.write("			$newRow.attr('id', '"+id+"' + count);");
		w.write("			$newRow.prepend('<br/>');");
		w.write("			$newRow.children('#removeRowButton').show();");
		w.write("			$newRow.find(\"[name|='template_"+name+"']\").attr('name', \""+name+"\");");
		w.write("			var newRowChildren = $newRow.children();");
		w.write("			for (var i=0; i<newRowChildren.length; i++) {");
		w.write("				newRowChildren[i].id = newRowChildren[i].id + count;");
		w.write("			}");
		w.write(		"});");
		w.write("	});");
		w.write("</script>");
		
		HtmlUtil.renderOpenTag(w, "span", "id="+id+"MultiFieldDiv");

		if (valuesToRender.isEmpty()) {
			valuesToRender.add("");
		}
		
		for (int i=0; i<=valuesToRender.size(); i++) {
			Object o = (i == valuesToRender.size() ? null : valuesToRender.get(i));
			WidgetConfig c = config.clone();
			if (config.getId() != null) {
				c.setId(config.getId() + "_" + i);
			}
			if (o == null) {
				c.setName("template_" + name);
			}
			c.setType(type);
			c.setGenericTypes(genericTypes);
			c.setDefaultValue(o);
			
			if (useSuffix && i != valuesToRender.size()) {
				String suffixProperty = config.getAttributeValue("suffixProperty", null);
				String pf = Integer.toString(i);
				if (o != null && suffixProperty != null) {
					Object suffixObj = ReflectionUtil.getPropertyValue(o, suffixProperty);
					if (suffixObj != null) {
						pf = suffixObj.toString();
					}
				}
				c.setName(c.getName() + suffixSeparator + pf);
			}
			
			Set<Attribute> atts = new HashSet<Attribute>();
			atts.add(new Attribute("class", "multiFieldInput", null, null));
			if (o == null) {
				atts.add(new Attribute("id", "template_"+id, null, null));
				atts.add(new Attribute("style", "display:none;", null, null));
			}
			c.setFixedAttribute("name", c.getName());
			
			String removeStyle = (i==0 ? "style=display:none;|" : "");
			
			HtmlUtil.renderOpenTag(w, "span", atts);
			if (i > 0 && i < valuesToRender.size()) {
				HtmlUtil.renderSimpleTag(w, "br", "");
			}
			handler.render(c, w);
			HtmlUtil.renderSimpleTag(w, "input", "type=button|id=removeRowButton|value=X|size=1|" + removeStyle + "onclick=removeParentWithClass(this,'multiFieldInput');");
			HtmlUtil.renderCloseTag(w, "span");
		}
			
		HtmlUtil.renderSimpleTag(w, "input", "id=AddButton"+id+"|type=button|value=+|size=1");
		HtmlUtil.renderOpenTag(w, "span", "id="+id+"Count|style=display:none;");
		w.write("" + (valuesToRender.size() + 1));
		HtmlUtil.renderCloseTag(w, "span");
		HtmlUtil.renderCloseTag(w, "span");
	}
}