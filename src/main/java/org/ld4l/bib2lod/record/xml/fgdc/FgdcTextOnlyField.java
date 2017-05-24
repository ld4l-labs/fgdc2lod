/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;

/**
 * Represents a generic text-only field in a FGDC record.
 */
public class FgdcTextOnlyField extends FgdcField {
	
	private String fieldName = "";

	public FgdcTextOnlyField(Element element) {
		super(element);
	}

	/**
	 * Constructor giving this generic field a specific name.
	 */
	public FgdcTextOnlyField(Element element, String fieldName) {
		super(element);
		if (fieldName != null && !fieldName.isEmpty()) {
			this.fieldName = fieldName;
		}
	}

	public String getFieldName() {
		return fieldName;
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
		if (fieldName.isEmpty()) {
			builder.append("FgdcTextOnlyField [textValue=");
		} else {
			builder.append("FgdcTextOnlyField [fieldName=");
			builder.append(fieldName);
			builder.append(", textValue=");
		}
		builder.append(textValue);
		builder.append("]");
		return builder.toString();
	}
	
}
