package org.openmrs.module.htmlwidgets.web.html;

import java.io.IOException;
import java.io.Writer;

import org.openmrs.module.htmlwidgets.web.WidgetConfig;

public class TextWidget implements Widget {

	/** 
	 * @see Widget#render(WidgetConfig)
	 */
	public void render(WidgetConfig config, Writer w) throws IOException {
		String textValue = config.getDefaultValue() == null ? "" : config.getDefaultValue().toString();
		config.setFixedAttribute("type", "text");
		config.setDefaultAttribute("value", textValue);
		config.setDefaultAttribute("size", "40");
		HtmlUtil.renderSimpleTag(w, "input", config.getAttributes());
	}
}