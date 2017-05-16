/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;

/**
 * Represents the local HGL identifier in a FGDC record.
 */
public class FgdcHglLayerId extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcHglLayerId(Element element) {
		super(element);
		// override above since we really just want to get text from attribute
		if (element.getAttribute("layerid") != null) {
			super.textValue = element.getAttribute("layerid");
		}
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
        if (getTextValue() == null || getTextValue().isEmpty()) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString() {
		return getTextValue();
	}

}
