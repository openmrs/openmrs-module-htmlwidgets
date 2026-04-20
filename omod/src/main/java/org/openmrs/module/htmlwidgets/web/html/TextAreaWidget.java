package org.openmrs.module.htmlwidgets.web.html;

import java.io.IOException;
import java.io.Writer;

import org.openmrs.module.htmlwidgets.web.WidgetConfig;

/**
 * This represents a single text area widget.
 */
public class TextAreaWidget implements Widget {

	/** 
	 * @see Widget#render(WidgetConfig)
	 */
	@Override
	public void render(WidgetConfig config, Writer w) throws IOException {
		
		Object idPrefix = config.getId();
		config.setFixedAttribute("onkeyup", "disableButtons('" + idPrefix + "')");
		config.setDefaultAttribute("cols", "20");
		config.setDefaultAttribute("rows", "2");
		HtmlUtil.renderOpenTag(w, "textarea", config.getAttributes());
		w.write(config.getDefaultValue() == null ? "" : config.getDefaultValue().toString());
		HtmlUtil.renderCloseTag(w, "textarea");
	}
}