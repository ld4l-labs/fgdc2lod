/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;

/**
 * Represents the local HGL identifier in a FGDC record.
 */
public class FgdcHglLayerId extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcHglLayerId(Element element) throws RecordException {
		super(element);
		// override above since we really just want to get text from attribute
		if (element.getAttribute("layerid") != null) {
			super.textValue = element.getAttribute("layerid");
		}
		isValid();
	}

	private void isValid() throws RecordFieldException {
        if (textValue == null) {
            throw new RecordFieldException("text value is null");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("text value is empty");
        }
    }
	
	@Override
	public String toString() {
		return getTextValue();
	}

}
