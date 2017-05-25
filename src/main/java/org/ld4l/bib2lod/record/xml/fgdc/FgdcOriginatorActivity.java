/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents the originator activity in a FGDC record.
 */
public class FgdcOriginatorActivity extends FgdcField {
	
    private enum Field {
        ORIGIN("origin");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
	
	private Element citeinfoElement;
	private List<FgdcAgent> origins;

	/**
	 * @param element
	 */
	public FgdcOriginatorActivity(Element element) throws RecordException {
		super(element);
		this.citeinfoElement = element;
		this.origins = new ArrayList<>();
		if (this.citeinfoElement != null) {
			NodeList originNodes = this.citeinfoElement.getElementsByTagName(Field.ORIGIN.tagName);
			if (originNodes != null && originNodes.getLength() > 0) {
				for (int i = 0; i < originNodes.getLength(); i++) {
					Node node = originNodes.item(i);
					this.origins.add(new FgdcAgent((Element)node));
				}
			}
		}
        isValid();
	}
	
	public List<FgdcAgent> getOrigins() {
		return this.origins;
	}

	private void isValid() throws RecordFieldException {
		if (this.origins.isEmpty()) {
			throw new RecordFieldException("origins value is null");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcOriginatorActivity [origins=");
		builder.append(origins);
		builder.append("]");
		return builder.toString();
	}
	
}
