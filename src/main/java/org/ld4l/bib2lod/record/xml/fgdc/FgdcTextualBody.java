/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;

/**
 * Represents TextualBody in a FGDC record.
 */
public class FgdcTextualBody extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcTextualBody(Element element) throws RecordException {
		super(element);
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
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcTitle [textValue=");
		builder.append(textValue);
		builder.append("]");
		return builder.toString();
	}
	
}
