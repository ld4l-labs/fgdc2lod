/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;

/**
 * Represents an Annotation in a FGDC record.
 */
public class FgdcAnnotation extends FgdcField {
	
	public enum AnnotationType {
		
		DESCRIBING,
		SUMMARIZING,
		PROVIDING_PURPOSE;
	}

	private AnnotationType annotationType;
	
	public FgdcAnnotation(Element element, AnnotationType annotationType) throws RecordException {
		super(element);
		this.annotationType = annotationType;
        isValid();
	}

	public AnnotationType getAnnotationType() {
		return annotationType;
	}

	private void isValid() throws RecordFieldException {
        if (textValue == null) {
            throw new RecordFieldException("text value is null");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("text value is empty");
        }
        if (this.annotationType == null) {
        	throw new RecordFieldException("AnnotationType is empty");
        }
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcAnnotation [annotationType=");
		builder.append(annotationType);
		builder.append("]");
		return builder.toString();
	}
	
}
