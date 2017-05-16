/**
 * 
 */
package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;

/**
 * Represents an Agent in a FGDC record.
 *
 */
public class FgdcAgent extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcAgent(Element element) {
		super(element);
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.records.RecordField#isValid()
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
		builder.append("FgdcAgent [textValue=");
		builder.append(textValue);
		builder.append("]");
		return builder.toString();
	}
	
}
