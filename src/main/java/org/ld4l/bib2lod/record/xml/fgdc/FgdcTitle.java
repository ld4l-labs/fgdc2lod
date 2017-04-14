/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;

/**
 * Represents the title in a FGDC record.
 */
public class FgdcTitle extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcTitle(Element element) {
		super(element);
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
        if (textValue == null) {
            return false;
        }
        if (textValue.isEmpty()) {
            return false;
        }
        return true;
    }

}
