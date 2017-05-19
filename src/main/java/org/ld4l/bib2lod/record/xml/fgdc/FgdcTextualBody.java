/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;

/**
 * Represents TextualBody in a FGDC record.
 */
public class FgdcTextualBody extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcTextualBody(Element element) {
		super(element);
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
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcTitle [textValue=");
		builder.append(textValue);
		builder.append("]");
		return builder.toString();
	}
	
}
