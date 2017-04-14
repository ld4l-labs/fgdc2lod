/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.record.xml.BaseXmlElement;
import org.w3c.dom.Element;

/**
 * Represents a field in a FGDC input record.
 */
public abstract class FgdcField extends BaseXmlElement {

	/**
	 * Constructor
	 * 
	 * @param element
	 */
	public FgdcField(Element element) {
		super(element);
	}

}
