package org.openmrs.module.htmlwidgets.web.html;

import java.io.IOException;
import java.io.Writer;

import org.openmrs.module.htmlwidgets.web.WidgetConfig;

/**
 * This represents a single widget on a form.
 */
public interface Widget {
	
    /**
     * Writes the generated HTML for this widget
     * @param config the WidgetConfig
     * @param w the writer
     */
    public void render(WidgetConfig config, Writer w) throws IOException;
}