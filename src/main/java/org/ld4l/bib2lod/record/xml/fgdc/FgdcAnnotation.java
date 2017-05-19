/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

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
	
	public FgdcAnnotation(Element element, AnnotationType annotationType) {
		super(element);
		this.annotationType = annotationType;
	}

	public AnnotationType getAnnotationType() {
		return annotationType;
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
        if (getTextValue() == null || getTextValue().isEmpty()) {
            return false;
        }
        if (this.annotationType == null) {
        	return false;
        }
        
        return true;
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
