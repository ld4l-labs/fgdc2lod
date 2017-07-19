/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.xml.BaseXmlElement;
import org.w3c.dom.Element;

/**
 * Represents a field in a FGDC input record.
 */
public abstract class BaseFgdcField extends BaseXmlElement {

	/**
	 * Constructor
	 * 
	 * @param element
	 */
	public BaseFgdcField(Element element) {
		super(element);
	}

}
