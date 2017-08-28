/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
import org.w3c.dom.Element;

/**
 * Represents a generic text-only field in a FGDC record.
 */
public class FgdcTextField extends BaseFgdcField implements XmlTextElement {
	
	private String textValue;
	private String fieldName = "";

	/**
	 * Constructor giving this generic field a specific name.
	 */
	public FgdcTextField(Element element, String fieldName) throws RecordException {
		super(element);
		textValue = retrieveTextValue(element);
		if (fieldName != null && !fieldName.isEmpty()) {
			this.fieldName = fieldName;
		}
		isValid();
	}

	/**
	 * @see org.ld4l.bib2lod.records.xml.XmlTextElement#getTextValue()
	 */
	@Override
	public String getTextValue() {
		return textValue;
	}

	/**
	 * Represents the input XML element name creating this record.
	 * 
	 * @return The element name used for creating the text value of this record.
	 */
	public String getFieldName() {
		return fieldName;
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
